package desafio.tests;

import desafio.base.BaseTest;
import desafio.pages.LoginPage;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginComSucesso() {
        driver.get("https://demoqa.com/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.realizarLogin("seu_usuario", "SuaSenha@123");
    }
}