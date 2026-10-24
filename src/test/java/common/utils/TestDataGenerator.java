package common.utils;

import java.util.UUID;

public final class TestDataGenerator {
  private TestDataGenerator() {}

  // Category names must be 3-10 characters, so do not use the full UUID.
  public static String categoryName() {
    return "QA" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
  }

  public static String plantName() {
    return "QAPlant" + UUID.randomUUID().toString().substring(0, 8);
  }
}
