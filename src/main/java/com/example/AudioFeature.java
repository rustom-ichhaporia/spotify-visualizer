package com.example;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Class representing AudioFeature object from Spotify API JSON. For details about fields, see:
 * https://developer.spotify.com/documentation/web-api/reference/tracks/get-audio-features/
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AudioFeature {
  private double danceability;      // How easy it is to dance to a song, from 0 to 1
  private double energy;            // How much energy a song has, from 0 to 1
  private int key;                  // The key of the song, as an int from 0 (C) to 11 (B)
  private double loudness;          // How loud the song is in decibels (negative number)
  private double speechiness;       // How close the song is to "spoken word", from 0 to 1
  private double acousticness;      // Degree of confidence that the song is acoustic, from 0 to 1
  private double instrumentalness;  // How much of the song is instrumental, from 0 to 1
  private double liveness;          // Degree of confidence song was performed live, from 0 to 1
  private double valence;           // How positive the song is, from 0 (negative) to 1 (positive)
  private double tempo;             // Tempo of the song in BPM (beats per minute)
  private String id;                // Spotify's internal ID for the song
  private int duration;             // Duration of the song in milliseconds
  private int time_signature;       // Estimated time signature of the piece in beats per measure
  private String songName;          // Name of the song

  /**
   * @return Danceability score for song
   */
  public double getDanceability() {
    return danceability;
  }

  /**
   * @return Energy score for song
   */
  public double getEnergy() {
    return energy;
  }

  /**
   * @return Int representing key for song (see SongNameFunctions.java for details)
   */
  public int getKey() {
    return key;
  }

  /**
   * @return Loudness score for song
   */
  public double getLoudness() {
    return loudness;
  }

  /**
   * @return Speechiness score for song
   */
  public double getSpeechiness() {
    return speechiness;
  }

  /**
   * @return Acousticness score for song
   */
  public double getAcousticness() {
    return acousticness;
  }

  /**
   * @return Instrumentalness score for song
   */
  public double getInstrumentalness() {
    return instrumentalness;
  }

  /**
   * @return Liveness score for song
   */
  public double getLiveness() {
    return liveness;
  }

  /**
   * @return Valence score for song
   */
  public double getValence() {
      return valence;
  }

  /**
   * @return Tempo of song
   */
  public double getTempo() {
    return tempo;
  }

  /**
   * @return ID string of song
   */
  public String getId() {
    return id;
  }

  /**
   * @return Duration of song in milliseconds
   */
  public int getDuration() {
    return duration;
  }

  /**
   * @return Estimated time signature of song
   */
  public int getTime_signature() {
    return time_signature;
  }

  /**
   * @return Name of song
   */
  @JsonGetter
  public String getSongName() {
    return songName;
  }

  /**
   * @param nameInput Song name to set
   */
  @JsonSetter
  public void setSongName(String nameInput) {
    songName = nameInput;
  }
}
