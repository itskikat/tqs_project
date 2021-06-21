package deti.tqs.g305.handymanservicesapp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

@Configuration
public class RequestsHelper {

    @Value("${enduser.token}")
    String token;

    public HttpHeaders getHeadersWithAuthorization(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APIToken", token);
        headers.set("Authorization", token);
        return headers;
    }

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APIToken", token);
        return headers;
    }

    public HttpEntity getEntity() {
        return new HttpEntity(this.getHeaders());
    }

    public HttpEntity getEntityWithAuthorization(String token) {
        return new HttpEntity(this.getHeadersWithAuthorization(token));
    }

}
