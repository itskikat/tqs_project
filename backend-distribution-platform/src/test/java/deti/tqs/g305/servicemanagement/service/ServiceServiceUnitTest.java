package deti.tqs.g305.servicemanagement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.repository.*;
import deti.tqs.g305.servicemanagement.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ServiceServiceUnitTest {

    @Mock( lenient = true)
    private ServiceContractRepository serviceContractRepository;

    @InjectMocks
    private ServiceServiceImpl serviceService;

    ServiceContract sc;

    @BeforeEach
    public void setUp() {
        sc = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.Waiting, new Client(),0);
        Mockito.when(serviceContractRepository.save(any())).thenReturn(sc);
        
    }

    @Test
    public void whenCreateServiceContract_thenServiceContractShouldBeStored( ){
        
        ServiceContract scfromDB = serviceService.saveServiceContract(sc);

        assertThat(sc).isEqualTo(scfromDB);
        verify(serviceContractRepository, times(1)).save(any());
    }

    @Test
    public void whenUpdateValidServiceContract_thenServiceContractShouldBeUpdated( ){
        
    }

    @Test
    public void whenUpdateInvalidServiceContractID_thenServiceContractShouldBeEmpty( ){
        
    } 

    
    @Test
    public void whenUpdateInvalidServiceContractStatus_thenServiceContractShouldBeEmpty( ){
        //should not be able to update from status waiting to finished (has to accept or reject)

        //should not be able to update from status finished to accept, waiting or rejected

        //should not be able to update from status rejected to accept, waiting or finished

        //should not be able to update from status accept, to waiting or reject
    }

    @Test
    public void whenUpdateInvalidServiceContractReview_thenServiceContractShouldBeEmpty( ){
        //should not be able to update review when there is already a review

        //should not be able to update review when the state is not finished
    }
    
    @Test
    public void givenServiceContracts_whenGetServiceContracts_thenReturnServiceContracts( ){
        
    } 

    @Test
    public void givenNoServiceContracts_whenGetServiceContracts_thenReturnEmptyList( ){
        
    } 

    @Test
    public void givenServiceContract_whenGetServiceContract_thenReturnServiceContract( ){
        
    } 

    @Test
    public void whenGetServiceContractInvalidContractId_thenServiceContractShouldBeEmpty( ){
        
    } 


    
}