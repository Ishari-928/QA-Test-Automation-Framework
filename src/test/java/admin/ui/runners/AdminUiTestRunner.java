package admin.ui.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features/admin/ui",
    glue = {"common.stepdefinitions.ui", "common.hooks"},
    tags = "@ui",
    plugin = {"pretty",
      "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm", "html:target/cucumber/admin-ui.html", "json:target/cucumber/admin-ui.json"},
    monochrome = true)
public class AdminUiTestRunner {}
