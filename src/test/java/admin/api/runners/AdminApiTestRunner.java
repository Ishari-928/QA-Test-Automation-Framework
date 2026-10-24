package admin.api.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features/admin/api",
    glue = {"common.stepdefinitions.api", "common.hooks"},
    tags = "@api",
    plugin = {
      "pretty",
      "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
      "html:target/cucumber/admin-api.html",
      "json:target/cucumber/admin-api.json"
    },
    monochrome = true)
public class AdminApiTestRunner {}
