package common.hooks;

import common.api.client.ResourceApiClient;
import common.context.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApiHooks {
  private final ScenarioContext context;
  private final ResourceApiClient client;

  public ApiHooks(ScenarioContext context, ResourceApiClient client) {
    this.context = context;
    this.client = client;
  }

  @After("@api or @ui")
  public void cleanup(Scenario scenario) {
    List<String> errors = new ArrayList<>();
    List<String> paths = new ArrayList<>(context.cleanupPaths);
    Collections.reverse(paths);
    for (String path : paths) {
      try {
        // Delete only scenario-owned resources through documented API endpoints.
        int status = client.delete(path, context.adminToken).statusCode();
        if (status != 204 && status != 404) errors.add(path + " returned " + status);
      } catch (RuntimeException | AssertionError e) {
        errors.add(path + ": " + e.getMessage());
      }
    }
    if (!errors.isEmpty()) {
      scenario.log("Cleanup failures: " + errors);
      throw new AssertionError("Could not remove scenario records: " + errors);
    }
  }
}
