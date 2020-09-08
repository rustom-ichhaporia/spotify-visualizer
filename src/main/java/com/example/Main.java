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
    apiDriver.setPlaylistFilePath("src/main/resources/top50Playlist.json");
    apiDriver.setAudioFeaturePath("src/main/resources/top50AudioFeatures.json");
    // Downloads JSONs for Top 50 songs in US on Spotify and their song features
    apiDriver.downloadPlaylist("37i9dQZEVXbLRQDuF5jeBp");
    apiDriver.deserializeJson("src/main/resources/top50AudioFeatures.json");
    songFeatures = apiDriver.getSongFeatures(); // Fills AudioFeature list with deserialized objects
  }

  /** Runs a sample call for each filtering function in FilteringFunctions. */
  public static void runFilteringFunctions() {
    System.out.println("Songs with tempo above 160: "
            + SongNameFunctions.getSongNames(filter.getSongsWithTempo(songFeatures, 160, true)));
    System.out.println("Songs with danceability above 0.85: "
            + SongNameFunctions.getSongNames(
                filter.getSongsWithDanceability(songFeatures, .85, true)));
    System.out.println("Songs with speechiness below 0.05: "
            + SongNameFunctions.getSongNames(
                filter.getSongsWithSpeechiness(songFeatures, .05, false)));
    System.out.println("Get songs in key Csharp: "
            + SongNameFunctions.getSongNames(filter.getSongsWithKey(songFeatures, "Csharp")));
  }

  /** Runs a sample call for some analysis functions in AnalysisFunctions. */
  public static void runAnalysisFunctions() {
    System.out.println("Mode of song keys: "
            + AnalysisFunctions.getModeKey(songFeatures));
    System.out.println("Median of song durations: "
            + AnalysisFunctions.getMedianDuration(songFeatures));
    System.out.println("Median of song acousticness scores: "
            + AnalysisFunctions.getMedianAcousticness(songFeatures));
    System.out.println("Median of song instrumentalness scores: "
            + AnalysisFunctions.getMedianInstrumentalness(songFeatures));
  }

  /** Generates sample plots for the data gathered from the playlist. */
  public static void runPlotter() {}
}
