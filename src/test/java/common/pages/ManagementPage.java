package common.pages;

import java.util.List;
import org.openqa.selenium.*;

public class ManagementPage extends BasePage {
  private String module;

  public ManagementPage(WebDriver driver) {
    super(driver);
  }

  private String base() {
    return "/ui/" + module;
  }

  private String formPath() {
    return base() + (module.equals("sales") ? "/new" : "/add");
  }

  public void open(String module) {
    if (!List.of("categories", "plants", "sales").contains(module))
      throw new IllegalArgumentException(module);
    this.module = module;
    openPath(base());
    visible(By.cssSelector("table"));
  }

  public String heading() {
    return visible(By.cssSelector("h3")).getText();
  }

  public boolean tableVisible() {
    return visible(By.cssSelector("table")).isDisplayed();
  }

  public List<String> headers() {
    return driver.findElements(By.cssSelector("table thead th")).stream()
        .map(WebElement::getText)
        .toList();
  }

  public void openForm() {
    click(By.cssSelector("a[href='" + formPath() + "']"));
    awaitPath(formPath());
    visible(By.cssSelector(module.equals("sales") ? "#plantId" : "#name"));
  }

  public boolean formVisible() {
    String[] fields =
        switch (module) {
          case "categories" -> new String[] {"name", "parentId"};
          case "plants" -> new String[] {"name", "categoryId", "price", "quantity"};
          default -> new String[] {"plantId", "quantity"};
        };
    for (String field : fields) if (!visible(By.id(field)).isDisplayed()) return false;
    return true;
  }

  public void cancel() {
    click(By.cssSelector("a.btn-secondary[href='" + base() + "']"));
    awaitPath(base());
  }

  public void submitEmpty() {
    // Empty numeric/select fields are already blank on a fresh form.
    for (WebElement e : driver.findElements(By.cssSelector("form input:not([type='hidden'])")))
      e.clear();
    String action = module.equals("sales") ? base() : formPath();
    click(By.cssSelector("form[action='" + action + "'] button.btn-primary"));
  }

  public String validationText() {
    By errors = By.cssSelector("form .invalid-feedback, form .text-danger");
    wait.until(
        d ->
            d.findElements(errors).stream()
                .anyMatch(e -> e.isDisplayed() && !e.getText().isBlank()));
    return String.join(
        " ",
        driver.findElements(errors).stream()
            .filter(WebElement::isDisplayed)
            .map(WebElement::getText)
            .toList());
  }

  public boolean createLinkAbsent() {
    return driver.findElements(By.cssSelector("a[href='" + formPath() + "']")).stream()
        .noneMatch(WebElement::isDisplayed);
  }
}
