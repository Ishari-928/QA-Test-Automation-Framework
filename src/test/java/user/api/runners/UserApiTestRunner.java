package user.api.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features/user/api",
    glue = {"common.stepdefinitions.api", "common.hooks"},
    tags = "@api",
    plugin = {"pretty",
      "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm", "html:target/cucumber/user-api.html", "json:target/cucumber/user-api.json"},
    monochrome = true)
public class UserApiTestRunner {}
