package common.hooks;

import common.context.ScenarioContext;
import common.driver.DriverFactory;
import io.cucumber.java.*;
import org.openqa.selenium.*;

public class UiHooks {
  private final ScenarioContext context;

  public UiHooks(ScenarioContext context) {
    this.context = context;
  }

  @Before(value = "@ui", order = 0)
  public void startBrowser() {
    context.driver = new DriverFactory().create();
  }

  @After(value = "@ui", order = 0)
  public void closeBrowser(Scenario scenario) {
    if (context.driver == null) return;
    try {
      if (scenario.isFailed())
        scenario.attach(
            ((TakesScreenshot) context.driver).getScreenshotAs(OutputType.BYTES),
            "image/png",
            "Failure screenshot");
    } catch (WebDriverException e) {
      scenario.log("Screenshot unavailable: " + e.getMessage());
    } finally {
      context.driver.quit();
      context.driver = null;
    }
  }
}
