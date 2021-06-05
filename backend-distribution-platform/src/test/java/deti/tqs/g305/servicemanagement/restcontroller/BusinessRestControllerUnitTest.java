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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;



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


    @Test
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
    void whenPutInvalidBusinessService_thenReturnBadRequest() throws Exception {

        mvc.perform(put("/api/businesses/services/2").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson("another bad service")))
                .andExpect(status().isBadRequest());

        verify(serviceService, times(0)).updateBusinessService(anyLong(),any());
    }


    @Test
    void whenDeleteValidBusinessService_thenDeleteBusinessService() throws Exception {
        BusinessService bs = new BusinessService(0, new ServiceType(), new Business());
        bs.setId(2);

        when(serviceService.deleteBusinessService(anyLong())).thenReturn(anyString());

        mvc.perform(delete("/api/businesses/services/delete/{id}", bs.getId()))
                .andExpect(status().isFound());

        verify(serviceService, times(1)).deleteBusinessService(anyLong());
    }

    @Test
    public void whenGetAllServiceContracts_thenReturnBusinessServiceContracts() throws  Exception {
        //TODO
    }

    @Test
    public void whenGetAllServiceContractsInvaildId_thenReturnNotFound() throws  Exception {
        //TODO
    }

}