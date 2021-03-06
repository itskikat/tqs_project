package deti.tqs.g305.handymanservicesapp.integration.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends Page {

    public LoginPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl + "/login");
    }

    // Actions
    public void writeEmail(String email) {
        driver.findElement(By.name("username")).sendKeys(email);
    }

    public void writePassword(String password) {
        driver.findElement(By.name("password")).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(By.cssSelector(".bg-blueGray-800")).click();
    }

    // Analyse
    public String getTitle() {
        return driver.findElement(By.cssSelector(".mb-3 > .text-blueGray-500")).getText();
    }

    public boolean createNewAccountOptionVisible() {
        return driver.findElement(By.cssSelector("a > .text-blueGray-500")).isDisplayed();
    }

    public String errorMessage() {
        return driver.findElement(By.cssSelector(".align-middle")).getText();
    }

}
