package deti.tqs.g305.handymanservicesapp.integration;

import deti.tqs.g305.handymanservicesapp.integration.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTestIT {

    WebDriver driver;

    String baseUrl = "http://localhost:4200/";

    @BeforeEach
    void setup(){
        //use FF Driver
        System.setProperty("webdriver.gecko.driver", "/opt/WebDriver/bin/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    void whenLogin_rightData_redirectToDashboard() throws InterruptedException {
        // Create page
        LoginPage page = new LoginPage(driver, baseUrl);

        // Initial validations
        assertThat(page.getTitle()).isEqualTo("Sign in");
        assertThat(page.createNewAccountOptionVisible()).isTrue();

        // Fill login form and submit
        page.writeEmail("xpto@ua.pt");
        page.writePassword("abc");
        page.clickLogin();

        TimeUnit time = TimeUnit.SECONDS;
        time.sleep(2);

        // Validate that was redirected to /dashboard
        assertThat(page.getUrl()).contains("/dashboard");

        // Close driver
        page.close();
    }

    @Test
    void whenLogin_wrongData_showError() throws InterruptedException {
        // Create page
        LoginPage page = new LoginPage(driver, baseUrl);

        // Fill login form and submit
        page.writeEmail("xpto@ua.pt");
        page.writePassword("def");
        page.clickLogin();

        // Validate
        assertThat(page.errorMessage()).contains("Invalid username/password");
        assertThat(page.getUrl()).contains("/login");

        // Close driver
        page.close();
    }
}
