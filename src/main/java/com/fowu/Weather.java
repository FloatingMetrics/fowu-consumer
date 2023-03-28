package com.fowu;

import java.time.Instant;

public class Weather {
        long captureTime;
        double waveHeight;
        double wavePeriod;
        int waveDirection;
        double windSpeed;
        int windDirection;

        public Weather() {}

        public Weather(double waveHeight, double wavePeriod, int waveDirection, double windSpeed, int windDirection) {

            long epochtime = Instant.now().getEpochSecond();

            this.captureTime = epochtime;
            this.waveHeight = waveHeight;
            this.wavePeriod = wavePeriod;
            this.waveDirection = waveDirection;
            this.windSpeed = windSpeed;
            this.windDirection = windDirection;
        }

        public long getCaptureTime() {
            return captureTime;
        }

        public void setCaptureTime(long captureTime) {
            this.captureTime = captureTime;
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
                   "captureTime=" + captureTime +
                   ", waveHeight=" + waveHeight +
                   ", wavePeriod=" + wavePeriod +
                   ", waveDirection=" + waveDirection +
                   ", windSpeed=" + windSpeed +
                   ", windDirection=" + windDirection +
                   '}';
        }

}
