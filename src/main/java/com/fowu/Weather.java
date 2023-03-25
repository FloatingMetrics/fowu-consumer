package com.fowu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Weather {


        String time;
        double waveHeight;
        double wavePeriod;
        int waveDirection;
        double windSpeed;
        int windDirection;

        public Weather() {}

        public Weather(double waveHeight, double wavePeriod, int waveDirection, double windSpeed, int windDirection) {

            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");


            this.time = localDateTime.format(formatter);
            this.waveHeight = waveHeight;
            this.wavePeriod = wavePeriod;
            this.waveDirection = waveDirection;
            this.windSpeed = windSpeed;
            this.windDirection = windDirection;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public double getWaveHeight() {
            return waveHeight;
        }

        public void setWaveHeight(double waveHeight) {
            this.waveHeight = waveHeight;
        }

        public double getWavePeriod() {
            return wavePeriod;
        }

        public void setWavePeriod(double wavePeriod) {
            this.wavePeriod = wavePeriod;
        }

        public int getWaveDirection() {
            return waveDirection;
        }

        public void setWaveDirection(int waveDirection) {
            this.waveDirection = waveDirection;
        }

        public double getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(double windSpeed) {
            this.windSpeed = windSpeed;
        }

        public int getWindDirection() {
            return windDirection;
        }

        public void setWindDirection(int windDirection) {
            this.windDirection = windDirection;
        }

        @Override
        public String toString() {
            return "Weather{" +
                    "time=" + time +
                    ", waveHeight=" + waveHeight +
                    ", wavePeriod=" + wavePeriod +
                    ", waveDirection=" + waveDirection +
                    ", windSpeed=" + windSpeed +
                    ", windDirection=" + windDirection +
                    '}';
        }

}
