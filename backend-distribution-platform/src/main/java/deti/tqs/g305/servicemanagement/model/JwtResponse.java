package deti.tqs.g305.servicemanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    @JsonProperty("token")
    private final String jwttoken;
    @JsonProperty("type")
    private final UserAuthority type;
    private final String name;
    private final String email;


    public JwtResponse(String jwttoken, UserAuthority type, String name, String email) {
        this.jwttoken = jwttoken;
        this.type = type;
        this.name = name;
        this.email = email;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public UserAuthority getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
