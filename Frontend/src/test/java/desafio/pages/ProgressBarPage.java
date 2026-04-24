package desafio.pages;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProgressBarPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By startButton = By.id("startStopButton");
    private final By resetButton = By.id("resetButton");
    private final By progressValue = By.cssSelector(".progress-bar");

    public ProgressBarPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    @Order(1)
    public void navigateToProgressBar() {
        driver.get("https://demoqa.com/progress-bar");
        wait.until(ExpectedConditions.elementToBeClickable(startButton));
    }

    @Test
    @Order(2)
    public void startProgressBar() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(startButton));
        button.click();
    }

    public void stopProgressBarAt(int targetPercentage) throws InterruptedException {
        boolean targetReached = false;

        while (!targetReached) {
            int currentValue = getProgressValue();

            if (currentValue >= targetPercentage) {
                stopProgressBar();
                Thread.sleep(500);
                targetReached = true;
            } else {
                Thread.sleep(50);
            }
        }
    }

    public void stopProgressBar() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(startButton));
            button.click();
        } catch (Exception e) {
            try {
                WebElement button = driver.findElement(startButton);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
            } catch (Exception e2) {
                System.out.println("Could not stop progress bar: " + e2.getMessage());
            }
        }
    }

    public int getProgressValue() {
        try {
            WebElement bar = driver.findElement(progressValue);
            String styleAttribute = bar.getAttribute("style");

            if (styleAttribute != null && styleAttribute.contains("width")) {
                String widthStr = styleAttribute.replaceAll(".*width:\\s*([0-9.]+)%.*", "$1");
                try {
                    return (int) Double.parseDouble(widthStr);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }

            String ariaValue = bar.getAttribute("aria-valuenow");
            if (ariaValue != null && !ariaValue.isEmpty()) {
                return Integer.parseInt(ariaValue);
            }

            String text = bar.getText();
            if (text != null && text.contains("%")) {
                String percentStr = text.replaceAll("[^0-9]", "");
                if (!percentStr.isEmpty()) {
                    return Integer.parseInt(percentStr);
                }
            }
        } catch (Exception e) {
            System.out.println("Error getting progress value: " + e.getMessage());
        }

        return 0;
    }

    public boolean isProgressLessThanOrEqual(int percentage) {
        int current = getProgressValue();
        return current <= percentage;
    }

    public void resetProgressBar() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(resetButton));
        button.click();

        wait.until(driver -> getProgressValue() == 0);
    }

    public void startAndCompleteProgressBar() throws InterruptedException {
        startProgressBar();

        boolean completed = false;
        int lastValue = 0;
        int noChangeCount = 0;

        while (!completed) {
            int currentValue = getProgressValue();

            if (currentValue >= 99) {
                Thread.sleep(200);
                if (getProgressValue() >= 100) {
                    stopProgressBar();
                    Thread.sleep(500);
                    completed = true;
                }
            } else if (currentValue == lastValue) {
                noChangeCount++;
                if (noChangeCount > 20) {
                    stopProgressBar();
                    Thread.sleep(500);
                    completed = true;
                }
            } else {
                noChangeCount = 0;
                lastValue = currentValue;
                Thread.sleep(100);
            }
        }
    }

    public int getCurrentProgressPercentage() {
        return getProgressValue();
    }
}
