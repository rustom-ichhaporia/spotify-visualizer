package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.AudioFeatures;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.tracks.GetAudioFeaturesForTrackRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    ApiDriver driver = new ApiDriver();
    driver.setup();

    File file = new File("src/main/resources/top_50_audio_features.json");
    ObjectMapper mapper = new ObjectMapper();
    AudioFeatureWrapper audioFeatures = mapper.readValue(file, AudioFeatureWrapper.class);

    List<AudioFeature> songFeatures = audioFeatures.getAudio_features();


  }
}
