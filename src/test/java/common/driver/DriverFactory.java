package common.driver;

import common.config.ConfigReader;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class DriverFactory {
  public WebDriver create() {
    boolean headless = Boolean.parseBoolean(ConfigReader.get("headless"));
    WebDriver driver;
    switch (ConfigReader.get("browser").toLowerCase()) {
      case "chrome":
        ChromeOptions chrome = new ChromeOptions();
        if (!ConfigReader.get("browser.binary").isBlank())
          chrome.setBinary(ConfigReader.get("browser.binary"));
        if (!ConfigReader.get("browser.arguments").isBlank())
          chrome.addArguments(ConfigReader.get("browser.arguments").split(","));
        if (headless) chrome.addArguments("--headless=new");
        chrome.addArguments("--window-size=1440,1000");
        driver = new ChromeDriver(chrome);
        break;
      case "edge":
        EdgeOptions edge = new EdgeOptions();
        if (!ConfigReader.get("browser.binary").isBlank())
          edge.setBinary(ConfigReader.get("browser.binary"));
        if (!ConfigReader.get("browser.arguments").isBlank())
          edge.addArguments(ConfigReader.get("browser.arguments").split(","));
        if (headless) edge.addArguments("--headless=new");
        edge.addArguments("--window-size=1440,1000");
        driver = new EdgeDriver(edge);
        break;
      default:
        throw new IllegalArgumentException("Supported browsers: chrome, edge");
    }
    driver.manage().timeouts().implicitlyWait(Duration.ZERO);
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(45));
    return driver;
  }
}
