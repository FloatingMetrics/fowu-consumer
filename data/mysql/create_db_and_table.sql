CREATE DATABASE IF NOT EXISTS fowudatabase;

USE fowudatabase;

CREATE TABLE IF NOT EXISTS weather
(
  captureTime   varchar(150),
  waveHeight    double,
  wavePeriod    double,
  waveDirection int,
  windSpeed     double,
  windDirection int,
  PRIMARY KEY (captureTime)
);

