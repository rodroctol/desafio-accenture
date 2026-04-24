package challenge.tests;

import org.junit.jupiter.api.Test;

import challenge.base.BaseTest;
import challenge.pages.BrowserWindowsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrowserWindowsTest extends BaseTest {

    @Test
    public void shouldOpenNewWindowAndValidateSamplePage() {
        driver.get("https://demoqa.com/browser-windows");

        BrowserWindowsPage browserWindowsPage = new BrowserWindowsPage(driver);
        browserWindowsPage.waitForPageLoad();
        browserWindowsPage.clickNewWindow();

        String newWindowText = browserWindowsPage.switchToNewWindowAndGetText();
        assertEquals("This is a sample page", newWindowText);

        browserWindowsPage.closeNewWindowAndReturn();
    }
}
