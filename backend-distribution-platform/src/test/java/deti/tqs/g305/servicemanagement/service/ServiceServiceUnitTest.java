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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        sc_wait.getClient().setEmail("username has to be present to work");
        sc_wait.getProviderService().setId(1L);
        sc_wait.getBusinessService().setId(1L);

        Mockito.when(serviceContractRepository.findById(sc_wait.getId())).thenReturn(null);
        Mockito.when(providerServiceRepository.findById(anyLong())).thenReturn(Optional.of(sc_wait.getProviderService()));
        Mockito.when(businessServiceRepository.findById(anyLong())).thenReturn(Optional.of(sc_wait.getBusinessService()));
        Mockito.when(clientRepository.findByEmail(any())).thenReturn(Optional.of(sc_wait.getClient()));

        ServiceContract scfromDB = serviceService.saveServiceContract(sc_wait).get();

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

        List<ServiceContract> scs = new ArrayList<ServiceContract>();
        scs.add(sc_wait);
        scs.add(sc_accept);
        scs.add(sc_fin);

        Pageable pageReq = PageRequest.of(10,10);
        Page<ServiceContract> page = new PageImpl(scs,pageReq, 1L);

        Mockito.when(serviceContractRepository.findByClientEmail(eq("hello"),any())).thenReturn(page);
        Mockito.when(serviceContractRepository.findByProviderService_Provider_Email(eq("hello"),any())).thenReturn(page);
        Mockito.when(serviceContractRepository.findByBusinessService_Business_Email(eq("hello"),any())).thenReturn(page);

        

        Page<ServiceContract> scBusinessfromDB = serviceService.getServiceContracts("hello",pageReq,"Business");
        Page<ServiceContract> scProviderfromDB = serviceService.getServiceContracts("hello",pageReq, "Provider");
        Page<ServiceContract> scClientfromDB = serviceService.getServiceContracts("hello",pageReq, "Client");    
        
        assertThat(scBusinessfromDB.getContent()).isEqualTo(scs);
        assertThat(scProviderfromDB.getContent()).isEqualTo(scs);
        assertThat(scClientfromDB.getContent()).isEqualTo(scs);   

    } 

    @Test
    public void givenServiceContract_whenGetServiceContract_thenReturnServiceContract( ){
        sc_wait.setClient(new Client("xpto@ua.pt", "abc", "xpto xpta", "lala", LocalDate.now()));
        Optional<ServiceContract> optSc = serviceService.getServiceContract("xpto@ua.pt", sc_wait.getId());
        assertThat(optSc.get()).isEqualTo(sc_wait);

        verify(serviceContractRepository, times(1)).findById(anyLong());
    } 

    @Test
    public void whenGetServiceContractInvalidContractId_thenServiceContractShouldBeEmpty( ){
        Optional<ServiceContract> optSc = serviceService.getServiceContract("xpto", -99L);
        
        assertThat(optSc).isEqualTo(Optional.empty());
        verify(serviceContractRepository, times(1)).findById(anyLong());
    } 

    @Test
    public void whenGetServiceContractInvalidUser_thenServiceContractShouldBeEmpty( ){
        //load service contract with client, business and provider usernames
        sc_wait.setClient(new Client("xpto@ua.pt", "abc", "xpto xpta", "lala", LocalDate.now()));
        ProviderService p = new ProviderService();
        Provider p1 = new Provider();
        p1.setEmail("p1@email.pt");
        p.setProvider(p1);
        BusinessService b = new BusinessService();
        Business b1 = new Business();
        b1.setEmail("p2@email.pt");
        b.setBusiness(b1);
        sc_wait.setProviderService(p);
        sc_wait.setBusinessService(b);
            
        Optional<ServiceContract> optSc = serviceService.getServiceContract("invalid", sc_wait.getId());
        
        assertThat(optSc).isEqualTo(Optional.empty());
        verify(serviceContractRepository, times(1)).findById(anyLong());
    } 


    
}