package deti.tqs.g305.servicemanagement.restcontroller;

import deti.tqs.g305.servicemanagement.model.*;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import deti.tqs.g305.servicemanagement.configuration.BusinessMatcher;


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

    @MockBean
    private BusinessMatcher bm;

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

    // ProviderService
    @Test
    @WithMockUser("duke")
    void whenPostValidProviderService_thenCreateProviderService() throws Exception {
        ProviderService bs = new ProviderService("LOREN ipsam", new Provider(), new ServiceType());

        when(serviceService.saveProviderService(any())).thenReturn(Optional.of(bs));

        mvc.perform(post("/api/provider/services").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(bs)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description", is(bs.getDescription())))
            .andExpect(jsonPath("$.service.hasExtras", is(false)));

        verify(serviceService, times(1)).saveProviderService(any());

    }

    @Test
    @WithMockUser("duke")
    void whenPostInvalidProviderService_thenReturnBadRequest() throws Exception {

        mvc.perform(post("/api/provider/services").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson("bad service")))
                .andExpect(status().isBadRequest());

        verify(serviceService, times(0)).saveProviderService(any());
    }


    @Test
    @WithMockUser("duke")
    void whenPutValidProviderService_thenUpdateProviderService() throws Exception {
        ProviderService bs = new ProviderService(null, new Provider(), new ServiceType());
        bs.setId(2);
        bs.setDescription("HElloo");

        when(serviceService.updateProviderService(anyLong(),any())).thenReturn(Optional.of(bs));

        mvc.perform(put("/api/provider/services/2").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(bs)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(bs.getDescription())));

        verify(serviceService, times(1)).updateProviderService(anyLong(),any());
    }

    @Test
    @WithMockUser("duke")
    void whenPutInvalidProviderService_thenReturnBadRequest() throws Exception {

        mvc.perform(put("/api/provider/services/2").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson("another bad service")))
                .andExpect(status().isBadRequest());

        verify(serviceService, times(0)).updateProviderService(anyLong(),any());
    }


    @Test
    @WithMockUser("duke")
    void whenDeleteValidProviderService_thenDeleteProviderService() throws Exception {
        ProviderService bs = new ProviderService(null, new Provider(), new ServiceType());
        bs.setId(2);

        when(serviceService.deleteProviderService(anyLong())).thenReturn(true);

        mvc.perform(delete("/api/provider/services/delete/" + bs.getId()))
                .andExpect(status().isOk());

        verify(serviceService, times(1)).deleteProviderService(anyLong());
    }

    @Test
    @WithMockUser("duke")
    public void whenGetAllProviderServices_thenReturnProviderServices() throws  Exception {

        Provider p = new Provider();

        ProviderService bs = new ProviderService();
        bs.setProvider(p);
        ProviderService bs2 = new ProviderService();
        bs2.setProvider(p);
        ProviderService bs3 = new ProviderService();
        bs3.setProvider(p);

        List<ProviderService> listProviderService = new ArrayList<>();
        listProviderService.add(bs);
        listProviderService.add(bs2);
        listProviderService.add(bs3);

        Pageable page = PageRequest.of(10,10);
        Page<ProviderService> optProviderServices = new PageImpl(listProviderService, page, 1L);

        when(serviceService.getProviderProviderServices(any(), any(), eq(Optional.empty()))).thenReturn(optProviderServices);

        mvc.perform(get("/api/provider/services").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(optProviderServices)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(3)));

        verify(serviceService, times(1)).getProviderProviderServices(any(), any(), eq(Optional.empty()));
    }

    @Test
    @WithMockUser("duke")
    public void whenGetExistentProviderService_thenReturnProviderService() throws  Exception {
        ProviderService bs = new ProviderService(null, new Provider(), new ServiceType());
        bs.setId(2);
        bs.setDescription("I have tests");

        when(serviceService.getProviderService(any(), anyLong())).thenReturn(Optional.of(bs));

        mvc.perform(get("/api/provider/services/" + bs.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(bs.getDescription())));

        verify(serviceService, times(1)).getProviderService(any(), anyLong());
    }

    @Test
    @WithMockUser("duke")
    public void whenGetInexistentProviderService_thenReturnBadRequest() throws  Exception {
        when(serviceService.getProviderService(any(), anyLong())).thenReturn(Optional.empty());

        mvc.perform(get("/api/provider/services/9283724"))
                .andExpect(status().isBadRequest());

        verify(serviceService, times(1)).getProviderService(any(), anyLong());
    }

}