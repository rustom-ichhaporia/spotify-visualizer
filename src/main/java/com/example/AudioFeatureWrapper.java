package com.example;

import java.util.List;

/**
 * Wrapper class for the AudioFeature objects deserialized from JSON. Needed to allow Jackson to
 * understand the JSON file.
 */
public class AudioFeatureWrapper {
  private List<AudioFeature> audio_features; // List of AudioFeatures in the JSON

  /**
   * Gets AudioFeatures list contained in the wrapper class.
   * @return List of AudioFeatures
   */
  public List<AudioFeature> getAudio_features() {
    return audio_features;
  }
}
