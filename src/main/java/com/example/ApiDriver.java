package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.AudioFeatures;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import com.wrapper.spotify.requests.data.tracks.GetAudioFeaturesForSeveralTracksRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Custom implementation of the spotify-web-api-java wrapper created by Jonas Thelemann For details,
 * see: https://github.com/thelinmichael/spotify-web-api-java
 */
public class ApiDriver {
  private SpotifyApi spotifyApi; // Main API class that runs all API calls
  private ApiCredentials
      apiCredentials; // Private class containing credentials for public/secret key to Spotify
  // API. NOTE: This is NOT uploaded to Github for security reasons, so please
  private List<AudioFeature> songFeatures; // List of AudioFeatures objects to be generated
  private Map<String, String> songIdsAndNames; // Map associating song IDs to song names
  private String playlistFilePath = ""; // File path to save playlist, can be edited
  private String audioFeaturePath = ""; // File path to save AudioFeatures

  /** Sets up the API for use using credential login. */
  public void setup() {
    initializeApi();
    setupClientCredentials();
  }

  /**
   * Initializes SpotifyApi object using personal credentials. See ApiCredentials field for
   * instructions.
   */
  public void initializeApi() {
    spotifyApi =
        new SpotifyApi.Builder()
            .setClientId(ApiCredentials.getClientId())
            .setClientSecret(ApiCredentials.getClientSecret())
            .build();
  }

  /** Sends call to the remote API to initialize login credentials. */
  private void setupClientCredentials() {
    ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

    ClientCredentials clientCredentials = null;
    try {
      clientCredentials =
          clientCredentialsRequest.execute(); // Attempts to login using provided credentials
    } catch (SpotifyWebApiException | ParseException | IOException e) {
      e.printStackTrace();
    }

    ApiCredentials.setAccessToken(clientCredentials.getAccessToken());
    spotifyApi.setAccessToken(ApiCredentials.getAccessToken()); // Set access token for future calls
  }

  /**
   * Deserializes a JSON file representing a list of AudioFeatures into an AudioFeatureWrapper
   *
   * @param fileName Path to file, beginning at src/
   */
  public void deserializeJson(String fileName) {
    File file = new File(fileName);
    ObjectMapper mapper = new ObjectMapper();
    AudioFeatureWrapper audioFeatures = new AudioFeatureWrapper(); // Wrapper for AudioFeature list
    try {
      audioFeatures = mapper.readValue(file, AudioFeatureWrapper.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    songFeatures = audioFeatures.getAudio_features();
    matchNamesToFeatures(songIdsAndNames);
  }

  /**
   * Returns the list of AudioFeatures retrieved by the API.
   *
   * @return
   */
  public List<AudioFeature> getSongFeatures() {
    return songFeatures;
  }

  /**
   * Takes the song names from the playlist tracks and matches them to the AudioFeatures using their
   * IDs.
   *
   * @param songIdsAndNames Map associating song IDs and names
   */
  public void matchNamesToFeatures(Map<String, String> songIdsAndNames) {
    for (AudioFeature feature : songFeatures) {
      for (String songId : songIdsAndNames.keySet()) {
        if (feature.getId().equals(songId)) {
          // If the songs match, add the name to the AudioFeature
          feature.setSongName(songIdsAndNames.get(songId));
        }
      }
    }
  }

  /**
   * Downloads the playlist given by a specified ID into a JSON file.
   *
   * @param playlistId Spotify internal ID representing playlist
   */
  public void downloadPlaylist(String playlistId) {
    Paging<PlaylistTrack> playlistItems = getPlaylistsItems(playlistId);
    songIdsAndNames = SongNameFunctions.matchSongIds(playlistItems);
    String[] playlistSongIds = new String[songIdsAndNames.size()];
    playlistSongIds = songIdsAndNames.keySet().toArray(playlistSongIds);
    writeAudioFeaturesJson(playlistSongIds);
  }

  /**
   * Returns the Map associating song IDs and names from the playlist.
   *
   * @return Map with keySet song IDs and values song names.
   */
  public Map<String, String> getSongIdsAndNames() {
    return songIdsAndNames;
  }

  /**
   * Saves the AudioFeatures for the songs in the playlist to a JSON file.
   *
   * @param trackIdsAndNames array of IDs of the songs to get.
   */
  private void writeAudioFeaturesJson(String[] trackIdsAndNames) {
    GetAudioFeaturesForSeveralTracksRequest request =
        spotifyApi.getAudioFeaturesForSeveralTracks(trackIdsAndNames).build();
    AudioFeatures[] audioFeatures = null;
    try {
      audioFeatures = request.execute();
      FileWriter writer = new FileWriter(audioFeaturePath);
      writer.write(request.getJson());
      writer.flush();
      writer.close();
    } catch (SpotifyWebApiException | ParseException | IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Writes the playlist items to JSON and returns a Paging of the tracks in the playlist.
   *
   * @param playlistId Spotify internal ID of playlist
   * @return Paging set of songs in the playlist
   */
  private Paging<PlaylistTrack> getPlaylistsItems(String playlistId) {
    GetPlaylistsItemsRequest playlistsItemsRequest =
        spotifyApi.getPlaylistsItems(playlistId).build();
    Paging<PlaylistTrack> playlistTrackPaging = null;
    try {
      playlistTrackPaging = playlistsItemsRequest.execute();
      FileWriter fileWriter = new FileWriter(playlistFilePath);
      fileWriter.write(playlistsItemsRequest.getJson());
      fileWriter.flush();
      fileWriter.close();
    } catch (SpotifyWebApiException | ParseException | IOException e) {
      e.printStackTrace();
    }
    return playlistTrackPaging;
  }

  /**
   * Gets the file path where the playlist is saved.
   *
   * @return Playlist file path
   */
  public String getPlaylistFilePath() {
    return playlistFilePath;
  }

  /**
   * Sets the file path for the playlist JSON to be saved.
   *
   * @param playlistFilePath File path to be set
   */
  public void setPlaylistFilePath(String playlistFilePath) {
    this.playlistFilePath = playlistFilePath;
  }

  /**
   * Gets the file path where the AudioFeatures are saved.
   *
   * @return AudioFeatures file path
   */
  public String getAudioFeaturePath() {
    return audioFeaturePath;
  }

  /**
   * Sets the file where the AudioFeatures JSON is to be saved.
   *
   * @param audioFeaturePath File path to be set
   */
  public void setAudioFeaturePath(String audioFeaturePath) {
    this.audioFeaturePath = audioFeaturePath;
  }
}
