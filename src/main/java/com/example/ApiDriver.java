package com.example;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.AudioFeatures;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import com.wrapper.spotify.requests.data.tracks.GetAudioFeaturesForSeveralTracksRequest;
import com.wrapper.spotify.requests.data.tracks.GetAudioFeaturesForTrackRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiDriver {
  private SpotifyApi spotifyApi;
  private ApiCredentials apiCredentials;

  public void setup() {
    initializeApi();
    setupClientCredentials();
    downloadTop50Json();
  }

  private void initializeApi() {
    spotifyApi =
        new SpotifyApi.Builder()
            .setClientId(ApiCredentials.getClientId())
            .setClientSecret(ApiCredentials.getClientSecret())
            .build();
  }

  private void setupClientCredentials() {
    ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

    ClientCredentials clientCredentials = null;
    try {
      clientCredentials = clientCredentialsRequest.execute();
    } catch (SpotifyWebApiException | ParseException | IOException e) {
      e.printStackTrace();
    }

    ApiCredentials.setAccessToken(clientCredentials.getAccessToken());
    spotifyApi.setAccessToken(ApiCredentials.getAccessToken());
  }

  private void downloadTop50Json() {
    String top50UnitedStatesPlaylistId = "37i9dQZEVXbLRQDuF5jeBp";
    Map<String, String> top50SongIdsAndNames = getPlaylistSongIds(getPlaylistsItems(top50UnitedStatesPlaylistId));
//    String[] top50SongIdsAndNames = new String[top50SongNames.size()];
//    top50SongIdsAndNames = top50SongNames.keySet().toArray(top50SongIdsAndNames);
    writeAudioFeaturesJson(top50SongIdsAndNames);
  }

  private void writeAudioFeaturesJson(Map<String, String> trackIdsAndNames) {
    for (String trackId : trackIdsAndNames.keySet()) {
      GetAudioFeaturesForTrackRequest request = spotifyApi.getAudioFeaturesForTrack(trackId).build();
      AudioFeatures audioFeature = null;
      try {
        audioFeature = request.execute();
        String filename = trackIdsAndNames.get(trackId);
        FileWriter fileWriter = new FileWriter("src/main/resources/" + filename + ".json");
        fileWriter.write(request.getJson());
        fileWriter.flush();
        fileWriter.close();
      } catch (SpotifyWebApiException | ParseException | IOException e) {
        e.printStackTrace();
      }
    }
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


  private Map<String, String> getPlaylistSongIds(Paging<PlaylistTrack> playlistTrackPaging) {


    Map<String, String> songIds = new HashMap<String, String>();
    for (PlaylistTrack playlistTrack : playlistTrackPaging.getItems()) {
      Track track = (Track) playlistTrack.getTrack();
      songIds.put(track.getId(), track.getName().replaceAll("\\W+", ""));
    }

    return songIds;
  }
}
