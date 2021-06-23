package deti.tqs.g305.servicemanagement.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;

@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class BusinessRestControllerITTest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mockMvc(mvc);
    }

    @Test
    @WithMockUser("duke")
    void whenGetServices_thenReturnServices() {
        given()
                .get("api/businesses/services")
        .then()
                .assertThat()
                    .statusCode(200)
                    .and()
                        .body("size()", greaterThan(0));
    }

}
