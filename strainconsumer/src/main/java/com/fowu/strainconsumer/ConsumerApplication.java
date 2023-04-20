package com.fowu.strainconsumer;

import io.vertx.core.Vertx;

public class ConsumerApplication {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new ConsumerVerticle());
  }
}
