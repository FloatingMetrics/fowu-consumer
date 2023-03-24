package com.fowu;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import io.vertx.kafka.client.consumer.KafkaConsumerRecord;
import io.vertx.kafka.client.consumer.KafkaConsumerRecords;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;


public class ConsumerVerticle extends AbstractVerticle {
  private final String topicName = "weather";
  private final int POLL_MS = 100;

  @Override
  public void start() throws Exception {
    Properties props = PropertiesHelper.getProperties();
    KafkaConsumer<String, JsonObject> consumer = KafkaConsumer.create(vertx, props);
    JsonObject datasourceConfig = PropertiesHelper.getDatasourceProperties();
    JDBCClient jdbc = JDBCClient.create(vertx, datasourceConfig);

    consumer.subscribe(topicName).onSuccess(v -> {
      System.out.println("Consumer subscribed");
      poll(jdbc, consumer);
    });
  }

  private void poll(JDBCClient jdbc, KafkaConsumer<String, JsonObject> consumer) {
    // Use Vert.x implementations Future and Promise instead of callbacks
    // Future: an object that represents the result of an action that may or may not have
    // occurred yet.
    // Promise: "the writeable side of an action that may or may not have occurred yet"

    // Promise variable to hold consumer records
    Promise<KafkaConsumerRecords<String, JsonObject>> pollPromise = Promise.promise();
    // Start polling
    consumer.poll(Duration.ofMillis(POLL_MS), pollPromise);

    //
    pollPromise.future()
               // compose: async map operation
               .compose(records -> {
                 List<Future<UpdateResult>> futures = IntStream
                   .range(0, records.size())
                   .mapToObj(records::recordAt)
                   // Store records in DB
                   .map(record -> persist(jdbc, record)).collect(toList());
                 // CompositeFuture: Handles multiple future results at the same time
                 return CompositeFuture
                   // Wait on all the futures to resolve
                   .all(new ArrayList<>(futures));
               })
               .compose(composite -> {
                 Promise<Void> commitPromise = Promise.promise();
                 consumer.commit(commitPromise);
                 return commitPromise.future();
               })
               .onSuccess(any -> {
                 System.out.println("All messages persisted and committed");
                 poll(jdbc, consumer); // WHY??
               })
               .onFailure(cause -> System.err.println("Error persisting and committing messages: " + cause));
  }

  private Future<UpdateResult> persist(JDBCClient jdbc,
                                       KafkaConsumerRecord<String, JsonObject> record) {
    Promise<UpdateResult> promise = Promise.promise();
    JsonArray params = toParams(record);
    jdbc.updateWithParams("", params, promise);
    return promise.future();
  }

  private JsonArray toParams(KafkaConsumerRecord<String, JsonObject> record) {
    // TODO: convert the record into params for the sql command
    return null;
  }
}
