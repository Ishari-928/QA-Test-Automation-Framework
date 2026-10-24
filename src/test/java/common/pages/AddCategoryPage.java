package common.pages;

import org.openqa.selenium.*;

public class AddCategoryPage extends BasePage {
  public AddCategoryPage(WebDriver driver) {
    super(driver);
  }

  public void enterCategoryName(String name) {
    WebElement input = visible(By.id("name"));
    input.clear();
    input.sendKeys(name);
  }

  public void clickSave() {
    click(By.cssSelector("form[action='/ui/categories/add'] button[type='submit']"));
  }

  public String getErrorMessage() {
    return visible(By.cssSelector("input#name + .invalid-feedback")).getText();
  }

  public void cancel() {
    click(By.cssSelector("a.btn-secondary[href='/ui/categories']"));
    awaitPath("/ui/categories");
  }
}
