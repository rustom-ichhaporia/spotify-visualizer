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
    File file = new File("src/main/resources/categories.json");

    String topListsId = "";

    ObjectMapper mapper = new ObjectMapper();
    CategoriesWrapper categoriesWrapper = mapper.readValue(file, CategoriesWrapper.class);
    Categories categoriesObj = categoriesWrapper.getCategories();
    List<Category> categories = categoriesObj.getAllCategories();
    for (Category category : categories) {
      // System.out.println(category.getName() + "\t" + category.getID());
      if (category.getName().equals("toplists")) {
        topListsId = category.getID();
      }
    }

    SpotifyApi spotifyApi =
        new SpotifyApi.Builder()
            .setClientId(ApiCredentials.getClientId())
            .setClientSecret(ApiCredentials.getClientSecret())
            .build();
    ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

    ClientCredentials clientCredentials = null;
    try {
      clientCredentials = clientCredentialsRequest.execute();
    } catch (SpotifyWebApiException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }

    ApiCredentials.setAccessToken(clientCredentials.getAccessToken());
    spotifyApi.setAccessToken(ApiCredentials.getAccessToken());

    GetAudioFeaturesForTrackRequest analysis =
        spotifyApi.getAudioFeaturesForTrack("4Oun2ylbjFKMPTiaSbbCih").build();
    try {
      AudioFeatures analysis1 = analysis.execute();
      System.out.println(analysis.getJson());
      System.out.println(analysis1.getDanceability());
      FileWriter fileWriter = new FileWriter("src/main/resources/cardib.json");
      fileWriter.write(analysis.getJson());
      fileWriter.flush();
      fileWriter.close();

    } catch (SpotifyWebApiException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
