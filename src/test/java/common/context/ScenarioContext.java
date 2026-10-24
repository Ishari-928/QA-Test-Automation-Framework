package common.context;

import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;

/** PicoContainer creates a fresh instance for each scenario. No static mutable state. */
public class ScenarioContext {
  public WebDriver driver;
  public Response response;
  public String token;
  public String adminToken;
  public long categoryId, subCategoryId, plantId, saleId;
  public String categoryName, plantName;
  public final List<String> uiCategoryNames = new ArrayList<>();
  public final List<String> cleanupPaths = new ArrayList<>();
}
