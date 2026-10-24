package common.api.spec;

import static io.restassured.config.HttpClientConfig.httpClientConfig;
import static io.restassured.config.RestAssuredConfig.config;

import common.config.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {
  private RequestSpecFactory() {}

  public static RequestSpecification create(String token) {
    int timeout = ConfigReader.integer("api.timeout.ms");
    RequestSpecBuilder b =
        new RequestSpecBuilder()
            .setBaseUri(ConfigReader.get("api.base.url"))
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .setConfig(
                config()
                    .httpClient(
                        httpClientConfig()
                            .setParam("http.connection.timeout", timeout)
                            .setParam("http.socket.timeout", timeout)));
    if (token != null) b.addHeader("Authorization", "Bearer " + token);
    return b.build();
  }
}
