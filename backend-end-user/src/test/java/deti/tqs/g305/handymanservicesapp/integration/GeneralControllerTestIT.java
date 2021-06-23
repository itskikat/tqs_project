package deti.tqs.g305.handymanservicesapp.integration;

import deti.tqs.g305.handymanservicesapp.integration.pages.DashboardPage;
import deti.tqs.g305.handymanservicesapp.integration.pages.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class GeneralControllerTestIT {

    WebDriver driver;

    String baseUrl = "http://localhost:4200";

    @BeforeEach
    void setup(){
        //use FF Driver
        System.setProperty("webdriver.gecko.driver", "/opt/WebDriver/bin/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    void whenBadMatch_thenReturnFeedback() throws InterruptedException {
        DashboardPage page = new DashboardPage(driver, baseUrl);

        TimeUnit time = TimeUnit.SECONDS;
        time.sleep(2);

        // Initial validations
        assertThat(page.getUrl()).contains("/dashboard");

        // Select option without macth
        page.select("Water shortages");
        page.clickMatch();

        // Validate feedback
        assertThat(page.getTitle()).contains("Unfortunately the service you are looking for is not available in your location.");

        page.close();
    }

    @Test
    void wheMatch_thenReturnAndSignContract() throws InterruptedException {
        DashboardPage page = new DashboardPage(driver, baseUrl);

        TimeUnit time = TimeUnit.SECONDS;
        time.sleep(2);

        // Initial validations
        assertThat(page.getUrl()).contains("/dashboard");

        // Select option without match
        page.select("Swimming pool maintenance");
        page.clickMatch();

        // Validate feedback
        assertThat(page.getTitle()).contains("We have found 1 offers that match your requirements!");

        // Sign contract
        page.clickSignContract();
        assertThat(page.getTitle()).contains("You have requested the service with success!");

        page.close();
    }

}
