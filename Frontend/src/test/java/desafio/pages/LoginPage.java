package desafio.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    private By userNameField = By.id("userName");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void realizarLogin(String usuario, String senha) {
        driver.findElement(userNameField).sendKeys(usuario);
        driver.findElement(passwordField).sendKeys(senha);
        driver.findElement(loginButton).click();
    }
}