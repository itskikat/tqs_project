package deti.tqs.g305.handymanservicesapp.service;

import deti.tqs.g305.handymanservicesapp.configuration.RequestsHelper;
import deti.tqs.g305.handymanservicesapp.model.ProviderService;
import deti.tqs.g305.handymanservicesapp.model.ServiceContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GeneralService {

    private static final Logger log = LoggerFactory.getLogger(GeneralService.class);

    @Autowired
    private RequestsHelper requestsHelper;

    @Value("${enduser.apiurl}")
    String apiBaseUrl;

    private RestTemplate restTemplate = new RestTemplate();

    // Past services
    public Map getContracts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(required=false) String status,
        @RequestParam(defaultValue = "date") String sort,
        @RequestParam(defaultValue = "ASC") String order,
        @RequestParam(defaultValue = "10") int size,
        HttpServletRequest request
    ) {
        log.info("getContracts for status {}, sorted by {} {}, page {}", status, sort, order, page);
        StringBuilder query = new StringBuilder(String.format("?page=%d&sort=%s&order=%s&size=%d", page, sort, order, size));
        if (status != null) {
            query.append(String.format("&status=%s", status));
        }
        log.info("query: {}", query.toString());
        return restTemplate.exchange(apiBaseUrl + "/clients/contracts" + query.toString(), HttpMethod.GET, requestsHelper.getEntityWithAuthorization(request.getHeader("Authorization")), Map.class).getBody();
    }

    public ServiceContract getContract(Long id, HttpServletRequest request) {
        return restTemplate.exchange(apiBaseUrl + "/clients/contracts/" + id.toString(), HttpMethod.GET, requestsHelper.getEntityWithAuthorization(request.getHeader("Authorization")), ServiceContract.class).getBody();
    }

    public ServiceContract updateContract(
        Long id,
        ServiceContract sc,
        HttpServletRequest request
    ) {
        HttpEntity<ServiceContract> entity = new HttpEntity<>(sc, requestsHelper.getHeadersWithAuthorization(request.getHeader("Authorization")));
        return restTemplate.exchange(apiBaseUrl + "/clients/contracts/" + id.toString(), HttpMethod.PUT, entity, ServiceContract.class).getBody();
    }

    // Dynamic matching
    public List services(HttpServletRequest request) {  // Returns List<BusinessService>
        return restTemplate.exchange(apiBaseUrl + "/businesses/allservices", HttpMethod.GET, requestsHelper.getEntityWithAuthorization(request.getHeader("Authorization")), List.class).getBody();
    }

    public List match(
            Long id,
            HttpServletRequest request
    ) { // Returns List<ProviderService>
        return restTemplate.exchange(apiBaseUrl + "/clients/matches/" + id.toString(), HttpMethod.GET, requestsHelper.getEntityWithAuthorization(request.getHeader("Authorization")), List.class).getBody();
    }

    public Optional<ProviderService> getService(
            Long providerServiceId,
            HttpServletRequest request
    ) {
        return restTemplate.exchange(apiBaseUrl + "/clients/services/" + providerServiceId.toString(), HttpMethod.GET, requestsHelper.getEntityWithAuthorization(request.getHeader("Authorization")), Optional.class).getBody();
    }

    public ServiceContract createContract(
        @Valid @RequestBody ServiceContract sc,
        HttpServletRequest request
    ) {
        HttpEntity<ServiceContract> entity = new HttpEntity<>(sc, requestsHelper.getHeadersWithAuthorization(request.getHeader("Authorization")));
        return restTemplate.exchange(apiBaseUrl + "/clients/contracts/", HttpMethod.POST, entity, ServiceContract.class).getBody();
    }

}
