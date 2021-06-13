package deti.tqs.g305.servicemanagement.restcontroller;

import deti.tqs.g305.servicemanagement.JsonUtil;
import deti.tqs.g305.servicemanagement.model.Business;
import deti.tqs.g305.servicemanagement.model.BusinessService;
import deti.tqs.g305.servicemanagement.model.ServiceType;
import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.model.ServiceStatus;
import deti.tqs.g305.servicemanagement.model.Client;
import deti.tqs.g305.servicemanagement.model.ProviderService;
import deti.tqs.g305.servicemanagement.service.ServiceService;
import deti.tqs.g305.servicemanagement.service.UserServiceImpl;

import deti.tqs.g305.servicemanagement.configuration.JwtTokenUtil;
import deti.tqs.g305.servicemanagement.configuration.JwtAuthenticationEntryPoint;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;


import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.security.test.context.support.WithMockUser;

/**
 * BusinessRestControllerUnitTest
 */

@WebMvcTest(BusinessRestController.class)
class BusinessRestControllerUnitTest {

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

    @Test
    @WithMockUser("duke")
    void whenPostValidBusinessService_thenCreateBusinessService() throws Exception {
        BusinessService bs = new BusinessService(0, new ServiceType(), new Business());

        when(serviceService.saveBusinessService(any())).thenReturn(Optional.of(bs));

        mvc.perform(post("/api/businesses/services").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(bs)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(0)))
                .andExpect(jsonPath("$.service.hasExtras", is(false)));

        verify(serviceService, times(1)).saveBusinessService(any());

    }

    @Test
    @WithMockUser("duke")
    void whenPostInvalidBusinessService_thenReturnBadRequest() throws Exception {

        mvc.perform(post("/api/businesses/services").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson("bad service")))
                .andExpect(status().isBadRequest());

        verify(serviceService, times(0)).saveBusinessService(any());
    }


    @Test
    @WithMockUser("duke")
    void whenPutValidBusinessService_thenUpdateBusinessService() throws Exception {
        BusinessService bs = new BusinessService(0, new ServiceType(), new Business());
        bs.setId(2);
        bs.setPrice(10000);

        when(serviceService.updateBusinessService(anyLong(),any())).thenReturn(Optional.of(bs));

        mvc.perform(put("/api/businesses/services/2").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(bs)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(10000)));

        verify(serviceService, times(1)).updateBusinessService(anyLong(),any());
    }

    @Test
    @WithMockUser("duke")
    void whenPutInvalidBusinessService_thenReturnBadRequest() throws Exception {

        mvc.perform(put("/api/businesses/services/2").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson("another bad service")))
                .andExpect(status().isBadRequest());

        verify(serviceService, times(0)).updateBusinessService(anyLong(),any());
    }


    @Test
    @WithMockUser("duke")
    void whenDeleteValidBusinessService_thenDeleteBusinessService() throws Exception {
        BusinessService bs = new BusinessService(0, new ServiceType(), new Business());
        bs.setId(2);

        when(serviceService.deleteBusinessService(anyLong())).thenReturn(true);

        mvc.perform(delete("/api/businesses/services/delete/"+ String.valueOf(bs.getId())))
                .andExpect(status().isFound());

        verify(serviceService, times(1)).deleteBusinessService(anyLong());
    }

    @Test
    @WithMockUser("duke")
    public void whenGetAllServiceContracts_thenReturnBusinessServiceContracts() throws  Exception {

        ServiceContract sc = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.Waiting, new Client(),0);
        ServiceContract sc1 = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.Waiting, new Client(),0);
        ServiceContract sc2 = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.Waiting, new Client(),0);

        List<ServiceContract> listServiceContract = new ArrayList<ServiceContract>();
        listServiceContract.add(sc);
        listServiceContract.add(sc1);
        listServiceContract.add(sc2);

        Pageable page = PageRequest.of(10,10);
        Page<ServiceContract> optServiceContracts = new PageImpl(listServiceContract,page, 1L);

        when( serviceService.getServiceContracts(any(),any(),eq("Business"),eq(Optional.empty()),eq(Optional.empty()))).thenReturn(optServiceContracts);

        mvc.perform(get("/api/businesses/contracts").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(optServiceContracts)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("data", hasSize(3)));
       
        verify(serviceService, times(1)).getServiceContracts(any(),any(),eq("Business"),eq(Optional.empty()),eq(Optional.empty()));
    }



}