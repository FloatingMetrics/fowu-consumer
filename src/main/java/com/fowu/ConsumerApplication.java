package com.fowu;

public class ConsumerApplication {
  public static void main(String[] args) throws Exception {
    new Consumer().run("test-topic");
  }
}
