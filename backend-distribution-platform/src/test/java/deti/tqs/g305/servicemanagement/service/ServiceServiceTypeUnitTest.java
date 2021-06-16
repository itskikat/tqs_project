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
    List<ServiceType> stlist;

    @BeforeEach
    public void setUp() {
        st = new ServiceType("eletricista", true);
        ServiceType st2= new ServiceType("canalizador", true);
        ServiceType st3 = new ServiceType("jardinagem", true);
        
        List<ServiceType> stlist= new ArrayList<ServiceType>();
        stlist.add(st);
        stlist.add(st2);
        stlist.add(st3);
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


    @Test
    public void whenGetAllServiceTypes_thenServiceTypesShouldBeStored(){
        Mockito.when(serviceTypeRepository.findAll()).thenReturn(stlist);

        List<ServiceType> serviceList = serviceServiceType.getServiceTypes(Optional.empty());

        assertThat(serviceList).isEqualTo(stlist);
        verify(serviceTypeRepository, times(1)).findAll();
        
    }

    @Test
    public void whenGetAllServiceTypesWithName_thenServiceTypesShouldBeStored(){

        Mockito.when(serviceTypeRepository.findByNameContains(eq("Hello"))).thenReturn(stlist);

        List<ServiceType> serviceList = serviceServiceType.getServiceTypes(Optional.of("Hello"));

        assertThat(serviceList).isEqualTo(stlist);
        verify(serviceTypeRepository, times(1)).findByNameContains(any());
        
    }


    

    

}