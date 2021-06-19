package deti.tqs.g305.handymanservicesapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserResponse implements Serializable {

    @JsonProperty("type")
    private UserAuthority type;
    private String name;
    private String email;

    public UserResponse() {
    }

    public UserResponse(UserAuthority type, String name, String email) {
        this.type = type;
        this.name = name;
        this.email = email;
    }
}
