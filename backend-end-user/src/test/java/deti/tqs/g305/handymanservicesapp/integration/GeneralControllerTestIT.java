package deti.tqs.g305.handymanservicesapp.integration;

import deti.tqs.g305.handymanservicesapp.integration.pages.DashboardPage;
import deti.tqs.g305.handymanservicesapp.integration.pages.Page;
import deti.tqs.g305.handymanservicesapp.integration.pages.PastServicesPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class GeneralControllerTestIT {

    WebDriver driver;

    String baseUrl = "http://deti-tqs-14.ua.pt:81";

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

    @Test
    void checkPastServices() throws InterruptedException {
        PastServicesPage page = new PastServicesPage(driver, baseUrl);

        TimeUnit time = TimeUnit.SECONDS;
        time.sleep(2);

        // Initial validations
        assertThat(page.getUrl()).contains("/past");

        // Validate page content
        assertThat(page.getStatusOptions()).contains("ALL");
        assertThat(page.getStatusOptions()).contains("ACCEPTED");
        assertThat(page.getStatusOptions()).contains("FINNISHED");
        assertThat(page.getStatusOptions()).contains("WAITING");
        assertThat(page.getStatusOptions()).contains("REJECTED");

        assertThat(page.getSortingOptions()).contains("Newest");
        assertThat(page.getSortingOptions()).contains("Oldest");

        assertThat(page.getPageNumber()).isEqualTo("1");

        assertThat(page.getTitle()).isEqualTo("My Services");

        assertThat(page.cardButtonText()).isEqualTo("SERVICE DETAILS");

        page.close();
    }

}
