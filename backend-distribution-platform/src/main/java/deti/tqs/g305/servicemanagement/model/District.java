package deti.tqs.g305.servicemanagement.model;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;


@Entity
@Data
@Table(name="DISTRICT")
public class District{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

}