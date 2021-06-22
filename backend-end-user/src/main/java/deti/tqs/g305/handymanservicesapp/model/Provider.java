package deti.tqs.g305.handymanservicesapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Data
public class Provider extends User {

    private String category;

    private Map<Integer, String> working_hours;

    private List<City> location_city;

    private List<District> location_district;

    private Set<ProviderService> providerServices;

    private String nif;

    private LocalDate birthdate;

    public Provider() {

    }

    public Provider(String email, String full_name,String password,
                    Map<Integer, String> working_hours, List<City> location_city, List<District> location_district, String nif, LocalDate birthdate) {
        super(email, full_name, password);
        this.working_hours = working_hours;
        this.location_city = location_city;
        this.location_district = location_district;
        this.nif = nif;
        this.birthdate = birthdate;
    }

}
