package com.example;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AnalysisFunctionsTests {
  private ApiDriver apiDriver;
  private List<AudioFeature> songFeatures;

  @Before
  public void deserializeJson() {
    apiDriver = new ApiDriver();
    apiDriver.setup(); // Connects to API using private credentials
    apiDriver.setPlaylistFilePath("src/main/resources/testPlaylist.json");
    apiDriver.setAudioFeaturePath("src/main/resources/testAudioFeatures.json");
    apiDriver.downloadPlaylist(
        "2XMOfCzfdPd8BHfZlfOGr9"); // Downloads JSON for Top 50 songs in US on Spotify
    apiDriver.deserializeJson("src/main/resources/testAudioFeatures.json");
    songFeatures = apiDriver.getSongFeatures(); // Fills AudioFeature list with deserialized objects
  }

  // All edge cases are handled by DeserializationTests, because API calls will always return valid
  // ints/doubles in a predefined range and DeserializationTests handles invalid API calls and
  // invalid data. Therefore, no null or int max value tests are needed.
  @Test
  public void testMostCommonKey() {
    assertEquals("D", AnalysisFunctions.getModeKey(songFeatures));
  }

  @Test
  public void testMedianDuration() {

  }
}
