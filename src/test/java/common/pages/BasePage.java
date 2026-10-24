package common.pages;

import common.config.ConfigReader;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public abstract class BasePage {
  protected final WebDriver driver;
  protected final WebDriverWait wait;

  protected BasePage(WebDriver driver) {
    if (driver == null) throw new IllegalStateException("Browser not initialized by UiHooks");
    this.driver = driver;
    this.wait =
        new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.integer("timeout.seconds")));
  }

  protected void openPath(String path) {
    driver.get(ConfigReader.get("ui.base.url") + path);
  }

  protected WebElement visible(By locator) {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  protected void click(By locator) {
    wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
  }

  public void awaitPath(String path) {
    wait.until(d -> java.net.URI.create(d.getCurrentUrl()).getPath().equals(path));
  }
}
