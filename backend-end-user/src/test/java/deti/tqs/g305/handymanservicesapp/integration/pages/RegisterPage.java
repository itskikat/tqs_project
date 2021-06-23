package deti.tqs.g305.handymanservicesapp.integration.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage extends Page {

    public RegisterPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl + "/register");
    }

    public void close() {
        driver.close();
    }

    // Actions
    public void writeName(String value) {
        driver.findElement(By.id("name")).sendKeys(value);
    }

    public void writeEmail(String value) {
        driver.findElement(By.id("email")).sendKeys(value);
    }

    public void writePassword(String value) {
        driver.findElement(By.id("password")).sendKeys(value);
    }

    public void writeAddress(String value) {
        driver.findElement(By.name("address")).sendKeys(value);
    }

    public void writeBirthdate(String value) {
        driver.findElement(By.name("birthdate")).sendKeys(value);
    }

    public void clickSubmit() {
        driver.findElement(By.cssSelector("a > .text-blueGray-500")).click();
    }

    // Analysers
    public String getTitle() {
        return driver.findElement(By.cssSelector(".mb-0 > .text-blueGray-500")).getText();
    }

    public boolean loginBtnVisible() {
        return driver.findElement(By.cssSelector("a > .text-blueGray-500")).isDisplayed();
    }

    public String getErrorMessage() {
        return driver.findElement(By.cssSelector(".align-middle")).getText();
    }
}
