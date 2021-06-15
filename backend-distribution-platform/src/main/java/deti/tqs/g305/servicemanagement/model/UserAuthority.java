package deti.tqs.g305.servicemanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {

    @JsonProperty("authority")
    private String authority;

    public UserAuthority(String authority) {
        this.authority = authority;
    }

    public UserAuthority() {
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }



}
