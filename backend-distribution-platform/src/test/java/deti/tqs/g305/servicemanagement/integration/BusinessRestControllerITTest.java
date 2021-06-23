package deti.tqs.g305.servicemanagement.integration;

import deti.tqs.g305.servicemanagement.JsonUtil;
import deti.tqs.g305.servicemanagement.model.*;
import deti.tqs.g305.servicemanagement.service.ServiceService;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations = "application-integrationtest.properties")
public class BusinessRestControllerITTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ServiceService serviceService;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    @WithMockUser("duke")
    void whenGetServices_thenReturnServices() throws Exception {
        Business b = new Business();

        BusinessService bs = new BusinessService();
        bs.setBusiness(b);
        BusinessService bs2 = new BusinessService();
        bs2.setBusiness(b);
        BusinessService bs3 = new BusinessService();
        bs3.setBusiness(b);

        List<BusinessService> listBusinessService = new ArrayList<>();
        listBusinessService.add(bs);
        listBusinessService.add(bs2);
        listBusinessService.add(bs3);

        Pageable page = PageRequest.of(10,10);
        Page<BusinessService> optBusinessServices = new PageImpl(listBusinessService, page, 1L);

        when(serviceService.getBusinessBusinessServices(any(), any(), eq(Optional.empty()))).thenReturn(optBusinessServices);


        RestAssuredMockMvc.given()
                                    .accept(ContentType.JSON)
                                    .contentType(ContentType.JSON)
                                    .body(JsonUtil.toJson(optBusinessServices))
                            .when()
                                    .get("api/businesses/services")
                            .then()
                                    .assertThat()
                                        .statusCode(200)
                                        .and()
                                            .body("size()", greaterThan(0))
                                        .and()
                                            .body("$", hasKey("data"))
                                        .and()
                                            .body("data", hasSize(3));
    }

    @Test
    @WithMockUser("duke")
    void whenGetContracts_thenReturnContracts() throws Exception {
        ServiceContract sc = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.WAITING, new Client(),0);
        ServiceContract sc1 = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.WAITING, new Client(),0);
        ServiceContract sc2 = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.WAITING, new Client(),0);

        List<ServiceContract> listServiceContract = new ArrayList<>();
        listServiceContract.add(sc);
        listServiceContract.add(sc1);
        listServiceContract.add(sc2);

        Pageable page = PageRequest.of(10,10);
        Page<ServiceContract> optServiceContracts = new PageImpl(listServiceContract,page, 1L);

        when(serviceService.getServiceContracts(any(), any(), eq("Business"), eq(Optional.empty()), eq(Optional.empty()))).thenReturn(optServiceContracts);


        RestAssuredMockMvc.given()
                                    .accept(ContentType.JSON)
                                    .contentType(ContentType.JSON)
                                    .body(JsonUtil.toJson(optServiceContracts))
                            .when()
                                    .get("api/businesses/contracts")
                            .then()
                                    .assertThat()
                                        .statusCode(200)
                                        .and()
                                            .body("size()", greaterThan(0))
                                        .and()
                                            .body("$", hasKey("data"))
                                        .and()
                                            .body("data", hasSize(3));
    }

    @Test
    void whenGetValidServiceID_thenReturnValidServiceID() throws Exception {
        // TODO
    }

}
