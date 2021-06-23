package deti.tqs.g305.servicemanagement.configuration;

import deti.tqs.g305.servicemanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

// https://stackoverflow.com/questions/56889156/spring-security-antmatcher-rule-based-on-custom-headers
@Component
public class BusinessMatcher implements RequestMatcher {


    private static final Logger log = LoggerFactory.getLogger(BusinessMatcher.class);

    @Autowired
    private UserService userService;

    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("APIToken");
        log.info("Validating that token {} is a valid Business API token (request {}, {})...", token, httpServletRequest.getRequestURI(), httpServletRequest.getMethod());
        if (token != null) {
            return userService.getBusinessByApiKey(token).isPresent() && httpServletRequest.getUserPrincipal()!=null;
        }
        return false;
    }
}
