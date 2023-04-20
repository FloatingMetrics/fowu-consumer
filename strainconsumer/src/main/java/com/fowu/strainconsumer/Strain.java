package com.fowu.strainconsumer;

public class Strain {
  String captureTime;
  double strain;

  public Strain() {
  }

  public Strain(String captureTime, double strain) {
    this.captureTime = captureTime;
    this.strain = strain;
  }

  public String getCaptureTime() {
    return captureTime;
  }

  public double getStrain() {
    return strain;
  }

  @Override
  public String toString() {
    return "Strain{" +
           "captureTime=" + captureTime +
           ", strain=" + strain +
           '}';
  }

}
