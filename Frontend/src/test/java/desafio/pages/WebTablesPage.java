package desafio.pages;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WebTablesPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By addButton = By.id("addNewRecordButton");
    private final By firstNameField = By.id("firstName");
    private final By lastNameField = By.id("lastName");
    private final By emailField = By.id("userEmail");
    private final By ageField = By.id("age");
    private final By salaryField = By.id("salary");
    private final By departmentField = By.id("department");
    private final By submitButton = By.xpath("//button[text()='Submit']");
    private final By searchBox = By.id("searchBox");
    private final By rowCountSelect = By.cssSelector("select");

    public WebTablesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    @Test
    @Order(1)
    public void openPage() {
        driver.get("https://demoqa.com/webtables");

        wait.until(ExpectedConditions.elementToBeClickable(addButton));
    }

    @Test
    @Order(8)
    public void changeRowCount(String value) {
        WebElement selectElement = driver.findElement(rowCountSelect);
        new org.openqa.selenium.support.ui.Select(selectElement).selectByValue(value = "50");
    }

    @Test
    @Order(2)
    public void createNewRecord(String firstName, String lastName, String email, String age, String salary,
            String department) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                wait.until(ExpectedConditions.elementToBeClickable(addButton)));
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(ageField).sendKeys(age);
        driver.findElement(salaryField).sendKeys(salary);
        driver.findElement(departmentField).sendKeys(department);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(submitButton));

        waitForModalClose();
    }

    @Test
    @Order(3)
    public boolean isRecordPresent(String email) {
        try {
            wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), '" + email + "')]")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Test
    @Order(9)
    public List<WebTableRecord> createMultipleRecords(int count) {
        List<WebTableRecord> createdRecords = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String email = String.format("user%d.%d@test.com", i, System.currentTimeMillis());
            WebTableRecord record = new WebTableRecord("First" + i, "Last" + i, email, String.valueOf(20 + i),
                    String.valueOf(1000 * i), "IT");
            createNewRecord(record.firstName, record.lastName, record.email, record.age, record.salary,
                    record.department);
            createdRecords.add(record);
        }
        return createdRecords;
    }

    public int getTotalRecordsCount() {
        List<WebElement> rows = driver.findElements(By.cssSelector(".rt-tr"));
        return rows.size();
    }

    public void editRecordByEmail(String currentEmail, String firstName, String lastName, String newEmail, String age,
            String salary, String department) {
        search(currentEmail);
        WebElement editBtn = wait.until(ExpectedConditions.elementToBeClickable(getEditButtonByEmail(currentEmail)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editBtn);

        WebElement first = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
        first.clear();
        first.sendKeys(firstName);

        fillField(lastNameField, lastName);
        fillField(emailField, newEmail);
        fillField(ageField, age);
        fillField(salaryField, salary);
        fillField(departmentField, department);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(submitButton));
        waitForModalClose();
        search(newEmail);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), '" + newEmail + "')]")));
        clearSearch();
    }

    private void fillField(By locator, String value) {
        WebElement el = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", el);
        el.sendKeys(value);
    }

    @Test
    @Order(6)
    public void deleteRecordByEmail(String email) {
        search(email);
        WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(getDeleteButtonByEmail(email)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteBtn);
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//td[contains(text(), '" + email + "')]"), 0));
        clearSearch();
    }

    @Test
    @Order(5)
    public void search(String text) {
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        box.clear();
        box.sendKeys(text);
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input'));", box);
    }

    @Test
    @Order(7)
    public void clearSearch() {
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = ''; arguments[0].dispatchEvent(new Event('input'));", box);
        wait.until(ExpectedConditions.attributeToBe(box, "value", ""));
    }

    private void waitForModalClose() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-content")));
    }

    private By getEditButtonByEmail(String email) {
        return By.xpath(
                "//td[contains(text(), '" + email + "')]/following-sibling::td//span[contains(@id,'edit-record')]");
    }

    private By getDeleteButtonByEmail(String email) {
        return By.xpath(
                "//td[contains(text(), '" + email + "')]/following-sibling::td//span[contains(@id,'delete-record')]");
    }

    public static class WebTableRecord {
        public final String firstName, lastName, email, age, salary, department;

        public WebTableRecord(String f, String l, String e, String a, String s, String d) {
            this.firstName = f;
            this.lastName = l;
            this.email = e;
            this.age = a;
            this.salary = s;
            this.department = d;
        }
    }
}