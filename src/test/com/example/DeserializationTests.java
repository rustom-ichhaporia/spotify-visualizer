package com.example;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DeserializationTests {
  private ApiDriver apiDriver;
  private List<AudioFeature> songFeatures;

  @Before
  public void deserializeJson() {
    apiDriver = new ApiDriver();
    apiDriver.setup(); // Connects to API using private credentials
    apiDriver.setPlaylistFilePath("src/main/resources/testPlaylist.json");
    apiDriver.setAudioFeaturePath("src/main/resources/testAudioFeatures.json");
    apiDriver.downloadPlaylist("2XMOfCzfdPd8BHfZlfOGr9"); // Downloads JSON for Top 50 songs in US on Spotify
    apiDriver.deserializeJson("src/main/resources/testAudioFeatures.json");
    songFeatures = apiDriver.getSongFeatures(); // Fills AudioFeature list with deserialized objects
  }

  @Test (expected = NullPointerException.class)
  public void testInvalidFilePath() {
    apiDriver.deserializeJson("");
  }

  @Test (expected = NullPointerException.class)
  public void testEmptyFile() {
    apiDriver.deserializeJson("src/main/resources/testEmptyFile.json");
  }

  @Test (expected = NullPointerException.class)
  public void testInvalidJSON() {
    // Tests the deserialization of a improperly formatted JSON where one entry is a String
    // instead of an int to invalidate Jackson.
    apiDriver.deserializeJson("src/main/resources/testAudioFeaturesInvalid.json");
  }

  @Test
  public void testEmptyJson() {
    apiDriver.deserializeJson("src/main/resources/emptyAudioFeatures.json");
    assertEquals(0, apiDriver.getSongFeatures().size());
  }

  // The following tests assume properly JSON format and deserialization, as passed by the previous tests.

  @Test
  public void testJsonItemName() {
    assertEquals("Youngblood", songFeatures.get(0).getSongName());
  }

  @Test
  public void testJsonListLength() {
    assertEquals(4, songFeatures.size());
  }
}
