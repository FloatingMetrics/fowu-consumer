package com.fowu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Weather {
  String captureTime;
  double waveHeight;
  double wavePeriod;
  int waveDirection;
  double windSpeed;
  int windDirection;

  public Weather() {
  }

  public Weather(String captureTime, double waveHeight, double wavePeriod,
                 int waveDirection,
                 double windSpeed, int windDirection) {

    this.captureTime = captureTime;
    this.waveHeight = waveHeight;
    this.wavePeriod = wavePeriod;
    this.waveDirection = waveDirection;
    this.windSpeed = windSpeed;
    this.windDirection = windDirection;
  }

  public String getCaptureTime() {
    return captureTime;
  }

  public double getWaveHeight() {
    return waveHeight;
  }

  public double getWavePeriod() {
    return wavePeriod;
  }

  public int getWaveDirection() {
    return waveDirection;
  }

  public double getWindSpeed() {
    return windSpeed;
  }

  public int getWindDirection() {
    return windDirection;
  }

  @Override
  public String toString() {
    return "Weather{" +
           "captureTime=" + captureTime +
           ", waveHeight=" + waveHeight +
           ", wavePeriod=" + wavePeriod +
           ", waveDirection=" + waveDirection +
           ", windSpeed=" + windSpeed +
           ", windDirection=" + windDirection +
           '}';
  }

}
