package com.fowu.weatherconsumer;

import com.fowu.common.PropertiesHelper;
import java.util.Properties;

public class WeatherConfig {
  static Properties props;

  public static Properties getWeatherProperties() throws Exception {
    props = PropertiesHelper.getProperties();
    props.setProperty("group.id", "weathergroup");
    return props;
  }
}
