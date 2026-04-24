package challenge.tests;

import org.junit.jupiter.api.Test;

import challenge.base.BaseTest;
import challenge.pages.WebTablesPage;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebTablesTest extends BaseTest {

    @Test
    public void shouldCreateEditAndDeleteWebTableRecord() {
        WebTablesPage webTablesPage = new WebTablesPage(driver);
        webTablesPage.openPage();

        String email = "new.user." + System.currentTimeMillis() + "@example.com";
        webTablesPage.createNewRecord("New", "User", email, "28", "7000", "QA");
        assertTrue(webTablesPage.isRecordPresent(email));

        String updatedEmail = "updated.user." + System.currentTimeMillis() + "@example.com";
        webTablesPage.editRecordByEmail(email, "Updated", "User", updatedEmail, "29", "8000", "Engineering");

        assertTrue(webTablesPage.isRecordPresent(updatedEmail),
                "O email atualizado não foi encontrado na tabela após a edição.");

        webTablesPage.deleteRecordByEmail(updatedEmail);
        assertFalse(webTablesPage.isRecordPresent(updatedEmail));
    }

    @Test
    public void shouldCreateTwelveWebTableRecords() {
        WebTablesPage webTablesPage = new WebTablesPage(driver);
        webTablesPage.openPage();
        webTablesPage.changeRowCount("50");

        List<WebTablesPage.WebTableRecord> records = webTablesPage.createMultipleRecords(12);
        assertTrue(records.size() == 12);
        for (WebTablesPage.WebTableRecord record : records) {
            assertTrue(webTablesPage.isRecordPresent(record.email));
        }
    }

}
