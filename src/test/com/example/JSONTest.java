package com.example;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

// It may be a good idea to rename/refactor depending on the focus of your assignment.
public class JSONTest {
  private ApiDriver apiDriver;

  @BeforeClass
  public void setUpApi() {
    apiDriver.setup();
  }

  @Before
  public void deserializeJson() {
    apiDriver = new ApiDriver();
    apiDriver.deserializeJson("src/main/top_3_audio_features.json");
  }

  @Test
  public void testTempoBelow() {
    FilteringFunctions filter = new FilteringFunctions();
  }
}
