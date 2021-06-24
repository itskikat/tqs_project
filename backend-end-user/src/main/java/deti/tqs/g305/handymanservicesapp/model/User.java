package deti.tqs.g305.handymanservicesapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
public class User {

    private String email;

    private String full_name;

    private String password;

    public User() {

    }

    public User(String email, String full_name, String password) {
        this.email = email;
        this.full_name = full_name;
        this.password = password;
    }

}
