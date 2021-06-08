package deti.tqs.g305.servicemanagement.model;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final GrantedAuthority type;
    private final String name;
    private final String email;

    public JwtResponse(String jwttoken, GrantedAuthority type, String name, String email) {
        this.jwttoken = jwttoken;
        this.type = type;
        this.name = name;
        this.email = email;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public GrantedAuthority getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
