package deti.tqs.g305.handymanservicesapp.service;

import deti.tqs.g305.handymanservicesapp.configuration.RequestsHelper;
import deti.tqs.g305.handymanservicesapp.exceptions.UnauthorizedException;
import deti.tqs.g305.handymanservicesapp.model.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class GeneralService {

    private static final Logger log = LoggerFactory.getLogger(GeneralService.class);

    @Autowired
    private RequestsHelper requestsHelper;

    @Value("${enduser.apiurl}")
    String apiBaseUrl;

    private RestTemplate restTemplate = new RestTemplate();

    public Map getContracts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(required=false) String status,
        @RequestParam(required=false) Long type,
        @RequestParam(defaultValue = "date") String sort,
        @RequestParam(defaultValue = "ASC") String order,
        HttpServletRequest request
    ) {
        return restTemplate.exchange(apiBaseUrl + "/clients/contracts", HttpMethod.GET, requestsHelper.getEntityWithAuthorization(request.getHeader("Authorization")), Map.class).getBody();
    }

}
