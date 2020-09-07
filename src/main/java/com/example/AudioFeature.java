package com.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AudioFeature {
    private double danceability;
    private double energy;
    private int key;
    private double loudness;
    private double speechiness;
    private double acousticness;
    private double instrumentalness;
    private double liveness;
    private double tempo;
    private String id;
    private int duration;
    private int time_signature;

    public double getDanceability() {
        return danceability;
    }

    public double getEnergy() {
        return energy;
    }

    public int getKey() {
        return key;
    }

    public double getLoudness() {
        return loudness;
    }

    public double getSpeechiness() {
        return speechiness;
    }

    public double getAcousticness() {
        return acousticness;
    }

    public double getInstrumentalness() {
        return instrumentalness;
    }

    public double getLiveness() {
        return liveness;
    }

    public double getTempo() {
        return tempo;
    }

    public String getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public int getTime_signature() {
        return time_signature;
    }
}
