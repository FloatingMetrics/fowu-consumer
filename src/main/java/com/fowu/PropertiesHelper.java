package com.fowu;

import io.vertx.core.json.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The type PropertiesHelper is a class that represents the value stored
 * in the properties file named config.properties.
 */
public class PropertiesHelper {
  /**
   * Gets a Properties object that contains the keys and values defined
   * in the file src/main/resources/config.properties
   *
   * @return a {@link java.util.Properties} object
   * @throws Exception Thrown if the file config.properties is not available
   *                   in the directory src/main/resources
   */
  public static Properties getProperties() throws Exception {

    Properties props = null;
    // try to load the file config.properties
    try (InputStream input = ConsumerVerticle.class.getClassLoader().getResourceAsStream("configuration" +
        ".properties")) {

      props = new Properties();

      if (input == null) {
        throw new Exception("Sorry, unable to find config.properties");
      }

      // load a properties file from class path, inside static method
      props.load(input);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return props;
  }

  public static JsonObject getDatasourceProperties() {
    JsonObject props = new JsonObject();

    props.put("url", "jdbc:mysql://mysql:3306/fowu");
    props.put("datasourceName", "fowu");
    props.put("username", "fowu_user");
    props.put("password", "1234");
    props.put("max_pool_size", 16);

    return props;
  }

}
