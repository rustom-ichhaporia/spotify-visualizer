package com.example;

import java.io.IOException;
import java.util.List;

/**
 * Driver class for JSON+X project for CS 126.
 *
 * @author Rustom Ichhaporia
 */
public class Main {
  // apiDriver connects the client to the Spotify API, downloads data, and serializes JSON.
  private static ApiDriver apiDriver = new ApiDriver();
  // filter contains all the functions that filter deserialized data.
  private static FilteringFunctions filter = new FilteringFunctions();
  // analysis contains all the functions that analyze deserialized data.
  private static AnalysisFunctions analysis = new AnalysisFunctions();
  // songFeatures is a list of AudioFeature objects representing deserialized data.
  private static List<AudioFeature> songFeatures;

  /**
   * Runs the all of the functions and plots for the program, not including tests.
   *
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    runApiDriver();
    runFilteringFunctions();
    runAnalysisFunctions();
    runPlotter();
  }

  /** Sets up the API to get data, create JSON files, and deserialize them. */
  public static void runApiDriver() {
    apiDriver.setup(); // Connects to API using private credentials
    apiDriver.downloadPlaylist("37i9dQZEVXbLRQDuF5jeBp"); // Downloads JSON for Top 50 songs in US on Spotify
    apiDriver.deserializeJson("src/main/resources/top_50_audio_features.json");
    songFeatures = apiDriver.getSongFeatures(); // Fills AudioFeature list with deserialized objects
  }

  /** Runs a sample call for each filtering function in FilteringFunctions. */
  public static void runFilteringFunctions() {
    filter.setAudioFeatures(songFeatures);
    System.out.println(
        "Songs with tempo above 160: " + SongNameFunctions.getSongNames(filter.getSongsWithTempo(160, true)));
    System.out.println(
        "Songs with danceability above 0.85: "
            + SongNameFunctions.getSongNames(filter.getSongsWithDanceability(0.85, true)));
    System.out.println(
        "Songs with speechiness below 0.05: "
            + SongNameFunctions.getSongNames(filter.getSongsWithSpeechiness(0.05, false)));
    System.out.println(
        "Get songs in key Csharp: " + SongNameFunctions.getSongNames(filter.getSongsWithKey("Csharp")));
  }

  /** Runs a sample call for each analysis function in AnalysisFunctions. */
  public static void runAnalysisFunctions() {}

  /** Generates sample plots for the data gathered from the playlist. */
  public static void runPlotter() {}
}
