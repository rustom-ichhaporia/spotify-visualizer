package com.example;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class containing analysis functions that are run on the playlist data, returning sublists of
 * AudioFeatures.
 */
public class AnalysisFunctions {
  /**
   * Sorts a given List of AudioFeatures by an Integer field, such as duration.
   *
   * @param audioFeatures List of AudioFeatures to be sorted
   * @param fieldGetter Get method from AudioFeatures that
   * @param ascending True if the list should be ascending, or false for descending
   * @return Sorted list of AudioFeatures.
   */
  public static List<AudioFeature> sortByInteger(
          List<AudioFeature> audioFeatures,
          Function<AudioFeature, Integer> fieldGetter,
          boolean ascending) {
    return (List<AudioFeature>)
            audioFeatures.stream()
                    .sorted(Comparator.comparing(fieldGetter))
                    .collect(Collectors.toList());
  }

  /**
   * Sorts a given List of AudioFeatures by an Double field, such as acousticness.
   *
   * @param audioFeatures List of AudioFeatures to be sorted
   * @param fieldGetter Get method from AudioFeatures that
   * @param ascending True if the list should be ascending, or false for descending
   * @return Sorted list of AudioFeatures.
   */
  public static List<AudioFeature> sortByDouble(
          List<AudioFeature> audioFeatures,
          Function<AudioFeature, Double> fieldGetter,
          boolean ascending) {
    return (List<AudioFeature>)
            audioFeatures.stream()
                    .sorted(Comparator.comparing(fieldGetter))
                    .collect(Collectors.toList());
  }

  /**
   * Sorts a given List of AudioFeatures by an String field, such as songName.
   *
   * @param audioFeatures List of AudioFeatures to be sorted
   * @param fieldGetter Get method from AudioFeatures that
   * @param ascending True if the list should be ascending, or false for descending
   * @return Sorted list of AudioFeatures.
   */
  public static List<AudioFeature> sortByString(
          List<AudioFeature> audioFeatures,
          Function<AudioFeature, String> fieldGetter,
          boolean ascending) {
    return (List<AudioFeature>)
            audioFeatures.stream()
                    .sorted(Comparator.comparing(fieldGetter))
                    .collect(Collectors.toList());
  }

  /**
   * Calculates the mode of the song keys (most common key).
   *
   * @param audioFeatures List of AudioFeatures to analyze
   * @return String representation of the most common key.
   */
  public static String getModeKey(List<AudioFeature> audioFeatures) {
    // Some function decomposition taken from:
    // https://stackoverflow.com/questions/43616422/find-the-most-common-attribute-value-from-a-list-of-objects-using-stream

    return MusicalKeys.getkeyString(
        audioFeatures.stream()
            .map(AudioFeature::getKey)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null)
            .intValue());
  }

  /**
   * Calculates median duration of the songs in milliseconds.
   *
   * @param audioFeatures List of AudioFeatures to analyze
   * @return Integer representation of the median duration
   */
  public static Integer getMedianDuration(List<AudioFeature> audioFeatures) {
    List<AudioFeature> sortedByDuration =
        sortByInteger(audioFeatures, AudioFeature::getDuration_ms, true);
    return sortedByDuration.get(sortedByDuration.size() / 2).getDuration_ms();
  }

  /**
   * Calculates median acousticness score of songs between 0 and 1.
   *
   * @param audioFeatures List of AudioFeatures to analyze
   * @return Double representation of the median acousticness between 0 and 1
   */
  public static Double getMedianAcousticness(List<AudioFeature> audioFeatures) {
    List<AudioFeature> sortedByDuration =
        sortByDouble(audioFeatures, AudioFeature::getAcousticness, true);
    return sortedByDuration.get(sortedByDuration.size() / 2).getAcousticness();
  }

  /**
   * Calculates median instrumentalness score of songs between 0 and 1.
   *
   * @param audioFeatures List of AudioFeatures to analyze
   * @return Double representation of the median instrumentalness between 0 and 1
   */
  public static Double getMedianInstrumentalness(List<AudioFeature> audioFeatures) {
    List<AudioFeature> sortedByDuration =
            sortByDouble(audioFeatures, AudioFeature::getInstrumentalness, true);
    return sortedByDuration.get(sortedByDuration.size() / 2).getInstrumentalness();
  }
}
