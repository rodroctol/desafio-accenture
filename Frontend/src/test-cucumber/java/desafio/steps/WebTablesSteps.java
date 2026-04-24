package desafio.runners;

import desafio.pages.WebTablesPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class WebTablesSteps {
    private WebDriver driver;
    private WebTablesPage webTablesPage;
    private int initialRecordCount;
    private List<WebTablesPage.WebTableRecord> createdRecords;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I open the web tables page")
    public void iOpenTheWebTablesPage() {
        webTablesPage = new WebTablesPage(driver);
        webTablesPage.openPage();
        initialRecordCount = webTablesPage.getTotalRecordsCount();
    }

    @When("I add {int} new records")
    public void iAddNewRecords(int count) {
        createdRecords = webTablesPage.createMultipleRecords(count);
    }

    @Then("{int} new records should be present")
    public void newRecordsShouldBePresent(int expectedCount) {
        Assertions.assertNotNull(createdRecords, "Created records list should not be null");
        Assertions.assertEquals(expectedCount, createdRecords.size(), "Expected created records count");
        int finalCount = webTablesPage.getTotalRecordsCount();
        Assertions.assertTrue(finalCount >= initialRecordCount + expectedCount,
                "Expected at least " + expectedCount + " new records, but found " + (finalCount - initialRecordCount));
        for (WebTablesPage.WebTableRecord record : createdRecords) {
            Assertions.assertTrue(webTablesPage.isRecordPresent(record.email),
                    "Expected record with email " + record.email + " to be present");
        }
    }
}
