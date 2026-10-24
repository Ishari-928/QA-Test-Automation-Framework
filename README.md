# QA Training App — UI and API Automation

This is a test automation project I built to practise testing a real web application from both the UI and API sides. I wanted to go beyond writing individual test cases and put together a framework that could run different types of tests, organise the results, and make failures easier to investigate.

The application under test is a Spring Boot training application for managing categories, plants and sales. My contribution was designing, implementing and executing the automated test cases using Selenium WebDriver, REST Assured, Cucumber and Java.

I also integrated Allure reporting to track test execution results and investigate failures.

## What the project does

I test the same application in two ways:

- **UI testing:** I use Selenium WebDriver to interact with the application through Chrome, including logging in, opening pages, submitting forms and checking what different users can access.
- **API testing:** I use REST Assured to send requests directly to the backend and check status codes, response data, validation and access permissions.

I wrote the scenarios in Cucumber using `Given`, `When` and `Then` steps. This makes the tests easier to read and helps me keep the scenarios separate from the Java code that runs them.

The framework uses Maven and JUnit 4 to execute the tests. I also integrated Allure so I can review the results in a dashboard rather than relying only on terminal output.

## What I tested

**Authentication:** Logging in as an admin or standard user, handling invalid credentials, and checking required-field validation in the login form. The API tests also check JWT token responses and rejected login requests.

**Categories:** Viewing categories, creating categories, validating category names, and checking access restrictions. I test that a standard user can read category data but cannot create, edit or delete categories through the API. On the UI side, I also check whether restricted actions are hidden or unavailable.

**Plants:** Viewing plant records, opening and validating forms, creating and retrieving plants through the API, and checking invalid values such as a negative price.

**Sales:** Viewing sales, checking form validation, creating sales through the API, checking the calculated total and remaining stock, and rejecting requests that exceed available stock.

I kept the admin and standard-user tests separate because their permissions are different. I also use test data created for individual scenarios where possible, instead of depending on records left behind by earlier tests.

## Tools I used

- **Java 21** for the test code
- **Selenium WebDriver** for browser automation
- **REST Assured** for API requests and assertions
- **Cucumber** for BDD-style scenarios
- **JUnit 4** for test execution
- **Maven** for dependencies and running the suite
- **Allure Report** for test results and failure details
- **Swagger / OpenAPI** as a reference for the API endpoints and request formats
- **IntelliJ IDEA** for development

## Project structure

I organised the tests by role and by testing layer:

```text
src/test/
├── java/
│   ├── admin/
│   │   ├── api/runners/
│   │   └── ui/runners/
│   ├── user/
│   │   ├── api/runners/
│   │   └── ui/runners/
│   └── common/
│       ├── api/
│       ├── config/
│       ├── context/
│       ├── hooks/
│       ├── pages/
│       └── stepdefinitions/
└── resources/
    ├── features/
    │   ├── admin/api/
    │   ├── admin/ui/
    │   ├── user/api/
    │   └── user/ui/
    └── config.properties
```

The feature files describe the scenarios, the step definitions connect those scenarios to Java, and the page classes keep the Selenium locators and browser actions together. Shared setup and cleanup logic lives in the hooks.

## Running the tests

Before running the tests, I start the QA training application and make sure it is accessible at `http://localhost:8008`. The application and its database need to be set up separately; they are not included in this repository.

To run this project, I use **JDK 21**, **Maven** and **Google Chrome**. I also check the application URL and the admin and standard-user credentials in:

```text
src/test/resources/config.properties
```

From the folder containing `pom.xml`, I run the full suite with:

```powershell
mvn clean test
```

I can also run each group separately:

```powershell
# Admin API tests
mvn clean test "-Dtest=AdminApiTestRunner"

# Admin UI tests
mvn clean test "-Dtest=AdminUiTestRunner"

# User API tests
mvn clean test "-Dtest=UserApiTestRunner"

# User UI tests
mvn clean test "-Dtest=UserUiTestRunner"
```

For a single tagged scenario, I can use a Cucumber tag filter. For example:

```powershell
mvn test "-Dtest=AdminUiTestRunner" "-Dcucumber.filter.tags=@TC_ADM_UI_CAT_01"
```

## Test reports

I use **Allure Report** to see which scenarios passed or failed, their execution times and the details behind failures.

After the test run finishes, I open the report with:

```powershell
mvn allure:serve
```

The project also generates Cucumber HTML reports under `target/cucumber/`, including separate reports for admin and user UI/API runs.

One thing I found useful about Allure is that it makes it easier to distinguish an assertion failure from a problem in the test setup or cleanup.

## What I learned

Working on this project helped me understand the difference between testing through a browser and testing an API directly. I also gained practice with role-based access checks, JWT authentication, reusable step definitions, test data setup and cleanup, and investigating failures from Maven logs and Allure reports.

## Notes

- The application under test must be running before the automation starts.
- The example credentials in `config.properties` should be replaced if the local accounts are different. Real credentials should not be committed to a public repository.
- Some UI validation messages and element locators depend on the running application's implementation.
- Test-created sales, plants and categories may have database relationships that affect cleanup.
- The test counts and results above describe one execution, not a guarantee that every future run will produce the same outcome.

## About me

I'm **Ishari Abeysooriya**, a final-year BSc (Hons) Information Technology and Management undergraduate at the University of Moratuwa. I built this project as part of developing my practical software testing and test automation skills.

[Portfolio](https://ishari-abeysooriya.vercel.app) · [GitHub](https://github.com/Ishari-928)
