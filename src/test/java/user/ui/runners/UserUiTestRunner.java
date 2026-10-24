package user.ui.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features/user/ui",
    glue = {"common.stepdefinitions.ui", "common.hooks"},
    tags = "@ui",
    plugin = {"pretty",
      "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm", "html:target/cucumber/user-ui.html", "json:target/cucumber/user-ui.json"},
    monochrome = true)
public class UserUiTestRunner {}
