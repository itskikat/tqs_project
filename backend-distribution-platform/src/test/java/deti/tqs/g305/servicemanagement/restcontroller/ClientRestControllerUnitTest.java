package deti.tqs.g305.servicemanagement.restcontroller;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.mockito.internal.verification.VerificationModeFactory;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.model.ServiceStatus;
import deti.tqs.g305.servicemanagement.model.ProviderService;
import deti.tqs.g305.servicemanagement.model.BusinessService;
import deti.tqs.g305.servicemanagement.model.Client;
import deti.tqs.g305.servicemanagement.service.ServiceService;

import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


/**
 * ClientRestControllerUnitTest
 */
@WebMvcTest(ClientRestController.class)
public class ClientRestControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ServiceService serviceService;

    @Test
    public void whenPostServiceContract_thenCreateServiceContract( ) {
        
        ServiceContract sc = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.Waiting, new Client(),0);
        when( serviceService.saveServiceContract(any())).thenReturn(sc);

        with().body(sc).when().request("POST", "/api/clients/contracts/new").then().statusCode(201);

    }

}