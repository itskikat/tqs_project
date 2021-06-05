package deti.tqs.g305.servicemanagement.restcontroller;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import java.io.IOException;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import deti.tqs.g305.servicemanagement.JsonUtil;

import org.springframework.beans.factory.annotation.Autowired;

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
    public void whenPostValidServiceContract_thenCreateServiceContract( ) throws IOException, Exception {
        
        ServiceContract sc = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.Waiting, new Client(),0);
        when( serviceService.saveServiceContract(sc)).thenReturn(sc);

        mvc.perform(post("/api/clients/contracts").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(sc)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("Waiting")));

        verify(serviceService, times(1)).saveServiceContract(any());
    }

    @Test
    public void whenPostInvalidServiceContract_thenReturnBadRequest( ) throws IOException, Exception {

        mvc.perform(post("/api/clients/contracts").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson("ups")))
        .andExpect(status().isBadRequest());

        verify(serviceService, times(0)).saveServiceContract(any());
    }


    @Test
    public void whenPutValidServiceContract_thenUpdateServiceContract( ) throws IOException, Exception {

        ServiceContract sc = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.Waiting, new Client(),0);
        sc.setReview(2);
        sc.setId(1);

        when( serviceService.updateServiceContract(anyLong(),any())).thenReturn(Optional.of(sc));
        
        mvc.perform(put("/api/clients/contracts/1").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(sc)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.review", is(2)));
        
        verify(serviceService, times(1)).updateServiceContract(anyLong(),any()); 
    }

    @Test
    public void whenPutIValidServiceContract_thenReturnBadRequest( ) throws IOException, Exception {

        mvc.perform(put("/api/clients/contracts/1").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson("upsie")))
        .andExpect(status().isBadRequest());
       
        verify(serviceService, times(0)).updateServiceContract(anyLong(),any());
    }

    @Test
    public void whenGetAllServiceContracts_thenReturnClientServiceContracts() throws IOException, Exception {
        ServiceContract sc = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.Waiting, new Client(),0);
        ServiceContract sc1 = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.Waiting, new Client(),0);
        ServiceContract sc2 = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.Waiting, new Client(),0);

        List<ServiceContract> listServiceContract = new ArrayList<ServiceContract>();
        listServiceContract.add(sc);
        listServiceContract.add(sc1);
        listServiceContract.add(sc2);

        Optional<List<ServiceContract>> optServiceContracts = Optional.of(listServiceContract);

        when( serviceService.getServiceContracts(anyLong())).thenReturn(optServiceContracts);

        mvc.perform(get("/api/clients/contracts"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)));
       
        verify(serviceService, times(1)).getServiceContracts(anyLong());
    }

    @Test
    public void whenGetValidServiceContract_thenReturnSpesificServiceContract() throws IOException, Exception {
        ServiceContract sc = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.Waiting, new Client(),0);
        
        when( serviceService.getServiceContract(anyLong())).thenReturn(Optional.of(sc));

        mvc.perform(get("/api/clients/contracts/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("Waiting")));
       
        verify(serviceService, times(1)).getServiceContract(anyLong());
    }

    @Test
    public void whenGetInValidServiceContractId_thenReturnNotFound() throws IOException, Exception {
               
        when( serviceService.getServiceContract(anyLong())).thenReturn(Optional.empty());

        mvc.perform(get("/api/clients/contracts/1"))
        .andExpect(status().isNotFound());
       
        verify(serviceService, times(1)).getServiceContract(anyLong());
    }


}