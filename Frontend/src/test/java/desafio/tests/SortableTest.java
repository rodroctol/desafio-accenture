package desafio.tests;

import desafio.base.BaseTest;
import desafio.pages.SortablePage;
import org.junit.jupiter.api.Test;

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
