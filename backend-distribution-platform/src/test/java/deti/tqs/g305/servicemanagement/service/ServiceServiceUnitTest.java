package deti.tqs.g305.servicemanagement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.repository.*;
import deti.tqs.g305.servicemanagement.service.messaging.NotificationController;
import deti.tqs.g305.servicemanagement.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testcontainers.shaded.com.google.common.util.concurrent.Service;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class ServiceServiceUnitTest {

    private static final Logger log = LoggerFactory.getLogger(ServiceServiceUnitTest.class);

    @Mock( lenient = true)
    private ServiceContractRepository serviceContractRepository;

    @Mock( lenient = true)
    private ProviderServiceRepository providerServiceRepository;

    @Mock( lenient = true)
    private BusinessServiceRepository businessServiceRepository;

    @Mock( lenient = true)
    private ClientRepository clientRepository;

    @Mock( lenient = true)
    private ServiceTypeRepository serviceTypeRepository;

    @Mock( lenient = true)
    private NotificationController notificationController;

    @InjectMocks
    private ServiceServiceImpl serviceService;

    ServiceContract sc_wait;
    ServiceContract sc_accept;
    ServiceContract sc_fin;
    ServiceContract sc_rej;

    BusinessService bs_free;
    BusinessService bs_withId;

    Business b;

    ProviderService ps_free;
    ProviderService ps_withId;
    

    @BeforeEach
    public void setUp() {
        Mockito.when(notificationController.send(any())).thenReturn("");

        ProviderService ps = new ProviderService();
        Provider p = new Provider();
        p.setEmail("abc@ua.pt");
        ps.setProvider(p);
        BusinessService bs = new BusinessService();
        b = new Business();
        b.setEmail("def@ua.pt");
        bs.setBusiness(b);
        Client c = new Client();
        c.setEmail("ghi@ua.pt");

        sc_wait = new ServiceContract(bs, ps, ServiceStatus.WAITING, c,0);
        sc_wait.setId(3L);
        Mockito.when(serviceContractRepository.save(sc_wait)).thenReturn(sc_wait);

        sc_accept = new ServiceContract(bs, ps, ServiceStatus.ACCEPTED, c,0);
        sc_fin = new ServiceContract(bs, ps, ServiceStatus.FINNISHED, c,0);
        sc_rej = new ServiceContract(bs, ps, ServiceStatus.REJECTED, c,0);
        sc_accept.setId(sc_wait.getId());
        sc_fin.setId(sc_accept.getId());
        sc_rej.setId(sc_accept.getId());

        Mockito.when(serviceContractRepository.save(sc_accept)).thenReturn(sc_accept);
        Mockito.when(serviceContractRepository.findById(sc_wait.getId())).thenReturn(sc_wait);
        Mockito.when(serviceContractRepository.findById(sc_accept.getId())).thenReturn(sc_accept);
        Mockito.when(serviceContractRepository.findById(-99L)).thenReturn(null);

        // BusinessService
        bs_free = new BusinessService(0, new ServiceType(), new Business());

        Mockito.when(businessServiceRepository.save(bs_free)).thenReturn(bs_free);

        bs_withId = new BusinessService(0, new ServiceType(), new Business());
        bs_withId.setId(2L);
        Mockito.when(businessServiceRepository.save(bs_withId)).thenReturn(bs_withId);
        Mockito.when(businessServiceRepository.findById(bs_withId.getId())).thenReturn(bs_withId);

        Mockito.when(businessServiceRepository.findById(-999L)).thenReturn(null);

        b = new Business();
        b.setEmail("samplegoogleid");

        // ProviderService
        ps_free = new ProviderService(null, new Provider(), new ServiceType());
        Mockito.when(providerServiceRepository.save(ps_free)).thenReturn(ps_free);

        ps_withId = new ProviderService("Loren ipsum", new Provider(), new ServiceType());
        ps_withId.setId(1L);
        Mockito.when(providerServiceRepository.save(ps_withId)).thenReturn(ps_withId);
        Mockito.when(providerServiceRepository.findById(ps_withId.getId())).thenReturn(Optional.of(ps_withId));
        Mockito.when(providerServiceRepository.findById(-999L)).thenReturn(Optional.empty());
    }

    @Test
    public void whenCreateServiceContract_thenServiceContractShouldBeStored( ){
        sc_wait.getClient().setEmail("username has to be present to work");
        sc_wait.getProviderService().setId(1L);
        sc_wait.getBusinessService().setId(1L);

        Mockito.when(serviceContractRepository.findById(sc_wait.getId())).thenReturn(null);
        Mockito.when(providerServiceRepository.findById(anyLong())).thenReturn(Optional.of(sc_wait.getProviderService()));
        Mockito.when(businessServiceRepository.findById(anyLong())).thenReturn(sc_wait.getBusinessService());
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
        ServiceContract sc_rev = new ServiceContract(new BusinessService(), new ProviderService(), ServiceStatus.FINNISHED, new Client(),3);
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



        Page<ServiceContract> scBusinessfromDB = serviceService.getServiceContracts("hello",pageReq,"Business", Optional.empty(),Optional.empty());
        Page<ServiceContract> scProviderfromDB = serviceService.getServiceContracts("hello",pageReq, "Provider", Optional.empty(),Optional.empty());
        Page<ServiceContract> scClientfromDB = serviceService.getServiceContracts("hello",pageReq, "Client", Optional.empty(),Optional.empty());

        assertThat(scBusinessfromDB.getContent()).isEqualTo(scs);
        assertThat(scProviderfromDB.getContent()).isEqualTo(scs);
        assertThat(scClientfromDB.getContent()).isEqualTo(scs);

    }

    @Test
    public void givenServiceContracts_whenGetServiceContractsWithStatus_thenReturnServiceContracts( ){
        List<ServiceContract> scs = new ArrayList<ServiceContract>();
        scs.add(sc_wait);
        scs.add(sc_accept);
        scs.add(sc_fin);

        Pageable pageReq = PageRequest.of(10,10);
        Page<ServiceContract> page = new PageImpl(scs,pageReq, 1L);

        Mockito.when(serviceContractRepository.findByStatusAndClientEmail(eq(ServiceStatus.WAITING),eq("hello"),any())).thenReturn(page);
        Mockito.when(serviceContractRepository.findByStatusAndProviderService_Provider_Email(eq(ServiceStatus.WAITING),eq("hello"),any())).thenReturn(page);

        Page<ServiceContract> scProviderfromDB = serviceService.getServiceContracts("hello",pageReq, "Provider", Optional.of(ServiceStatus.WAITING),Optional.empty());
        Page<ServiceContract> scClientfromDB = serviceService.getServiceContracts("hello",pageReq, "Client", Optional.of(ServiceStatus.WAITING),Optional.empty());

        assertThat(scProviderfromDB.getContent()).isEqualTo(scs);
        assertThat(scClientfromDB.getContent()).isEqualTo(scs);
    }

    @Test
    public void givenServiceContracts_whenGetServiceContractsWithType_thenReturnServiceContracts( ){
        List<ServiceContract> scs = new ArrayList<ServiceContract>();
        scs.add(sc_wait);
        scs.add(sc_accept);
        scs.add(sc_fin);

        Pageable pageReq = PageRequest.of(10,10);
        Page<ServiceContract> page = new PageImpl(scs,pageReq, 1L);

        Mockito.when(serviceContractRepository.findByProviderService_Service_IdAndProviderService_Provider_Email(anyLong(),eq("hello"),any())).thenReturn(page);
        Mockito.when(serviceContractRepository.findByProviderService_Service_IdAndClientEmail(anyLong(),eq("hello"),any())).thenReturn(page);

        Page<ServiceContract> scProviderfromDB = serviceService.getServiceContracts("hello",pageReq, "Provider",Optional.empty(), Optional.of(1L));
        Page<ServiceContract> scClientfromDB = serviceService.getServiceContracts("hello",pageReq, "Client",Optional.empty(), Optional.of(1L));

        assertThat(scProviderfromDB.getContent()).isEqualTo(scs);
        assertThat(scClientfromDB.getContent()).isEqualTo(scs);
 
    }

    @Test
    public void givenServiceContracts_whenGetServiceContractsWithTypeAndStatus_thenReturnServiceContracts( ){
        List<ServiceContract> scs = new ArrayList<ServiceContract>();
        scs.add(sc_wait);
        scs.add(sc_accept);
        scs.add(sc_fin);

        Pageable pageReq = PageRequest.of(10,10);
        Page<ServiceContract> page = new PageImpl(scs,pageReq, 1L);

        Mockito.when(serviceContractRepository.findByStatusAndProviderService_Service_IdAndClientEmail(eq(ServiceStatus.WAITING),anyLong(), eq("hello"),any())).thenReturn(page);
        Mockito.when(serviceContractRepository.findByStatusAndProviderService_Service_IdAndProviderService_Provider_Email(eq(ServiceStatus.WAITING),anyLong(),eq("hello"),any())).thenReturn(page);

        Page<ServiceContract> scProviderfromDB = serviceService.getServiceContracts("hello",pageReq, "Provider", Optional.of(ServiceStatus.WAITING),Optional.of(1L));
        Page<ServiceContract> scClientfromDB = serviceService.getServiceContracts("hello",pageReq, "Client", Optional.of(ServiceStatus.WAITING),Optional.of(1L));

        assertThat(scProviderfromDB.getContent()).isEqualTo(scs);
        assertThat(scClientfromDB.getContent()).isEqualTo(scs);
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


    // PROVIDER SERVICE
    @Test
    void whenCreateProviderService_thenProviderServiceShouldBeStored() {

        ps_free.getService().setId(3L);

        Mockito.when(serviceTypeRepository.findById(anyLong())).thenReturn(ps_free.getService());

        ProviderService bsFromDB = serviceService.saveProviderService(ps_free).get();

        assertThat(ps_free).isEqualTo(bsFromDB);
        verify(providerServiceRepository, times(1)).save(any());
    }

    @Test
    void whenUpdateValidProviderService_thenProviderServiceShouldBeUpdated() {
        ProviderService bsFromDB = serviceService.updateProviderService(ps_withId.getId(), ps_withId).get();

        assertThat(ps_withId).isEqualTo(bsFromDB);

        verify(providerServiceRepository, times(1)).save(any());
        verify(providerServiceRepository, times(1)).findById(anyLong());
    }

    @Test
    void whenUpdateInvalidProviderServiceID_thenProviderServiceShouldBeEmpty() {
        Optional<ProviderService> invalidBsFromDB = serviceService.updateProviderService(-99L, ps_withId);

        assertThat(invalidBsFromDB).isEqualTo(Optional.empty());

        verify(providerServiceRepository, times(0)).save(any());
        verify(providerServiceRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenProviderServices_whenGetProviderServices_thenReturnProviderServices() {
        Provider b = new Provider();
        b.setEmail("samplegoogleid");

        List<ProviderService> bss = new ArrayList<ProviderService>();
        bss.add(ps_free);
        bss.add(ps_withId);

        Pageable mypage = PageRequest.of(10,10);
        Page<ProviderService> page = new PageImpl(bss, mypage, 1L);

        Mockito.when(providerServiceRepository.findByProvider_Email(eq("samplegoogleid") ,any())).thenReturn(page);

        Page<ProviderService> bsBusinessFromDB = serviceService.getProviderProviderServices(b.getEmail(), mypage, Optional.empty());

        assertThat(bsBusinessFromDB.getContent()).isEqualTo(bss);
    }

    @Test
    void givenProviderServices_whenGetProviderServicesByTypeName_thenReturnProviderServices() {
        Provider b = new Provider();
        b.setEmail("samplegoogleid");

        ServiceType st = new ServiceType("myservicetype", true);

        List<ProviderService> bss = new ArrayList<ProviderService>();
        ps_free.setService(st);
        bss.add(ps_free);
        bss.add(ps_withId);

        Pageable mypage = PageRequest.of(10,10);
        Page<ProviderService> page = new PageImpl(bss, mypage, 1L);

        Mockito.when(providerServiceRepository.findByProvider_EmailAndService_NameContains(eq("samplegoogleid") ,any(), any())).thenReturn(page);

        Page<ProviderService> bsBusinessFromDB = serviceService.getProviderProviderServices(b.getEmail(), mypage, Optional.of(st.getName()));

        assertThat(bsBusinessFromDB.getContent()).isEqualTo(bss);
    }

    @Test
    void whenDeleteValidProviderServiceID_thenProviderServiceShouldBeDeleted() {

        when(providerServiceRepository.findById(ps_withId.getId())).thenReturn(Optional.of(ps_withId));

        serviceService.deleteProviderService(ps_withId.getId());

        verify(providerServiceRepository, times(1)).delete(ps_withId);

    }

    @Test
    void whenDeleteInvalidProviderServiceID_thenExceptionShouldBeThrown() {
        assertTrue(!serviceService.deleteProviderService(-99L));
        verify(providerServiceRepository, times(0)).delete(any());

    }


    // BUSINESS SERVICE
    @Test
    void whenCreateBusinessService_thenBusinessServiceShouldBeStored() {
        bs_free.getService().setId(1L);

        ServiceContract sc1 = new ServiceContract();
        ServiceContract sc2 = new ServiceContract();
        List<ServiceContract> scList = new ArrayList<>();
        scList.add(sc1);
        scList.add(sc2);

        bs_free.setServiceContract(scList);

        Mockito.when(serviceTypeRepository.findById(anyLong())).thenReturn(bs_free.getService());
        Mockito.when(serviceContractRepository.findByBusinessServiceId(anyLong())).thenReturn(scList);

        BusinessService bsFromDB = serviceService.saveBusinessService(bs_free).get();

        assertThat(bs_free).isEqualTo(bsFromDB);
        verify(businessServiceRepository, times(1)).save(any());

    }

    @Test
    void whenUpdateValidBusinessService_thenBusinessServiceShouldBeUpdated() {
        BusinessService bsFromDB = serviceService.updateBusinessService(bs_withId.getId(), bs_withId).get();

        assertThat(bs_withId).isEqualTo(bsFromDB);

        verify(businessServiceRepository, times(1)).save(any());
        verify(businessServiceRepository, times(1)).findById(anyLong());
    }

    @Test
    void whenUpdateInvalidBusinessServiceID_thenBusinessServiceShouldBeEmpty() {
        Optional<BusinessService> invalidBsFromDB = serviceService.updateBusinessService(-99L, bs_withId);

        assertThat(invalidBsFromDB).isEqualTo(Optional.empty());

        verify(businessServiceRepository, times(0)).save(any());
        verify(businessServiceRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenBusinessServices_whenGetBusinessBusinessServices_thenReturnBusinessServices() {
        List<BusinessService> bss = new ArrayList<>();
        bss.add(bs_free);
        bss.add(bs_withId);

        Pageable mypage = PageRequest.of(10,10);
        Page<BusinessService> page = new PageImpl(bss, mypage, 1L);

        Mockito.when(businessServiceRepository.findByBusiness_Email(eq("samplegoogleid") ,any())).thenReturn(page);

        Page<BusinessService> bsBusinessFromDB = serviceService.getBusinessBusinessServices(b.getEmail(), mypage, Optional.empty());

        assertThat(bsBusinessFromDB.getContent()).isEqualTo(bss);
    }

    @Test
    void givenBusinessServices_whenGetBusinessBusinessServicesWithType_thenReturnBusinessServices() {
        ServiceType st = new ServiceType("myservicetype", true);

        List<BusinessService> bss = new ArrayList<>();
        bs_free.setService(st);
        bss.add(bs_free);
        bss.add(bs_withId);

        Pageable mypage = PageRequest.of(10,10);
        Page<BusinessService> page = new PageImpl(bss, mypage, 1L);

        Mockito.when(businessServiceRepository.findByBusiness_EmailAndService_NameContains(eq("samplegoogleid") ,any(), any())).thenReturn(page);

        Page<BusinessService> bsBusinessFromDB = serviceService.getBusinessBusinessServices(b.getEmail(), mypage, Optional.of(st.getName()));

        assertThat(bsBusinessFromDB.getContent()).isEqualTo(bss);
    }

    @Test
    void whenDeleteValidBusinessServiceID_thenBusinessServiceShouldBeDeleted() {

        when(businessServiceRepository.findById(bs_withId.getId())).thenReturn(bs_withId);

        serviceService.deleteBusinessService(bs_withId.getId());

        verify(businessServiceRepository, times(1)).delete(bs_withId);
    }

    @Test
    void whenDeleteInvalidBusinessServiceID_thenExceptionShouldBeThrown() {
        assertFalse(serviceService.deleteBusinessService(-99L));
        verify(businessServiceRepository, times(0)).delete(any());
    }

    @Test
    void givenServiceContracts_whenGetBusinessBusinessServicesProfit_thenReturnProfit() {
        List<ServiceContract> listServiceContract = new ArrayList<>();
        listServiceContract.add(sc_accept);
        listServiceContract.add(sc_fin);

        Mockito.when(serviceContractRepository.findByStatusAndBusinessService_Business_Email(any(), any())).thenReturn(listServiceContract);

        double expected = serviceService.getBusinessBusinessServiceProfit(b.getEmail(), Optional.empty(), Optional.empty());

        assertThat(0.0).isEqualTo(expected);
        verify(serviceContractRepository, times(1)).findByStatusAndBusinessService_Business_Email(any(), any());
        
    }

    @Test
    void givenBusinessServiceContracts_whenGetBusinessServiceContracts_thenReturnServiceContracts() {
        bs_withId.setBusiness(b);

        sc_wait.setBusinessService(bs_withId);
        sc_accept.setBusinessService(bs_withId);
        sc_fin.setBusinessService(bs_withId);

        List<ServiceContract> listServiceContract = new ArrayList<>();
        listServiceContract.add(sc_wait);
        listServiceContract.add(sc_accept);
        listServiceContract.add(sc_fin);

        Mockito.when(serviceContractRepository.findByBusinessService_Business_Email(any())).thenReturn(listServiceContract);

        Integer expected = serviceService.getTotalBusinessServiceContracts(b.getEmail(), Optional.empty(), Optional.empty());

        assertThat(listServiceContract.size()).isEqualTo(expected);

        verify(serviceContractRepository, times(1)).findByBusinessService_Business_Email(any());
    }

    @Test
    void givenBusinessBusinessServices_whenGetMostRequestedServiceType_thenReturnMostRequestServiceType() {
        ServiceType st = new ServiceType("canalizacao", true);

        BusinessService bs1 = new BusinessService(0, st, b);
        BusinessService bs2 = new BusinessService(0, new ServiceType(), b);
        bs_withId.setService(st);
        bs_withId.setBusiness(b);

        Mockito.when(businessServiceRepository.findByBusiness_Email_MostRequestedServiceTypeId(any())).thenReturn(st.getId());
        Mockito.when(serviceTypeRepository.findById(anyLong())).thenReturn(st);

        ServiceType expected = serviceService.getBusinessMostRequestedServiceType(b.getEmail(), Optional.empty(), Optional.empty()).get();

        assertThat(st).isEqualTo(expected);

        verify(businessServiceRepository, times(1)).findByBusiness_Email_MostRequestedServiceTypeId(any());
        verify(serviceTypeRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenServiceContracts_whenGetBusinessBusinessServicesProfitDateInterval_thenReturnProfitDateInterval() {
        List<ServiceContract> listServiceContract = new ArrayList<>();
        listServiceContract.add(sc_accept);
        listServiceContract.add(sc_fin);

        Mockito.when(businessServiceRepository.findByBusiness_Email_TotalProfitDateInterval(any(), any(), any())).thenReturn(0.0);

        double expected = serviceService.getBusinessBusinessServiceProfit(b.getEmail(), Optional.of(LocalDate.now().minusWeeks(1)), Optional.of(LocalDate.now().plusWeeks(1)));

        assertThat(0.0).isEqualTo(expected);
        verify(businessServiceRepository, times(1)).findByBusiness_Email_TotalProfitDateInterval(any(), any(), any());
    }

    @Test
    void givenBusinessServiceContracts_whenGetBusinessServiceContractsDateInterval_thenReturnServiceContractsDateInterval() {
        bs_withId.setBusiness(b);

        sc_wait.setBusinessService(bs_withId);
        sc_accept.setBusinessService(bs_withId);
        sc_fin.setBusinessService(bs_withId);

        List<ServiceContract> listServiceContract = new ArrayList<>();
        listServiceContract.add(sc_wait);
        listServiceContract.add(sc_accept);
        listServiceContract.add(sc_fin);

        Mockito.when(businessServiceRepository.findByBusiness_Email_TotalContractsFinishedDateInterval(any(), any(), any())).thenReturn(listServiceContract.size());

        Integer expected = serviceService.getTotalBusinessServiceContracts(b.getEmail(), Optional.of(LocalDate.now().minusWeeks(1)), Optional.of(LocalDate.now().plusWeeks(1)));

        assertThat(listServiceContract.size()).isEqualTo(expected);

        verify(businessServiceRepository, times(1)).findByBusiness_Email_TotalContractsFinishedDateInterval(any(), any(), any());
    }

    @Test
    void givenBusinessBusinessServices_whenGetMostRequestedServiceTypeDateInterval_thenReturnMostRequestServiceTypeDateInterval() {
        ServiceType st = new ServiceType("canalizacao", true);

        BusinessService bs1 = new BusinessService(0, st, b);
        BusinessService bs2 = new BusinessService(0, new ServiceType(), b);
        bs_withId.setService(st);
        bs_withId.setBusiness(b);

        Mockito.when(businessServiceRepository.findByBusiness_Email_MostRequestedServiceTypeIdDateInterval(any(), any(), any())).thenReturn(st.getId());
        Mockito.when(serviceTypeRepository.findById(anyLong())).thenReturn(st);

        ServiceType expected = serviceService.getBusinessMostRequestedServiceType(b.getEmail(), Optional.of(LocalDate.now().minusWeeks(1)), Optional.of(LocalDate.now().plusWeeks(1))).get();

        assertThat(st).isEqualTo(expected);

        verify(businessServiceRepository, times(1)).findByBusiness_Email_MostRequestedServiceTypeIdDateInterval(any(), any(), any());
        verify(serviceTypeRepository, times(1)).findById(anyLong());
    }

    @Test
    void whenGetTotalProfit_ReturnTotalProfit(){
        Mockito.when(providerServiceRepository.getTotalProfit(eq("hello"), any(), any())).thenReturn(6.0);
        
        double profit = serviceService.getTotalProfit("hello", LocalDate.now().minusWeeks(1),  LocalDate.now().plusWeeks(1)).get();

        assertThat(profit).isEqualTo(6.0);

        verify(providerServiceRepository, times(1)).getTotalProfit(any(),any(),any());
    }

    @Test
    void whenGetTotalProfitInvalidDate_ReturnTotalProfit(){
        Mockito.when(providerServiceRepository.getTotalProfit(eq("hello"), any(), any())).thenReturn(6.0);
        
        Optional<Double> profit = serviceService.getTotalProfit("hello", LocalDate.now().plusWeeks(1),  LocalDate.now().minusWeeks(1));

        assertThat(profit).isEqualTo(Optional.empty());

        verify(providerServiceRepository, times(0)).getTotalProfit(any(),any(),any());
    }

    @Test
    void whenGetTotalFinished_ReturnTotalFinished(){
        Mockito.when(providerServiceRepository.getTotalFinished(eq("hello"), any(), any())).thenReturn(6);
        
        int contracts = serviceService.getTotalFinished("hello", LocalDate.now().minusWeeks(1),  LocalDate.now().plusWeeks(1)).get();

        assertThat(contracts).isEqualTo(6);

        verify(providerServiceRepository, times(1)).getTotalFinished(any(),any(),any());
    }

    @Test
    void whenGetTotalFinishedInvalidDate_ReturnTotalFinished(){
        
        Optional<Integer> contracts = serviceService.getTotalFinished("hello", LocalDate.now().plusWeeks(1),  LocalDate.now().minusWeeks(1));

        assertThat(contracts).isEqualTo(Optional.empty());

        verify(providerServiceRepository, times(0)).getTotalProfit(any(),any(),any());
    }

    @Test
    void whenGetTotalMostContractsProviderService_ReturnProviderService(){
        Mockito.when(providerServiceRepository.getTotalMostContractsProviderService(eq("hello"), any(), any())).thenReturn(Long.valueOf(1));
        Mockito.when(providerServiceRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(ps_withId));
    

        Optional<ProviderService> ps = serviceService.getTotalMostContractsProviderService("hello", LocalDate.now().minusWeeks(1),  LocalDate.now().plusWeeks(1));

        assertThat(ps.get()).isEqualTo(ps_withId);

        verify(providerServiceRepository, times(1)).getTotalMostContractsProviderService(any(),any(),any());
        verify(providerServiceRepository, times(1)).findById(Long.valueOf(1));
    }

    @Test
    void whenGetTotalMostContractsProviderServiceInvalidDate_ReturnProviderService(){
        
        Optional<ProviderService> ps = serviceService.getTotalMostContractsProviderService("hello",  LocalDate.now().plusWeeks(1),  LocalDate.now().minusWeeks(1));

        assertThat(ps).isEqualTo(Optional.empty());

        verify(providerServiceRepository, times(0)).getTotalMostContractsProviderService(any(),any(),any());
    }

    @Test
    void whenGetTotalMostProfitProviderService_ReturnProviderService(){
        Mockito.when(providerServiceRepository.getTotalMostProfitProviderService(eq("hello"), any(), any())).thenReturn(Long.valueOf(1) );
        Mockito.when(providerServiceRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(ps_withId));
        
        ProviderService ps = serviceService.getTotalMostProfitProviderService("hello", LocalDate.now().minusWeeks(1),  LocalDate.now().plusWeeks(1)).get();

        assertThat(ps).isEqualTo(ps_withId);

        verify(providerServiceRepository, times(1)).getTotalMostProfitProviderService(any(),any(),any());
        verify(providerServiceRepository, times(1)).findById(Long.valueOf(1));
    }

    @Test
    void whenGetTotalMostProfitProviderServiceInvalidDate_ReturnProviderService(){
        
        Optional<ProviderService> ps = serviceService.getTotalMostContractsProviderService("hello",  LocalDate.now().plusWeeks(1),  LocalDate.now().minusWeeks(1));

        assertThat(ps).isEqualTo(Optional.empty());

        verify(providerServiceRepository, times(0)).getTotalMostProfitProviderService(any(),any(),any());
    }

    @Test
    void whenGetProfitHistory_ReturnProfitHistory(){
        List<Object[]> profit = new ArrayList<Object[]>();
        Timestamp date = Timestamp.valueOf(LocalDateTime.now());
        profit.add( new Object[]{(Object) date, (Object) 2.0 });

        Mockito.when(providerServiceRepository.getProfitHistory(eq("hello"), any() , any())).thenReturn(profit);

        Map<LocalDate,Double> hist = serviceService.getProfitHistory("hello", LocalDate.now().minusWeeks(1),  LocalDate.now().plusWeeks(1)).get();

        assertThat(hist.get(date.toLocalDateTime().toLocalDate())).isEqualTo(2.0);
        verify(providerServiceRepository, times(1)).getProfitHistory(any(),any(),any());
    }

    @Test
    void whenGetProfitHistoryInvalidDate_ReturnProfitHistory(){
        
        Optional<Map<LocalDate,Double>> hist = serviceService.getProfitHistory("hello",   LocalDate.now().plusWeeks(1), LocalDate.now().minusWeeks(1));
        assertThat(hist).isEqualTo(Optional.empty());
        verify(providerServiceRepository, times(0)).getProfitHistory(any(),any(),any());
    }

    @Test
    void whenGetContractsHistory_ReturnContractsHistory(){
        
        List<Object[]> profit = new ArrayList<Object[]>();
        Timestamp date = Timestamp.valueOf(LocalDateTime.now());
        profit.add( new Object[]{(Object) date, (Object) BigInteger.valueOf(2) });

        Mockito.when(providerServiceRepository.getContractsHistory(eq("hello"), any() , any())).thenReturn(profit);

        Map<LocalDate,Integer> hist = serviceService.getContractsHistory("hello", LocalDate.now().minusWeeks(1),  LocalDate.now().plusWeeks(1)).get();

        assertThat(hist.get(date.toLocalDateTime().toLocalDate())).isEqualTo(2);
        verify(providerServiceRepository, times(1)).getContractsHistory(any(),any(),any());

    }

    @Test
    void whenGetContractsHistoryInvalidDate_ReturnContractsHistory(){
        
        Optional<Map<LocalDate,Integer>> hist = serviceService.getContractsHistory("hello",   LocalDate.now().plusWeeks(1), LocalDate.now().minusWeeks(1));
        assertThat(hist).isEqualTo(Optional.empty());
        verify(providerServiceRepository, times(0)).getContractsHistory(any(),any(),any());
    }
}