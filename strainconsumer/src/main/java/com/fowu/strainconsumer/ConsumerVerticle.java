package com.fowu.strainconsumer;

import com.fowu.common.PropertiesHelper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import io.vertx.kafka.client.consumer.KafkaConsumerRecord;
import io.vertx.sqlclient.Tuple;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConsumerVerticle extends AbstractVerticle {
  private final String topicName = "strain";
  private final int NUMBER_OF_CONSUMERS = 4;
  private final int TIME_OUT_MS = 1000;
  private final int POLL_MS = 100;


  @Override
  public void start() throws Exception {

    Properties props = StrainConfig.getStrainProperties();
    List<KafkaConsumer<String, JsonObject>> consumers = new ArrayList<>();

    for (int i = 0; i < NUMBER_OF_CONSUMERS; i++) {
      KafkaConsumer<String, JsonObject> consumer = KafkaConsumer.create(vertx, props);
      consumers.add(consumer);
    }

    for (int i = 0; i < NUMBER_OF_CONSUMERS; i++) {
      KafkaConsumer<String, JsonObject> consumer = consumers.get(i);
      int consumerNum = i + 1;

      consumer.subscribe(topicName)
              .onSuccess(v -> {
                System.out.println("Consumer subscribed to topic: " + topicName);
                System.out.println("Custom properties: " + props);
                poll(consumer, consumerNum);
              })
              .onFailure(cause -> System.err.println("Error cannot subscribe to topic: " + cause));
    }
  }

  private Strain toParams(KafkaConsumerRecord<String, JsonObject> record) {
    return record.value().mapTo(Strain.class);
  }

  private void poll(KafkaConsumer<String, JsonObject> consumer, int consumerNum) {

    vertx.setPeriodic(TIME_OUT_MS,
                      timerId -> consumer.poll(Duration.ofMillis(POLL_MS)).onSuccess(records -> {
                        for (int i = 0; i < records.size(); i++) {
                          KafkaConsumerRecord<String, JsonObject> record = records.recordAt(i);
                          System.out.println(
                            "key=" + record.key() + ",value=" + record.value() +
                            ",partition=" + record.partition() + ",timestamp=" + record.timestamp() + ",offset=" +
                            record.offset());

                          Strain strainData = toParams(record);
                          JsonObject datasourceConfig = PropertiesHelper.getDatasourceProperties();
                          JDBCPool pool = JDBCPool.pool(vertx, datasourceConfig);
                          String query = "INSERT INTO strain" + consumerNum + " (captureTime, strain) values (?, ?)";
                          pool
                            .getConnection()
                            .onFailure(e -> {
                              System.out.println("failed to get a connection: " + e.toString());
                            })
                            .onSuccess(conn -> {
                              conn
                                .preparedQuery(query)
                                .execute(
                                  Tuple.of(strainData.getCaptureTime(), strainData.getStrain()))
                                .onFailure(e -> {
                                  System.out.println("failed to execute query: " + e.toString());
                                  conn.close();
                                })
                                .onSuccess(rows -> {
                                  System.out.println("successfully added row " + strainData.getCaptureTime());
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
