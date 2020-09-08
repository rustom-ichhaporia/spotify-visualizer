package com.example;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class FilteringFunctionsTests {
  private ApiDriver apiDriver;
  private List<AudioFeature> songFeatures;
  private FilteringFunctions filter;

  @Before
  public void deserializeJson() {
    apiDriver = new ApiDriver();
    apiDriver.setup(); // Connects to API using private credentials
    apiDriver.setPlaylistFilePath("src/main/resources/testPlaylist.json");
    apiDriver.setAudioFeaturePath("src/main/resources/testAudioFeatures.json");
    apiDriver.downloadPlaylist("2XMOfCzfdPd8BHfZlfOGr9"); // Downloads JSON for test playlist
    apiDriver.deserializeJson("src/main/resources/testAudioFeatures.json");
    songFeatures = apiDriver.getSongFeatures(); // Fills AudioFeature list with deserialized objects
    filter = new FilteringFunctions();
  }

  // All edge cases are handled by DeserializationTests, because API calls will always return valid
  // ints/doubles in a predefined range and DeserializationTests handles invalid API calls and
  // invalid data. Therefore, no null or int max value tests are needed.

  @Test
  public void testSongsAboveTempo() {
    assertEquals("Despacito - Remix\n",
        SongNameFunctions.getSongNames(filter.getSongsWithTempo(songFeatures, 170, true)));
  }

  @Test
  public void testSongsBelowTempo() {
    assertEquals("Youngblood\nCircles\n",
        SongNameFunctions.getSongNames(filter.getSongsWithTempo(songFeatures, 121, false)));
  }

  @Test
  public void testSongsAboveDanceability() {
    assertEquals("Circles\n",
        SongNameFunctions.getSongNames(filter.getSongsWithDanceability(songFeatures, 0.68, true)));
  }

  @Test
  public void testSongsBelowDanceability() {
    assertEquals("She Looks So Perfect\n",
        SongNameFunctions.getSongNames(filter.getSongsWithDanceability(songFeatures, 0.5, false)));
  }

  @Test
  public void testSongsAboveSpeechiness() {
    assertEquals("Youngblood\n",
        SongNameFunctions.getSongNames(filter.getSongsWithSpeechiness(songFeatures, 0.2, true)));
  }

  @Test
  public void testSongsBelowSpeechiness() {
    assertEquals("Circles\n",
        SongNameFunctions.getSongNames(filter.getSongsWithSpeechiness(songFeatures, 0.05, false)));
  }

  @Test
  public void testSongsWithKey() {
    assertEquals("Circles\n",
            SongNameFunctions.getSongNames(filter.getSongsWithKey(songFeatures, "C")));
  }
}
