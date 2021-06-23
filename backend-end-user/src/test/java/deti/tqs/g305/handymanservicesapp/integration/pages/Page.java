package deti.tqs.g305.handymanservicesapp.integration.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Page {

    protected WebDriver driver;

    public Page(WebDriver driver, String baseUrl) {
        this.driver = driver;
        driver.get(baseUrl);
        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    // Generic methods
    public void close() {
        driver.close();
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }
}
