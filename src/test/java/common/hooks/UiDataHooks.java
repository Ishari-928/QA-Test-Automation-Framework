package common.hooks;

import common.api.client.*;
import common.context.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import java.util.List;
import java.util.Map;

public class UiDataHooks {
  private final ScenarioContext context;
  private final AuthApiClient auth;
  private final ResourceApiClient client;

  public UiDataHooks(ScenarioContext context, AuthApiClient auth, ResourceApiClient client) {
    this.context = context;
    this.auth = auth;
    this.client = client;
  }

  @After(value = "@ui", order = 10)
  public void cleanup(Scenario scenario) {
    if (context.uiCategoryNames.isEmpty()) return;
    String token = auth.tokenFor("admin");
    for (String name : context.uiCategoryNames) {
      String query = java.net.URLEncoder.encode(name, java.nio.charset.StandardCharsets.UTF_8);
      var response = client.get("/api/categories?name=" + query, token);
      response.then().statusCode(200);
      Object body = response.jsonPath().get();
      List<Map<String, Object>> records;
      if (body instanceof List<?>) records = (List<Map<String, Object>>) body;
      else if (body instanceof Map<?, ?>) {
        Map<String, Object> object = (Map<String, Object>) body;
        records = object.get("content") instanceof List<?> ? (List<Map<String, Object>>) object.get("content") : List.of(object);
      } else throw new AssertionError("Unexpected categories response shape");
      for (var row : records)
        if (name.equals(row.get("name"))) {
          long id = ((Number) row.get("id")).longValue();
          client.delete("/api/categories/" + id, token).then().statusCode(204);
          scenario.log("Removed scenario category ID " + id);
        }
    }
  }
}
