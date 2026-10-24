package common.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CategoryListPage extends BasePage {
  public CategoryListPage(WebDriver driver) {
    super(driver);
  }

  public void navigateToCategoryList() {
    openPath("/ui/categories");
    visible(By.cssSelector("table"));
  }

  public boolean isCategoryTableDisplayed() {
    return visible(By.cssSelector("table")).isDisplayed();
  }

  public void clickAddCategory() {
    click(By.cssSelector("a[href='/ui/categories/add']"));
    awaitPath("/ui/categories/add");
  }

  public void search(String name) {
    WebElement input = visible(By.name("name"));
    input.clear();
    input.sendKeys(name);
    WebElement table = visible(By.cssSelector("table"));
    click(By.cssSelector("form[method='get'] button"));
    wait.until(ExpectedConditions.stalenessOf(table));
    visible(By.cssSelector("table"));
  }

  public boolean hasCategory(String name) {
    return driver.findElements(By.cssSelector("table tbody tr td:nth-child(2)")).stream()
        .anyMatch(e -> e.getText().equals(name));
  }

  public boolean addHidden() {
    return driver.findElements(By.cssSelector("a[href='/ui/categories/add']")).stream()
        .noneMatch(WebElement::isDisplayed);
  }

  public boolean deleteDisabledOrAbsent() {
    return driver.findElements(By.cssSelector("table button[title='Delete']")).stream()
        .noneMatch(e -> e.isDisplayed() && e.isEnabled());
  }

  public String emptyMessage() {
    return visible(By.cssSelector("table tbody td[colspan]")).getText();
  }

  public void openCreateDirectly() {
    openPath("/ui/categories/add");
  }

  public boolean accessDenied() {
    return wait.until(
        d ->
            d.getCurrentUrl().contains("403")
                || d.findElement(By.tagName("body")).getText().contains("Access Denied")
                || d.findElement(By.tagName("body")).getText().contains("Forbidden"));
  }
}
