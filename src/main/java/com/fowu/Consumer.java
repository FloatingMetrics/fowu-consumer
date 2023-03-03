package com.fowu;

import io.vertx.core.Vertx;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import io.vertx.kafka.client.consumer.KafkaConsumerRecord;
import io.vertx.kafka.client.consumer.KafkaConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.Properties;

public class Consumer {
  Vertx vertx = Vertx.vertx();
  private final Properties props = PropertiesHelper.getProperties();
  private final KafkaConsumer<String, String> consumer = KafkaConsumer.create(vertx, props);
  private final int TIME_OUT_MS = 5000;

  public Consumer() throws Exception {
  }

  public void run(String topicName) {
    // Register a handler for handling incoming messages
    consumer.handler(record -> {
      System.out.println(
        "Processing key=" + record.key() + ",value=" + record.value() + ",partition=" +
        record.partition() + ",offset=" + record.offset());
    });

    // Subscribe to topic
    consumer
      .subscribe(topicName)
      .onSuccess(v -> {
        System.out.println("subscribed");
        // Optional: Poll at set interval (instead of using handler!)
      })
      .onFailure(cause -> System.out.println("Could not subscribe " + cause.getMessage()));

    // Process messages
//    for (ConsumerRecord<String, String> record : records)
  }
}
