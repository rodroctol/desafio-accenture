package challenge.tests;

import org.junit.jupiter.api.Test;

import challenge.base.BaseTest;
import challenge.pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginComSucesso() {
        driver.get("https://demoqa.com/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.realizarLogin("seu_usuario", "SuaSenha@123");
    }
}