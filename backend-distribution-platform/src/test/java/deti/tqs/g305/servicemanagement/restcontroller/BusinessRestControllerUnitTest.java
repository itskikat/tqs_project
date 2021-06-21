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
                .andExpect(jsonPath("$.price", is(0.0)))
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
                .andExpect(jsonPath("$.price", is(10000.0)));

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

        mvc.perform(delete("/api/businesses/services/delete/" + bs.getId()))
                .andExpect(status().isOk());

        verify(serviceService, times(1)).deleteBusinessService(anyLong());
    }

    @Test
    @WithMockUser("duke")
    public void whenGetAllServiceContracts_thenReturnBusinessServiceContracts() throws  Exception {

        ServiceContract sc = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.WAITING, new Client(),0);
        ServiceContract sc1 = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.WAITING, new Client(),0);
        ServiceContract sc2 = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.WAITING, new Client(),0);

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

    @Test
    @WithMockUser("duke")
    public void whenGetAllBusinessServices_thenReturnBusinessServices() throws  Exception {

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

        mvc.perform(get("/api/businesses/services").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(optBusinessServices)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(3)));

        verify(serviceService, times(1)).getBusinessBusinessServices(any(), any(), eq(Optional.empty()));
    }

    @Test
    @WithMockUser("duke")
    public void whenGetExistentBusinessService_thenReturnBusinessService() throws  Exception {
        BusinessService bs = new BusinessService(0, new ServiceType(), new Business());
        bs.setId(2);
        bs.setPrice(10000);

        when(serviceService.getBusinessService(any(), anyLong())).thenReturn(Optional.of(bs));

        mvc.perform(get("/api/businesses/services/" + bs.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.price", is(bs.getPrice())));

        verify(serviceService, times(1)).getBusinessService(any(), anyLong());
    }

    @Test
    @WithMockUser("duke")
    public void whenGetInexistentBusinessService_thenReturnBadRequest() throws  Exception {
        when(serviceService.getBusinessService(any(), anyLong())).thenReturn(Optional.empty());

        mvc.perform(get("/api/businesses/services/9283724"))
                .andExpect(status().isBadRequest());

        verify(serviceService, times(1)).getBusinessService(any(), anyLong());
    }

    @Test
    @WithMockUser("duke")
    public void whenGetBusinessStatistics_thenReturnStatistics() throws  Exception {

        Business b = new Business();

        ServiceType st = new ServiceType("canalizacao", true);

        BusinessService bs = new BusinessService(10, st, b);
        BusinessService bs1 = new BusinessService(20, st, b);

        ServiceContract sc = new ServiceContract(bs, new ProviderService(), ServiceStatus.FINNISHED, new Client(),0);
        ServiceContract sc1 = new ServiceContract(bs1, new ProviderService(), ServiceStatus.FINNISHED, new Client(),0);

        List<ServiceContract> listServiceContract = new ArrayList<>();
        listServiceContract.add(sc);
        listServiceContract.add(sc1);

        double profit = 30;

        when(serviceService.getBusinessBusinessServiceProfit(any(), eq(Optional.empty()), eq(Optional.empty()))).thenReturn(profit);
        when(serviceService.getTotalBusinessServiceContracts(any(), eq(Optional.empty()), eq(Optional.empty()))).thenReturn(listServiceContract.size());
        when(serviceService.getBusinessMostRequestedServiceType(any(), eq(Optional.empty()), eq(Optional.empty()))).thenReturn(Optional.of(st));

        mvc.perform(get("/api/businesses/statistics").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profit", is(30.0)))
                .andExpect(jsonPath("$.total-contracts", is(listServiceContract.size())))
                .andExpect(jsonPath("$.most-requested-ServiceType.name", is(st.getName())));

        verify(serviceService, times(1)).getBusinessBusinessServiceProfit(any(), eq(Optional.empty()), eq(Optional.empty()));
        verify(serviceService, times(1)).getTotalBusinessServiceContracts(any(), eq(Optional.empty()), eq(Optional.empty()));
        verify(serviceService, times(1)).getBusinessMostRequestedServiceType(any(), eq(Optional.empty()), eq(Optional.empty()));
    }

    @Test
    @WithMockUser("duke")
    public void whenGetBusinessStatisticsDateInterval_thenReturnStatisticsDateInterval() throws  Exception {
        double profit = 30;
        ServiceType st = new ServiceType("canalizacao", true);

        when(serviceService.getBusinessBusinessServiceProfit(any(), any(), any())).thenReturn(profit);
        when(serviceService.getTotalBusinessServiceContracts(any(), any(), any())).thenReturn(3);
        when(serviceService.getBusinessMostRequestedServiceType(any(), any(), any())).thenReturn(Optional.of(st));

        mvc.perform(get("/api/businesses/statistics?start=11/12/2021&end=11/12/2021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profit", is(30.0)))
                .andExpect(jsonPath("$.start_date[0]", is(2021)))
                .andExpect(jsonPath("$.start_date[1]", is(12)))
                .andExpect(jsonPath("$.start_date[2]", is(11)));

        verify(serviceService, times(1)).getBusinessBusinessServiceProfit(any(), any(), any());
        verify(serviceService, times(1)).getTotalBusinessServiceContracts(any(), any(), any());
        verify(serviceService, times(1)).getBusinessMostRequestedServiceType(any(), any(), any());
    }


}