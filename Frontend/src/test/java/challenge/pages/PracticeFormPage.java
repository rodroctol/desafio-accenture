package challenge.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PracticeFormPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By firstNameField = By.id("firstName");
    private final By lastNameField = By.id("lastName");
    private final By emailField = By.id("userEmail");
    private final By genderMale = By.xpath("//label[text()='Male']");
    private final By mobileField = By.id("userNumber");
    private final By subjectInput = By.id("subjectsInput");
    private final By hobbyReading = By.xpath("//label[text()='Reading']");
    private final By uploadPictureInput = By.id("uploadPicture");
    private final By currentAddressField = By.id("currentAddress");
    private final By stateInput = By.id("react-select-3-input");
    private final By cityInput = By.id("react-select-4-input");
    private final By submitButton = By.id("submit");
    private final By modalTitle = By.id("example-modal-sizes-title-lg");
    private final By closeModalButton = By.id("closeLargeModal");

    public PracticeFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillFirstName(String firstName) {
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    public void fillLastName(String lastName) {
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    public void fillEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void chooseGenderMale() {
        driver.findElement(genderMale).click();
    }

    public void fillMobile(String mobileNumber) {
        driver.findElement(mobileField).sendKeys(mobileNumber);
    }

    public void fillSubject(String subject) {
        WebElement input = driver.findElement(subjectInput);
        input.sendKeys(subject);
        input.sendKeys(Keys.ENTER);
    }

    public void chooseHobbyReading() {
        driver.findElement(hobbyReading).click();
    }

    public void uploadFile(String filePath) {
        driver.findElement(uploadPictureInput).sendKeys(filePath);
    }

    public void fillCurrentAddress(String address) {
        driver.findElement(currentAddressField).sendKeys(address);
    }

    public void chooseState(String state) {
        WebElement stateField = driver.findElement(stateInput);
        stateField.sendKeys(state);
        stateField.sendKeys(Keys.ENTER);
    }

    public void chooseCity(String city) {
        WebElement cityField = driver.findElement(cityInput);
        cityField.sendKeys(city);
        cityField.sendKeys(Keys.ENTER);
    }

    public void submitForm() {
        WebElement button = driver.findElement(submitButton);
        button.click();
    }

    public boolean isModalDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(modalTitle)).isDisplayed();
    }

    public String getModalTitleText() {
        return driver.findElement(modalTitle).getText();
    }

    public void closeModal() {
        wait.until(ExpectedConditions.elementToBeClickable(closeModalButton)).click();
    }
}
