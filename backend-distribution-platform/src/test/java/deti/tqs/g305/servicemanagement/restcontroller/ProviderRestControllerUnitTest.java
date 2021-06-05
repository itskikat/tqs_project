package deti.tqs.g305.servicemanagement.restcontroller;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import java.io.IOException;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.model.ServiceStatus;
import deti.tqs.g305.servicemanagement.model.ProviderService;
import deti.tqs.g305.servicemanagement.model.BusinessService;
import deti.tqs.g305.servicemanagement.model.Client;
import deti.tqs.g305.servicemanagement.service.ServiceService;
import deti.tqs.g305.servicemanagement.JsonUtil;

import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.*;


/**
 * ProviderRestControllerUnitTest
 */
@WebMvcTest(ProviderRestController.class)
public class ProviderRestControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ServiceService serviceService;


    @Test
    public void whenPutValidServiceContract_thenUpdateServiceContract( ) throws IOException, Exception {

        //TODO
    }

    @Test
    public void whenPutIValidServiceContract_thenReturnBadRequest( ) throws IOException, Exception {

        //TODO
    }

    @Test
    public void whenGetAllServiceContracts_thenReturnProviderServiceContracts() throws IOException, Exception {
        //TODO
    }


}