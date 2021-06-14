package deti.tqs.g305.servicemanagement.restcontroller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.*;
import java.io.IOException;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.model.ServiceStatus;
import deti.tqs.g305.servicemanagement.model.ProviderService;
import deti.tqs.g305.servicemanagement.model.BusinessService;
import deti.tqs.g305.servicemanagement.model.Client;
import deti.tqs.g305.servicemanagement.service.ServiceService;
import deti.tqs.g305.servicemanagement.service.UserServiceImpl;
import deti.tqs.g305.servicemanagement.JsonUtil;
import deti.tqs.g305.servicemanagement.configuration.JwtTokenUtil;
import deti.tqs.g305.servicemanagement.configuration.JwtAuthenticationEntryPoint;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
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

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private JwtTokenUtil jwtToken;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuth;

    private List<ServiceContract> listServiceContract;
    
    @BeforeEach
    public void setUp(){
        ServiceContract sc = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.WAITING, new Client(),0);
        ServiceContract sc1 = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.WAITING, new Client(),0);
        ServiceContract sc2 = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.WAITING, new Client(),0);

        listServiceContract = new ArrayList<ServiceContract>();
        listServiceContract.add(sc);
        listServiceContract.add(sc1);
        listServiceContract.add(sc2);
    }

    @Test
    @WithMockUser("duke")
    public void whenPutValidServiceContract_thenUpdateServiceContract( ) throws IOException, Exception {

        ServiceContract sc = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.WAITING, new Client(),0);
        sc.setReview(2);

        when( serviceService.updateServiceContract(anyLong(),any())).thenReturn(Optional.of(sc));
        
        mvc.perform(put("/api/provider/contracts/"+ String.valueOf( sc.getId() )).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(sc)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.review", is(2)));
        
        verify(serviceService, times(1)).updateServiceContract(anyLong(),any()); 
    }

    @Test
    @WithMockUser("duke")
    public void whenPutIValidServiceContract_thenReturnBadRequest( ) throws IOException, Exception {

        mvc.perform(put("/api/provider/contracts/1").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson("upsie")))
        .andExpect(status().isBadRequest());
       
        verify(serviceService, times(0)).updateServiceContract(anyLong(),any());
    }

    @Test
    @WithMockUser("duke")
    public void whenGetAllServiceContracts_thenReturnClientServiceContracts() throws IOException, Exception {
        Pageable page = PageRequest.of(10,10);
        Page<ServiceContract> optServiceContracts = new PageImpl(listServiceContract,page, 1L);

        when( serviceService.getServiceContracts(any(),any(),eq("Provider"),eq(Optional.empty()),eq(Optional.empty()))).thenReturn(optServiceContracts);

        mvc.perform(get("/api/provider/contracts"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("data", hasSize(3)));
       
        verify(serviceService, times(1)).getServiceContracts(any(),any(),eq("Provider"),eq(Optional.empty()),eq(Optional.empty()));
    }

    @Test
    @WithMockUser("duke")
    public void whenGetAllServiceContractsWithStatusParameter_thenReturnClientServiceContracts() throws IOException, Exception {
        Pageable page = PageRequest.of(10,10);
        Page<ServiceContract> optServiceContracts = new PageImpl(listServiceContract,page, 1L);


        when( serviceService.getServiceContracts(any(),any(),eq("Provider"),eq(Optional.of(ServiceStatus.WAITING)),eq(Optional.empty()))).thenReturn(optServiceContracts);

        mvc.perform(get("/api/provider/contracts?status=Waiting"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("data", hasSize(3)));
       
        verify(serviceService, times(1)).getServiceContracts(any(),any(),eq("Provider"),eq(Optional.of(ServiceStatus.WAITING)),eq(Optional.empty()));
    }

    @Test
    @WithMockUser("duke")
    public void whenGetAllServiceContractsWithTypeParameter_thenReturnClientServiceContracts() throws IOException, Exception {
        Pageable page = PageRequest.of(10,10);
        Page<ServiceContract> optServiceContracts = new PageImpl(listServiceContract,page, 1L);


        when( serviceService.getServiceContracts(any(),any(),eq("Provider"),eq(Optional.empty()),eq(Optional.of(1L)))).thenReturn(optServiceContracts);

        mvc.perform(get("/api/provider/contracts?type=1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("data", hasSize(3)));
       
        verify(serviceService, times(1)).getServiceContracts(any(),any(),eq("Provider"),eq(Optional.empty()),eq(Optional.of(1L)));
    }

    @Test
    @WithMockUser("duke")
    public void whenGetAllServiceContractsWithTypeAndStatusParameter_thenReturnClientServiceContracts() throws IOException, Exception {
        Pageable page = PageRequest.of(10,10);
        Page<ServiceContract> optServiceContracts = new PageImpl(listServiceContract,page, 1L);

        when( serviceService.getServiceContracts(any(),any(),eq("Provider"),eq(Optional.of(ServiceStatus.FINNISHED)),eq(Optional.of(1L)))).thenReturn(optServiceContracts);

        mvc.perform(get("/api/provider/contracts?type=1&status=finnished"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("data", hasSize(3)));
       
        verify(serviceService, times(1)).getServiceContracts(any(),any(),any(),any(),any());
    }

    @Test
    @WithMockUser("duke")
    public void whenGetAllServiceContractsWithInvalidStatusParameter_thenReturnClientServiceContracts() throws IOException, Exception {
        Pageable page = PageRequest.of(10,10);
        Page<ServiceContract> optServiceContracts = new PageImpl(listServiceContract,page, 1L);


        when( serviceService.getServiceContracts(any(),any(),any(),any(), any())).thenReturn(optServiceContracts);

        mvc.perform(get("/api/provider/contracts?type=1&status=Invalid"))
        .andExpect(status().isBadRequest());
       
        verify(serviceService, times(0)).getServiceContracts(any(),any(),any(),any(),any());
    }

    @Test
    @WithMockUser("duke")
    public void whenGetValidServiceContract_thenReturnSpesificServiceContract() throws IOException, Exception {
        ServiceContract sc = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.WAITING, new Client(),0);
        
        when( serviceService.getServiceContract(any(),anyLong())).thenReturn(Optional.of(sc));

        mvc.perform(get("/api/provider/contracts/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("WAITING")));
       
        verify(serviceService, times(1)).getServiceContract(any(), anyLong());
    }

    @Test
    @WithMockUser("duke")
    public void whenGetInValidServiceContractId_thenReturnNotFound() throws IOException, Exception {
               
        when( serviceService.getServiceContract(any(), anyLong())).thenReturn(Optional.empty());

        mvc.perform(get("/api/provider/contracts/1"))
        .andExpect(status().isNotFound());
       
        verify(serviceService, times(1)).getServiceContract(any(),anyLong());
    }

}