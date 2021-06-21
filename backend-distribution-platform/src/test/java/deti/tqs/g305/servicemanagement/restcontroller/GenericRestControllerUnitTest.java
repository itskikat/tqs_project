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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import deti.tqs.g305.servicemanagement.model.ServiceType;
import deti.tqs.g305.servicemanagement.service.ServiceServiceType;
import deti.tqs.g305.servicemanagement.service.UserServiceImpl;
import deti.tqs.g305.servicemanagement.JsonUtil;
import deti.tqs.g305.servicemanagement.configuration.JwtTokenUtil;
import deti.tqs.g305.servicemanagement.configuration.JwtAuthenticationEntryPoint;

import deti.tqs.g305.servicemanagement.configuration.BusinessMatcher;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.*;


/**
 * GenericRestControllerUnitTest
 */
@WebMvcTest(GenericRestController.class)
public class GenericRestControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ServiceServiceType serviceServiceType;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private JwtTokenUtil jwtToken;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuth;

    @MockBean
    private BusinessMatcher bm;

    List<ServiceType> listServiceType;

    @BeforeEach
    public void setUp(){
        ServiceType st = new ServiceType("canalização", true);
        ServiceType st1 = new ServiceType("babysitting", false);
        ServiceType st2 = new ServiceType("jardinagem", true);

        listServiceType = new ArrayList<ServiceType>();
        listServiceType.add(st);
        listServiceType.add(st1);
        listServiceType.add(st2);
    }

    @Test
    @WithMockUser("duke")
    public void whenPostValidServiceType_thenCreateServiceType( ) throws IOException, Exception {
        
        ServiceType st = new ServiceType("eletricista", false);
        when( serviceServiceType.addServiceType(st)).thenReturn(Optional.of(st));

        mvc.perform(post("/api/servicetypes").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(st)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("eletricista")));

        verify(serviceServiceType, times(1)).addServiceType(any());
    }

    @Test
    @WithMockUser("duke")
    public void whenPostInvalidServiceType_thenCreateServiceType( ) throws IOException, Exception {
        
        ServiceType st = new ServiceType("eletricista", false);
        when( serviceServiceType.addServiceType(st)).thenReturn(Optional.empty());

        mvc.perform(post("/api/servicetypes").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(st)))
        .andExpect(status().isBadRequest());

        verify(serviceServiceType, times(1)).addServiceType(any());
    }

    @Test
    @WithMockUser("duke")
    public void whenGetAllServices_thenReturnAllServiceTypes( ) throws IOException, Exception {
        
        when( serviceServiceType.getServiceTypes(Optional.empty())).thenReturn(listServiceType);

        mvc.perform(get("/api/servicetypes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)));

        verify(serviceServiceType, times(1)).getServiceTypes(any());
    }

    @Test
    @WithMockUser("duke")
    public void whenGetAllServicesContainingName_thenReturnAllServiceTypes( ) throws IOException, Exception {
        
        when( serviceServiceType.getServiceTypes(Optional.of("hello"))).thenReturn(listServiceType);

        mvc.perform(get("/api/servicetypes?name=hello"))
        .andExpect(status().isOk());
        //.andExpect(jsonPath("$", hasSize(3)));

        verify(serviceServiceType, times(1)).getServiceTypes(any());
    }


}