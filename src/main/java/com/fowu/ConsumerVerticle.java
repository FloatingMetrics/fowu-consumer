package com.fowu;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import io.vertx.kafka.client.consumer.KafkaConsumerRecord;

import java.time.Duration;
import java.util.Properties;

public class ConsumerVerticle extends AbstractVerticle {
  private final String topicName = "weather";
  private final int TIME_OUT_MS = 1000;
  private final int POLL_MS = 100;

  @Override
  public void start() throws Exception {
    Properties props = PropertiesHelper.getProperties();
    KafkaConsumer<String, JsonObject> consumer = KafkaConsumer.create(vertx, props);

    consumer.subscribe(topicName).onSuccess(v -> {
      System.out.println("Consumer subscribed");
      poll(consumer);
    });

  }

  private void poll(KafkaConsumer<String, JsonObject> consumer) {
    // Let's poll every second
    vertx.setPeriodic(TIME_OUT_MS,
                      timerId -> consumer.poll(Duration.ofMillis(POLL_MS)).onSuccess(records -> {
//                        System.out.println("On success, " + "records size:" + records.size());
                        for (int i = 0; i < records.size(); i++) {
                          KafkaConsumerRecord<String, JsonObject> record = records.recordAt(i);
                          System.out.println(
                            "key=" + record.key() + ",value=" + record.value() + ",partition=" +
                            record.partition() + ",timestamp=" + record.timestamp() + ",offset=" + record.offset());
                        }
                      }).onFailure(cause -> {
                        System.out.println(
                          "Something went wrong when polling " + cause.toString());
                        cause.printStackTrace();

                        // Stop polling if something went wrong
                        vertx.cancelTimer(timerId);
                      }));
  }
}
