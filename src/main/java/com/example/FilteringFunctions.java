package com.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class containing filtering functions that are run on the playlist data, returning sublists of
 * AudioFeatures.
 */
public class FilteringFunctions {
  /**
   * Selects songs wth a tempo above or below a certain BPM (beats per minute) value.
   *
   * @param audioFeatures List of AudioFeatures to filter
   * @param tempoThreshold Threshold for tempo
   * @param above True if asking for songs above threshold, false for songs below threshold
   * @return List of AudioFeatures for songs that match the criterion
   */
  public static List<AudioFeature> getSongsWithTempo(
      List<AudioFeature> audioFeatures, double tempoThreshold, boolean above) {
    List<AudioFeature> output = new ArrayList<AudioFeature>();
    for (AudioFeature feature : audioFeatures) {
      double tempo = feature.getTempo();
      if (above && tempo > tempoThreshold) { // Above threshold
        output.add(feature);
      } else if (!above && tempo < tempoThreshold) { // Below threshold
        output.add(feature);
      }
    }
    return output;
  }

  /**
   * Selects songs with a danceability score above or below a certain value.
   *
   * @param danceabilityThreshold Danceability score from 0 to 1
   * @param above True if asking for songs above threshold, false for songs below threshold
   * @return List of AudioFeatures for songs that match the criterion.
   */
  public static List<AudioFeature> getSongsWithDanceability(
      List<AudioFeature> audioFeatures, double danceabilityThreshold, boolean above) {
    List<AudioFeature> output = new ArrayList<AudioFeature>();
    for (AudioFeature feature : audioFeatures) {
      double danceability = feature.getDanceability();
      if (above && danceability > danceabilityThreshold) { // Above threshold
        output.add(feature);
      } else if (!above && danceability < danceabilityThreshold) { // Below threshold
        output.add(feature);
      }
    }
    return output;
  }

  /**
   * Selects songs with a speechiness score above or below a certain value.
   *
   * @param audioFeatures List of AudioFeatures to filter
   * @param speechinessThreshold Speechiness score from 0 to 1
   * @param above True if asking for songs above threshold, false for songs below threshold
   * @return List of AudioFeatures for songs that match the criterion.
   */
  public List<AudioFeature> getSongsWithSpeechiness(
      List<AudioFeature> audioFeatures, double speechinessThreshold, boolean above) {
    List<AudioFeature> output = new ArrayList<AudioFeature>();
    for (AudioFeature feature : audioFeatures) {
      double speechiness = feature.getSpeechiness();
      if (above && speechiness > speechinessThreshold) { // Above threshold
        output.add(feature);
      } else if (!above && speechiness < speechinessThreshold) { // Below threshold
        output.add(feature);
      }
    }
    return output;
  }

  /**
   * Selects songs with a speechiness score above or below a certain value.
   *
   * @param audioFeatures List of AudioFeatures to filter
   * @param keyString String representing key, in form such as "G", "Gsharp", or "Gflat".
   * @return List of AudioFeatures for songs that match the criterion.
   */
  public List<AudioFeature> getSongsWithKey(List<AudioFeature> audioFeatures, String keyString) {
    int keyInt = MusicalKeys.getKeyInt(keyString); // Convert string to int representation
    List<AudioFeature> output = new ArrayList<AudioFeature>();
    for (AudioFeature feature : audioFeatures) {
      int key = feature.getKey();
      if (key == keyInt) {
        output.add(feature);
      }
    }
    return output;
  }
}
