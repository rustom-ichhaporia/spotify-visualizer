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

  /**
   * Sets up the API for use using credential login.
   */
  public void setup() {
    initializeApi();
    setupClientCredentials();
  }

  /**
   * Initializes SpotifyApi object using personal credentials. See ApiCredentials field for instructions.
   */
  public void initializeApi() {
    spotifyApi =
        new SpotifyApi.Builder()
            .setClientId(ApiCredentials.getClientId())
            .setClientSecret(ApiCredentials.getClientSecret())
            .build();
  }

  /**
   * Sends call to the remote API to initialize login credentials.
   */
  private void setupClientCredentials() {
    ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

    ClientCredentials clientCredentials = null;
    try {
      clientCredentials = clientCredentialsRequest.execute(); // Attempts to login using provided credentials
    } catch (SpotifyWebApiException | ParseException | IOException e) {
      e.printStackTrace();
    }

    ApiCredentials.setAccessToken(clientCredentials.getAccessToken());
    spotifyApi.setAccessToken(ApiCredentials.getAccessToken()); // Set access token for future calls
  }

  public void deserializeJson(String fileName) {
    File file = new File(fileName);
    ObjectMapper mapper = new ObjectMapper();
    AudioFeatureWrapper audioFeatures = new AudioFeatureWrapper();
    try {
      audioFeatures = mapper.readValue(file, AudioFeatureWrapper.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    songFeatures = audioFeatures.getAudio_features();
    matchNamesToFeatures(songIdsAndNames);
  }

  public List<AudioFeature> getSongFeatures() {
    return songFeatures;
  }

  public void matchNamesToFeatures(Map<String, String> songIdsAndNames) {
    for (AudioFeature feature : songFeatures) {
      for (String songId : songIdsAndNames.keySet()) {
        if (feature.getId().equals(songId)) {
          feature.setSongName(songIdsAndNames.get(songId));
        }
      }
    }
  }

  public void downloadPlaylist(String playlistId) {
    Paging<PlaylistTrack> playlistItems = getPlaylistsItems(playlistId);
    songIdsAndNames = SongNameFunctions.matchSongIds(playlistItems);
    String[] top50SongIds = new String[songIdsAndNames.size()];
    top50SongIds = songIdsAndNames.keySet().toArray(top50SongIds);
    writeAudioFeaturesJson(top50SongIds);
  }

  public Map<String, String> getSongIdsAndNames() {
    return songIdsAndNames;
  }

  private void writeAudioFeaturesJson(String[] trackIdsAndNames) {
    GetAudioFeaturesForSeveralTracksRequest request =
        spotifyApi.getAudioFeaturesForSeveralTracks(trackIdsAndNames).build();
    AudioFeatures[] audioFeatures = null;
    try {
      audioFeatures = request.execute();
      FileWriter writer = new FileWriter("src/main/resources/top_50_audio_features.json");
      writer.write(request.getJson());
      writer.flush();
      writer.close();
    } catch (SpotifyWebApiException | ParseException | IOException e) {
      e.printStackTrace();
    }
    //    for (String trackId : trackIdsAndNames.keySet()) {
    //      GetAudioFeaturesForTrackRequest request =
    // spotifyApi.getAudioFeaturesForTrack(trackId).build();
    //      AudioFeatures audioFeature = null;
    //      try {
    //        audioFeature = request.execute();
    //        String filename = trackIdsAndNames.get(trackId);
    //        FileWriter fileWriter = new FileWriter("src/main/resources/" + filename + ".json");
    //        fileWriter.write(request.getJson());
    //        fileWriter.flush();
    //        fileWriter.close();
    //      } catch (SpotifyWebApiException | ParseException | IOException e) {
    //        e.printStackTrace();
    //      }
    //    }
  }

  private Paging<PlaylistTrack> getPlaylistsItems(String playlistId) {
    GetPlaylistsItemsRequest playlistsItemsRequest =
        spotifyApi.getPlaylistsItems(playlistId).build();
    Paging<PlaylistTrack> playlistTrackPaging = null;
    try {
      playlistTrackPaging = playlistsItemsRequest.execute();
      FileWriter fileWriter = new FileWriter("src/main/resources/top_50.json");
      fileWriter.write(playlistsItemsRequest.getJson());
      fileWriter.flush();
      fileWriter.close();
    } catch (SpotifyWebApiException | ParseException | IOException e) {
      e.printStackTrace();
    }
    return playlistTrackPaging;
  }
}
