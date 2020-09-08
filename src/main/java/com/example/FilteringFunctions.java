package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing filtering functions that are run on the playlist data, returning sublists of
 * AudioFeatures.
 */
public class FilteringFunctions {
  private List<AudioFeature> audioFeatures; // Master list of AudioFeatures from which to select

  /**
   * Returns the master AudioFeature list that the filtering functions use.
   *
   * @return List of AudioFeatures
   */
  public List<AudioFeature> getAudioFeatures() {
    return audioFeatures;
  }

  /**
   * Sets the master AudioFeature list that the filtering functions use.
   *
   * @param audioFeatures List of AudioFeatures
   */
  public void setAudioFeatures(List<AudioFeature> audioFeatures) {
    this.audioFeatures = audioFeatures;
  }

  /**
   * Selects songs wth a tempo above or below a certain BPM (beats per minute) value.
   *
   * @param tempoThreshold Threshold for tempo
   * @param above True if asking for songs above threshold, false for songs below threshold
   * @return List of AudioFeatures for songs that match the criterion
   */
  public List<AudioFeature> getSongsWithTempo(double tempoThreshold, boolean above) {
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
  public List<AudioFeature> getSongsWithDanceability(double danceabilityThreshold, boolean above) {
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
   * @param speechinessThreshold Speechiness score from 0 to 1
   * @param above True if asking for songs above threshold, false for songs below threshold
   * @return List of AudioFeatures for songs that match the criterion.
   */
  public List<AudioFeature> getSongsWithSpeechiness(double speechinessThreshold, boolean above) {
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
   * @param keyString String representing key, in form such as "G", "Gsharp", or "Gflat".
   * @return List of AudioFeatures for songs that match the criterion.
   */
  public List<AudioFeature> getSongsWithKey(String keyString) {
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
