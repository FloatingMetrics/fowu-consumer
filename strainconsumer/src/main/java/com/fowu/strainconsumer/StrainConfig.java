package com.fowu.strainconsumer;

import com.fowu.common.PropertiesHelper;

import java.util.Properties;

public class StrainConfig {
  static Properties props;

  public static Properties getStrainProperties() throws Exception {
    props = PropertiesHelper.getProperties();
    props.setProperty("group.id", "straingroup");
    return props;
  }
}
