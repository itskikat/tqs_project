package deti.tqs.g305.handymanservicesapp.integration.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DashboardPage extends LoginPage {

    public DashboardPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
        this.writeEmail("xpto@ua.pt");
        this.writePassword("abc");
        this.clickLogin();
    }

    // Actions
    public void select(String name) {
        WebElement dropdown = driver.findElement(By.id("search-city"));
        dropdown.findElement(By.xpath("//option[. = '" + name + "']")).click();
    }

    public void clickMatch() {
        driver.findElement(By.cssSelector(".mx-4")).click();
    }

    public void clickSignContract() {
        driver.findElement(By.cssSelector(".mt-auto:nth-child(6)")).click();
    }


    // Validators
    public String getTitle() {
        return driver.findElement(By.cssSelector(".text-xl")).getText();
    }

}
