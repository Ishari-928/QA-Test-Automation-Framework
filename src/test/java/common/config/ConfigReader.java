package common.config;

import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

public final class ConfigReader {
  private static final Properties VALUES = load();

  private ConfigReader() {}

  private static Properties load() {
    Properties p = new Properties();
    try (InputStream in =
        ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
      if (in == null) throw new IllegalStateException("config.properties not found");
      p.load(in);
      return p;
    } catch (java.io.IOException e) {
      throw new IllegalStateException("Cannot read configuration", e);
    }
  }

  public static String get(String key) {
    String value = System.getProperty(key);
    if (value == null) value = System.getenv(key.toUpperCase(Locale.ROOT).replace('.', '_'));
    if (value == null) value = VALUES.getProperty(key);
    if (value == null) throw new IllegalArgumentException("Missing setting: " + key);
    return value;
  }

  public static int integer(String key) {
    return Integer.parseInt(get(key));
  }
}
