package desafio.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class BrowserWindowsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final String originalWindow;

    private final By alertsFrameWindowsCard = By
            .xpath("//h5[text()='Alerts, Frame & Windows']/ancestor::div[contains(@class,'card')]");
    private final By browserWindowsMenu = By.xpath("//span[text()='Browser Windows']");
    private final By pageHeader = By
            .xpath("//div[contains(@class,'main-header') and normalize-space(text())='Browser Windows']");
    private final By newWindowButton = By.id("windowButton");
    private final By samplePageText = By.xpath("//*[normalize-space(text())='This is a sample page']");

    public BrowserWindowsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.originalWindow = driver.getWindowHandle();
    }

    public void openAlertsFrameWindowsSection() {
        WebElement card = wait.until(ExpectedConditions.elementToBeClickable(alertsFrameWindowsCard));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", card);
        card.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(browserWindowsMenu));
    }

    public void openBrowserWindowsMenu() {
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(browserWindowsMenu));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", menu);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menu);
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeader));
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(newWindowButton));
    }

    public void clickNewWindow() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(newWindowButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }

    public String switchToNewWindowAndGetText() {
        wait.until(driver -> driver.getWindowHandles().size() > 1);
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(originalWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
        WebElement sampleText = wait.until(ExpectedConditions.visibilityOfElementLocated(samplePageText));
        return sampleText.getText().trim();
    }

    public void closeNewWindowAndReturn() {
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(originalWindow)) {
                driver.switchTo().window(window);
                driver.close();
                break;
            }
        }
        driver.switchTo().window(originalWindow);
    }
}
