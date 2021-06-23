package deti.tqs.g305.handymanservicesapp.integration.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class PastServicesPage extends LoginPage {

    public PastServicesPage(WebDriver driver, String baseUrl) throws InterruptedException {
        super(driver, baseUrl);
        this.writeEmail("xpto@ua.pt");
        this.writePassword("abc");
        this.clickLogin();

        this.driver.get(baseUrl + "/past");
    }

    // Validations
    public String getStatusOptions() {
        return driver.findElement(By.id("search-city")).getText();
    }

    public String getSortingOptions() {
        return driver.findElement(By.cssSelector(".lg\\3Aw-6\\/12:nth-child(2) #search-city")).getText();
    }

    public String getPageNumber() {
        return driver.findElement(By.cssSelector("li > .text-white")).getText();
    }

    public String getTitle() {
        return driver.findElement(By.cssSelector(".text-2xl")).getText();
    }

    public String cardButtonText() {
        return driver.findElement(By.cssSelector(".flex:nth-child(1) > .relative .mt-auto")).getText();
    }
}
