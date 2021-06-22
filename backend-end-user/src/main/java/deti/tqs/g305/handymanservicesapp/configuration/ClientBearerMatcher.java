package deti.tqs.g305.handymanservicesapp.configuration;

import deti.tqs.g305.handymanservicesapp.exceptions.UnauthorizedException;
import deti.tqs.g305.handymanservicesapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

// https://stackoverflow.com/questions/56889156/spring-security-antmatcher-rule-based-on-custom-headers
@Configuration
public class ClientBearerMatcher implements RequestMatcher {

    private static final Logger log = LoggerFactory.getLogger(ClientBearerMatcher.class);

    @Autowired
    private UserService userService;

    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {
        log.info("httpServletRequest: {}", httpServletRequest);
        String token = httpServletRequest.getHeader("Authorization");
        log.info("Validating that client authorization token {} is a valid one...", token);
        if (token != null) {
            try {
                return userService.getUserLogged(httpServletRequest)!=null;
            } catch (UnauthorizedException e) {
                return false;
            }
        }
        return false;
    }
}
