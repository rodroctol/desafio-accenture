package challenge.tests;

import org.junit.jupiter.api.Test;

import challenge.base.BaseTest;
import challenge.pages.SortablePage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortableTest extends BaseTest {

    @Test
    public void shouldReorderSortableListInAscendingOrder() {
        SortablePage sortablePage = new SortablePage(driver);
        sortablePage.openPage();
        sortablePage.switchToListTab();

        sortablePage.moveItemToTop("Six");

        List<String> expectedOrder = List.of("One", "Two", "Three", "Four", "Five", "Six");
        sortablePage.sortListAscending();

        assertEquals(expectedOrder, sortablePage.getListItemTexts(), "The sortable list should be ordered ascending.");
    }
}
