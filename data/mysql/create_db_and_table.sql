CREATE DATABASE IF NOT EXISTS fowudatabase;

USE fowudatabase;

DROP TABLE IF EXISTS weather;
CREATE TABLE weather
(
  captureTime   int,
  waveHeight    double,
  wavePeriod    double,
  waveDirection int,
  windSpeed     double,
  windDirection int,
  PRIMARY KEY (captureTime)
);

