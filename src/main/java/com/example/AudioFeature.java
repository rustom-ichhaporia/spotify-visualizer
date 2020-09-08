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
  private Double danceability;      // How easy it is to dance to a song, from 0 to 1
  private Double energy;            // How much energy a song has, from 0 to 1
  private Integer key;                  // The key of the song, as an int from 0 (C) to 11 (B)
  private Double loudness;          // How loud the song is in decibels (negative number)
  private Double speechiness;       // How close the song is to "spoken word", from 0 to 1
  private Double acousticness;      // Degree of confidence that the song is acoustic, from 0 to 1
  private Double instrumentalness;  // How much of the song is instrumental, from 0 to 1
  private Double liveness;          // Degree of confidence song was performed live, from 0 to 1
  private Double valence;           // How positive the song is, from 0 (negative) to 1 (positive)
  private Double tempo;             // Tempo of the song in BPM (beats per minute)
  private String id;                // Spotify's internal ID for the song
  private Integer duration_ms;             // Duration of the song in milliseconds
  private Integer time_signature;       // Estimated time signature of the piece in beats per measure
  private String songName;          // Name of the song

  /**
   * @return Danceability score for song
   */
  public Double getDanceability() {
    return danceability;
  }

  /**
   * @return Energy score for song
   */
  public Double getEnergy() {
    return energy;
  }

  /**
   * @return Int representing key for song (see SongNameFunctions.java for details)
   */
  public Integer getKey() {
    return key;
  }

  /**
   * @return Loudness score for song
   */
  public Double getLoudness() {
    return loudness;
  }

  /**
   * @return Speechiness score for song
   */
  public Double getSpeechiness() {
    return speechiness;
  }

  /**
   * @return Acousticness score for song
   */
  public Double getAcousticness() {
    return acousticness;
  }

  /**
   * @return Instrumentalness score for song
   */
  public Double getInstrumentalness() {
    return instrumentalness;
  }

  /**
   * @return Liveness score for song
   */
  public Double getLiveness() {
    return liveness;
  }

  /**
   * @return Valence score for song
   */
  public Double getValence() {
      return valence;
  }

  /**
   * @return Tempo of song
   */
  public Double getTempo() {
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
  public Integer getDuration_ms() {
    return duration_ms;
  }

  /**
   * @return Estimated time signature of song
   */
  public Integer getTime_signature() {
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
