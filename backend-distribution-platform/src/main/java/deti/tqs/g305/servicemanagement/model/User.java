package deti.tqs.g305.servicemanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import javax.persistence.*;

@Entity
@Data
@Table(name="USER_GENERIC")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type", discriminatorType = DiscriminatorType.STRING)
public class User {

    @Id
    @Column(name="email", unique = true, nullable = false)
    private String email;

    @Column(name = "full_name")
    private String full_name;

    @Column(name = "password", nullable = false)
    private String password;

    public User() {

    }

    public User(String email, String full_name, String password) {
        this.email = email;
        this.full_name = full_name;
        this.password = password;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
