package deti.tqs.g305.handymanservicesapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserResponse implements Serializable {

    @JsonProperty("type")
    private final UserAuthority type;
    private final String name;
    private final String email;

    public UserResponse(UserAuthority type, String name, String email) {
        this.type = type;
        this.name = name;
        this.email = email;
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
