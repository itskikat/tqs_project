package deti.tqs.g305.servicemanagement.model;

import lombok.Data;


import javax.persistence.*;

@Entity
@Data
@Table(name="USER_GENERIC")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type", discriminatorType = DiscriminatorType.STRING)
public class User {

    @Id
    @Column(name = "googleID", unique = true)
    private String google_id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "full_name")
    private String full_name;

    public User() {

    }

    public User(String google_id, String username, String email, String full_name) {
        this.google_id = google_id;
        this.username = username;
        this.email = email;
        this.full_name = full_name;
    }
}
