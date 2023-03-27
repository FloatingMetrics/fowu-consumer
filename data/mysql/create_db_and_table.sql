create database fowu;

USE fowu;

CREATE TABLE weather
(
  captureTime   varchar(150),
  waveHeight    double,
  wavePeriod    double,
  waveDirection int,
  windSpeed     double,
  windDirection int,
  PRIMARY KEY (captureTime)
);

