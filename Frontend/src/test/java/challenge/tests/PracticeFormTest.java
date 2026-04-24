package challenge.tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import challenge.base.BaseTest;
import challenge.pages.PracticeFormPage;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PracticeFormTest extends BaseTest {

    @Test
    public void shouldSubmitPracticeFormWithUpload() {
        driver.get("https://demoqa.com/automation-practice-form");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

        By firstNameField = By.id("firstName");
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));

        PracticeFormPage formPage = new PracticeFormPage(driver);

        String firstName = "Test" + new Random().nextInt(1000);
        String lastName = "User" + new Random().nextInt(1000);
        String email = "demoqa" + System.currentTimeMillis() % 10000 + "@example.com";
        String mobile = "55119" + (10000000 + new Random().nextInt(90000000));
        String address = "Street Example 123";
        String uploadFilePath = Paths.get("src", "test", "resources", "upload.txt").toAbsolutePath().toString();

        formPage.fillFirstName(firstName);
        formPage.fillLastName(lastName);
        formPage.fillEmail(email);
        formPage.chooseGenderMale();
        formPage.fillMobile(mobile);
        formPage.fillSubject("Maths");
        formPage.chooseHobbyReading();
        formPage.uploadFile(uploadFilePath);
        formPage.fillCurrentAddress(address);
        formPage.chooseState("NCR");
        formPage.chooseCity("Delhi");
        formPage.submitForm();

        assertTrue(formPage.isModalDisplayed(), "The submission confirmation modal should be displayed");
        assertEquals("Thanks for submitting the form", formPage.getModalTitleText());
        formPage.closeModal();
    }
}
