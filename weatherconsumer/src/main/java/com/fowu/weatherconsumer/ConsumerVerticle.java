package com.fowu.weatherconsumer;

import com.fowu.common.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import io.vertx.kafka.client.consumer.KafkaConsumerRecord;
import io.vertx.sqlclient.Tuple;

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

    consumer.subscribe(topicName)
            .onSuccess(v -> {
              System.out.println("Consumer subscribed");
              poll(consumer);
            })
            .onFailure(cause -> System.err.println("Error cannot subscribe to topic: " + cause));
  }

  private Weather toParams(KafkaConsumerRecord<String, JsonObject> record) {
    return record.value().mapTo(Weather.class);
  }

  private void poll(KafkaConsumer<String, JsonObject> consumer) {
    vertx.setPeriodic(TIME_OUT_MS,
                      timerId -> consumer.poll(Duration.ofMillis(POLL_MS)).onSuccess(records -> {
                        for (int i = 0; i < records.size(); i++) {
                          KafkaConsumerRecord<String, JsonObject> record = records.recordAt(i);
                          System.out.println(
                            "key=" + record.key() + ",value=" + record.value() + ",partition=" +
                            record.partition() + ",timestamp=" + record.timestamp() + ",offset=" + record.offset());

                          Weather weather = toParams(record);
                          JsonObject datasourceConfig = PropertiesHelper.getDatasourceProperties();
                          JDBCPool pool = JDBCPool.pool(vertx, datasourceConfig);
                          String query = "INSERT INTO weather (captureTime, waveHeight, wavePeriod, " +
                                         "waveDirection, windSpeed, windDirection) values (?, ?, ?, ?, ?, ?)";
                          pool
                            .getConnection()
                            .onFailure(e -> {
                              System.out.println("failed to get a connection: " + e.toString());
                            })
                            .onSuccess(conn -> {
                              conn
                                .preparedQuery(query)
                                .execute(
                                  Tuple.of(weather.getCaptureTime(), weather.getWaveHeight(), weather.getWavePeriod(),
                                           weather.getWaveDirection(), weather.getWindSpeed(),
                                           weather.getWindDirection()))
                                .onFailure(e -> {
                                  System.out.println("failed to execute query: " + e.toString());
                                  conn.close();
                                })
                                .onSuccess(rows -> {
                                  System.out.println("successfully added row " + weather.getCaptureTime());
                                  conn.close();
                                });
                            });
                        }
                      }).onFailure(cause -> {
                        System.out.println(
                          "Something went wrong when polling " + cause.toString());
                        cause.printStackTrace();

                        vertx.cancelTimer(timerId);
                      }));
  }
}
