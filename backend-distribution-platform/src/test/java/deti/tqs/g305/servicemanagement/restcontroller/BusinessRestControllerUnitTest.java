package deti.tqs.g305.servicemanagement.restcontroller;

import deti.tqs.g305.servicemanagement.JsonUtil;
import deti.tqs.g305.servicemanagement.model.Business;
import deti.tqs.g305.servicemanagement.model.BusinessService;
import deti.tqs.g305.servicemanagement.model.ServiceType;
import deti.tqs.g305.servicemanagement.service.ServiceService;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;



/**
 * BusinessRestControllerUnitTest
 */

@WebMvcTest(BusinessRestController.class)
class BusinessRestControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ServiceService serviceService;

    @Test
    void whenPostValidBusinessService_thenCreateBusinessService() throws Exception {
        BusinessService bs = new BusinessService(0, new ServiceType(), new Business());

        when(serviceService.saveBusinessService(any())).thenReturn(bs);

        mvc.perform(post("/api/businesses/services").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(bs)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(0)))
                .andExpect(jsonPath("$.service.hasExtras", is(false)));

        verify(serviceService, times(1)).saveBusinessService(any());

    }

    @Test
    void whenPostInvalidBusinessService_thenReturnBadRequest() throws Exception {

        mvc.perform(post("/api/businesses/services").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson("bad service")))
                .andExpect(status().isBadRequest());

        verify(serviceService, times(0)).saveBusinessService(any());
    }


}