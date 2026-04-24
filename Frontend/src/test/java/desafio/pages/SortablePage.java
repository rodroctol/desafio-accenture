package desafio.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SortablePage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    private final By listTab = By.id("demo-tab-list");
    private final By listItems = By.cssSelector(".vertical-list-container .list-group-item");

    public SortablePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
    }

    public void openPage() {
        driver.get("https://demoqa.com/sortable");
        wait.until(ExpectedConditions.elementToBeClickable(listTab));
    }

    public void switchToListTab() {
        wait.until(ExpectedConditions.elementToBeClickable(listTab)).click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(listItems));
    }

    public List<String> getListItemTexts() {
        return driver.findElements(listItems)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void moveItemToTop(String itemText) {
        List<String> currentOrder = getListItemTexts();
        if (!currentOrder.isEmpty() && currentOrder.get(0).equals(itemText)) {
            return;
        }

        WebElement source = findListItemByText(itemText);
        WebElement target = driver.findElements(listItems).get(0);
        actions.clickAndHold(source)
                .moveToElement(target, 0, -10)
                .release()
                .perform();

        wait.until(driver -> getListItemTexts().get(0).equals(itemText));
    }

    public void sortListAscending() {
        List<String> currentOrder = getListItemTexts();
        List<String> targetOrder = new ArrayList<>(currentOrder);
        targetOrder.sort(Comparator.comparingInt(this::toSortOrder));

        if (currentOrder.equals(targetOrder)) {
            return;
        }

        for (int i = 0; i < targetOrder.size(); i++) {
            final int index = i;
            final String expectedItem = targetOrder.get(i);
            currentOrder = getListItemTexts();
            if (currentOrder.get(index).equals(expectedItem)) {
                continue;
            }

            WebElement source = findListItemByText(expectedItem);
            WebElement target = driver.findElements(listItems).get(index);
            actions.clickAndHold(source)
                    .moveToElement(target, 0, -10)
                    .release()
                    .perform();

            wait.until(driver -> getListItemTexts().get(index).equals(expectedItem));
        }
    }

    private WebElement findListItemByText(String text) {
        return driver.findElements(listItems)
                .stream()
                .filter(item -> item.getText().equals(text))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("List item not found: " + text));
    }

    private int toSortOrder(String itemText) {
        Map<String, Integer> orderMap = new HashMap<>();
        orderMap.put("One", 1);
        orderMap.put("Two", 2);
        orderMap.put("Three", 3);
        orderMap.put("Four", 4);
        orderMap.put("Five", 5);
        orderMap.put("Six", 6);
        return orderMap.getOrDefault(itemText, Integer.MAX_VALUE);
    }
}
