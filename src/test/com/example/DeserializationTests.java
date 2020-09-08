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

  @Test (expected = FileNotFoundException.class)
  public void testInvalidFile() {
    apiDriver.deserializeJson("");
  }

  @Test
  public void testJsonItemName() {
    assertEquals("Youngblood", songFeatures.get(0).getSongName());
  }

  @Test (expected = NullPointerException.class)
  public void testEmptyFile() {
    apiDriver.deserializeJson("src/main/resources/emptyFileTest.json");
  }
}
