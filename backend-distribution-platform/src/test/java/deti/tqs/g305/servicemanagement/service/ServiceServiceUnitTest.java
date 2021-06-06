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

    @Mock( lenient = true)
    private ProviderServiceRepository providerServiceRepository;

    @Mock( lenient = true)
    private BusinessServiceRepository businessServiceRepository;

    @Mock( lenient = true)
    private ClientRepository clientRepository;

    @InjectMocks
    private ServiceServiceImpl serviceService;

    ServiceContract sc_wait;
    ServiceContract sc_accept;
    ServiceContract sc_fin;
    ServiceContract sc_rej;
    

    @BeforeEach
    public void setUp() {
        sc_wait = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.Waiting, new Client(),0);
        Mockito.when(serviceContractRepository.save(sc_wait)).thenReturn(sc_wait);

        sc_accept = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.Accepted, new Client(),0);
        sc_fin = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.Finnished, new Client(),0);
        sc_rej = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.Rejected, new Client(),0);
        sc_accept.setId(sc_wait.getId());
        sc_fin.setId(sc_accept.getId());
        sc_rej.setId(sc_accept.getId());

        Mockito.when(serviceContractRepository.save(sc_accept)).thenReturn(sc_accept);
        Mockito.when(serviceContractRepository.findById(sc_wait.getId())).thenReturn(sc_wait);
        Mockito.when(serviceContractRepository.findById(-99L)).thenReturn(null);
    }

    @Test
    public void whenCreateServiceContract_thenServiceContractShouldBeStored( ){
        
        ServiceContract scfromDB = serviceService.saveServiceContract(sc_wait);

        assertThat(sc_wait).isEqualTo(scfromDB);
        verify(serviceContractRepository, times(1)).save(any());
    }

    @Test
    public void whenUpdateValidServiceContract_thenServiceContractShouldBeUpdated( ){
        ServiceContract sc1fromDB = serviceService.updateServiceContract(sc_accept.getId(),sc_accept).get();

        assertThat(sc_accept).isEqualTo(sc1fromDB);
        verify(serviceContractRepository, times(1)).save(any());
        verify(serviceContractRepository, times(1)).findById(anyLong());
    }

    @Test
    public void whenUpdateInvalidServiceContractID_thenServiceContractShouldBeEmpty( ){
        Optional<ServiceContract> invalidfromDB = serviceService.updateServiceContract(-99L,sc_accept);

        assertThat(invalidfromDB).isEqualTo(Optional.empty());
        verify(serviceContractRepository, times(0)).save(any());
        verify(serviceContractRepository, times(1)).findById(anyLong());
    } 

    
    @Test
    public void whenUpdateInvalidServiceContractStatus_thenServiceContractShouldBeEmpty( ){
        
        long id = sc_wait.getId();

        //should not be able to update from status waiting to finished (has to accept or reject)
        Mockito.when(serviceContractRepository.findById(id)).thenReturn(sc_wait);
        
        Optional<ServiceContract> invalidfromDB = serviceService.updateServiceContract(id,sc_fin);
        assertThat(invalidfromDB).withFailMessage("should not be able to update from status waiting to finished").isEqualTo(Optional.empty());
        verify(serviceContractRepository, times(0)).save(any());
        verify(serviceContractRepository, times(1)).findById(anyLong());

        //should not be able to update from status finished to accept, waiting or rejected
        Mockito.when(serviceContractRepository.findById(id)).thenReturn(sc_fin);
        
        Optional<ServiceContract> invalidfromDB1 = serviceService.updateServiceContract(id,sc_accept);
        Optional<ServiceContract> invalidfromDB2 = serviceService.updateServiceContract(id,sc_wait);
        Optional<ServiceContract> invalidfromDB3 = serviceService.updateServiceContract(id,sc_rej);

        assertThat(invalidfromDB1).withFailMessage("should not be able to update from status finished to accept").isEqualTo(Optional.empty());
        assertThat(invalidfromDB2).withFailMessage("should not be able to update from status finished to waiting").isEqualTo(Optional.empty());
        assertThat(invalidfromDB3).withFailMessage("should not be able to update from status finished to rejected").isEqualTo(Optional.empty());

        verify(serviceContractRepository, times(0)).save(any());
        verify(serviceContractRepository, times(4)).findById(anyLong());

        //should not be able to update from status rejected to accept, waiting or finished
        Mockito.when(serviceContractRepository.findById(id)).thenReturn(sc_rej);
        
        Optional<ServiceContract> invalidfromDB4 = serviceService.updateServiceContract(id,sc_accept);
        Optional<ServiceContract> invalidfromDB5 = serviceService.updateServiceContract(id,sc_wait);
        Optional<ServiceContract> invalidfromDB6 = serviceService.updateServiceContract(id,sc_fin);

        assertThat(invalidfromDB4).withFailMessage("should not be able to update from status rejected to accept").isEqualTo(Optional.empty());
        assertThat(invalidfromDB5).withFailMessage("should not be able to update from status rejected to waiting").isEqualTo(Optional.empty());
        assertThat(invalidfromDB6).withFailMessage("should not be able to update from status rejected to finnished").isEqualTo(Optional.empty());

        verify(serviceContractRepository, times(0)).save(any());
        verify(serviceContractRepository, times(7)).findById(anyLong());

        //should not be able to update from status accept, to waiting or reject
        Mockito.when(serviceContractRepository.findById(id)).thenReturn(sc_accept);
        
        Optional<ServiceContract> invalidfromDB7 = serviceService.updateServiceContract(id,sc_rej);
        Optional<ServiceContract> invalidfromDB8 = serviceService.updateServiceContract(id,sc_wait);


        assertThat(invalidfromDB7).withFailMessage("should not be able to update from status accept to rejected").isEqualTo(Optional.empty());
        assertThat(invalidfromDB8).withFailMessage( "should not be able to update from status accept to waiting").isEqualTo(Optional.empty());

        verify(serviceContractRepository, times(0)).save(any());
        verify(serviceContractRepository, times(9)).findById(anyLong());
    }

    @Test
    public void whenUpdateInvalidServiceContractReview_thenServiceContractShouldBeEmpty( ){

        long id = sc_fin.getId();

        //should not be able to update review when there is already a review
        sc_fin.setReview(4);
        Mockito.when(serviceContractRepository.findById(id)).thenReturn(sc_fin);
        ServiceContract sc_rev = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.Finnished, new Client(),3);
        sc_rev.setId(id);

        Optional<ServiceContract> invalidfromDB = serviceService.updateServiceContract(id,sc_rev);

        assertThat(invalidfromDB).withFailMessage("should not be able to update review when there is already a review").isEqualTo(Optional.empty());

        verify(serviceContractRepository, times(0)).save(any());
        verify(serviceContractRepository, times(1)).findById(anyLong());

        //should not be able to update review when the state is not finished
        Mockito.when(serviceContractRepository.findById(id)).thenReturn(sc_accept);
        Optional<ServiceContract> invalidfromDB1 = serviceService.updateServiceContract(id,sc_rev);

        assertThat(invalidfromDB1).withFailMessage("should not be able to update review when the status is not finished").isEqualTo(Optional.empty());

        verify(serviceContractRepository, times(0)).save(any());
        verify(serviceContractRepository, times(2)).findById(anyLong());
    }
    
    @Test
    public void givenServiceContracts_whenGetServiceContracts_thenReturnServiceContracts( ){
        /*
        List<ServiceContract> scs = new ArrayList<ServiceContract>();
        scs.add(sc_wait);
        scs.add(sc_accept);
        scs.add(sc_fin);

        Mockito.when(businessServiceRepository.findByBusiness(1)).thenReturn(scs);
        Mockito.when(providerServiceRepository.findByProvider(2)).thenReturn(scs);
        Mockito.when(clientRepository.findById(3)).thenReturn(scs);

        List<ServiceContract> scBusinessfromDB = serviceService.getServiceContracts(1);
        List<ServiceContract> scProviderfromDB = serviceService.getServiceContracts(2);
        List<ServiceContract> scClientfromDB = serviceService.getServiceContracts(3);    
        
        assertThat(scBusinessfromDB).isEqualTo(scs);
        assertThat(scProviderfromDB).isEqualTo(scs);
        assertThat(scClientfromDB).isEqualTo(scs);   
        */   
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