package challenge.tests;

import org.junit.jupiter.api.Test;

import challenge.base.BaseTest;
import challenge.pages.ProgressBarPage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgressBarTest extends BaseTest {

    @Test
    public void shouldStopProgressBarBefore100Percent() throws InterruptedException {
        ProgressBarPage progressBarPage = new ProgressBarPage(driver);
        progressBarPage.navigateToProgressBar();

        progressBarPage.startProgressBar();
        Thread.sleep(2000);
        progressBarPage.stopProgressBar();

        int value = progressBarPage.getCurrentProgressPercentage();
        assertTrue(value > 0 && value < 100,
                "Progress bar should be between 0 and 100%, was: " + value);
    }

    @Test
    public void shouldReachProgressBar100PercentAndReset() throws InterruptedException {
        ProgressBarPage progressBarPage = new ProgressBarPage(driver);
        progressBarPage.navigateToProgressBar();

        progressBarPage.startAndCompleteProgressBar();

        int finalValue = progressBarPage.getCurrentProgressPercentage();
        assertEquals(100, finalValue, "Progress bar should reach 100%");

        progressBarPage.resetProgressBar();

        int resetValue = progressBarPage.getCurrentProgressPercentage();
        assertEquals(0, resetValue, "Progress bar should be reset to 0%");
    }
}
