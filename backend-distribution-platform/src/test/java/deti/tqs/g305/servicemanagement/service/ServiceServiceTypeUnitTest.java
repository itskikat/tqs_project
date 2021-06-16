package deti.tqs.g305.servicemanagement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.repository.*;
import deti.tqs.g305.servicemanagement.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class ServiceServiceTypeUnitTest {


    @Mock( lenient = true)
    private ServiceTypeRepository serviceTypeRepository;

    @InjectMocks
    private ServiceServiceTypeImpl serviceServiceType;

    ServiceType st;
       

    @BeforeEach
    public void setUp() {
        st = new ServiceType("eletrecista", true);
               
    }

    @Test
    public void whenSaveServiceType_thenServiceTypeShouldBeStored(){

        Mockito.when(serviceTypeRepository.findByName(any())).thenReturn(Optional.empty());
        Mockito.when(serviceTypeRepository.save(st)).thenReturn(st);

        Optional optst = serviceServiceType.addServiceType(st);

        assertThat(optst.get()).isEqualTo(st);
        verify(serviceTypeRepository, times(1)).save(any());
        verify(serviceTypeRepository, times(1)).findByName(any());
    }

    @Test
    public void whenSaveInvalidServiceType_thenServiceTypeShouldNotBeStored(){
        Mockito.when(serviceTypeRepository.findByName(st.getName())).thenReturn(Optional.of(st));

        Optional optst = serviceServiceType.addServiceType(st);

        assertThat(optst).isEqualTo(Optional.empty());
        verify(serviceTypeRepository, times(0)).save(any());
        verify(serviceTypeRepository, times(1)).findByName(any());
    }
    
    

    

}