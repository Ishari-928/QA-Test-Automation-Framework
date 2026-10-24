package common.pages;

import org.openqa.selenium.*;

public class LoginPage extends BasePage {
  public LoginPage(WebDriver driver) {
    super(driver);
  }

  public void open() {
    openPath("/ui/login");
    visible(By.name("username"));
  }

  public void submit(String username, String password) {
    WebElement u = visible(By.name("username"));
    u.clear();
    u.sendKeys(username);
    WebElement p = visible(By.name("password"));
    p.clear();
    p.sendKeys(password);
    click(By.cssSelector("#loginForm button[type='submit']"));
  }

  public void awaitDashboard() {
    wait.until(d -> java.net.URI.create(d.getCurrentUrl()).getPath().equals("/ui/dashboard"));
    visible(By.cssSelector("body"));
  }

  public String error() {
    return visible(By.cssSelector(".alert-danger")).getText();
  }

  public String fieldError(String name) {
    return visible(By.cssSelector("input[name='" + name + "'] + .invalid-feedback")).getText();
  }
}
