package com.example;

import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.model_objects.specification.Track;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class containing utility functions for song names and IDs.
 */
public class SongNameFunctions {
  /**
   * Creates a map of corresponding song IDs and their corresponding names.
   * @param playlistTrackPaging List of songs
   * @return Map with song ID as key and song name as value
   */
  public static Map<String, String> matchSongIds(Paging<PlaylistTrack> playlistTrackPaging) {
    Map<String, String> songIds = new HashMap<String, String>();
    for (PlaylistTrack playlistTrack : playlistTrackPaging.getItems()) {
      Track track = (Track) playlistTrack.getTrack();
      songIds.put(track.getId(), track.getName());  // ID key, name value
    }
    return songIds;
  }

  /**
   * returns the song names a list of AudioFeatures
   * @param listToPrint List of AudioFeatures from which to get names
   * @return String with each song name on a new line
   */
  public static String getSongNames(List<AudioFeature> listToPrint) {
    String output = "";
    for (AudioFeature feature : listToPrint) {
      output += feature.getSongName() + "\n";
    }
    return output;
  }
}
