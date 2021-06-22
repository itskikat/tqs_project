package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.City;
import deti.tqs.g305.servicemanagement.model.District;
import deti.tqs.g305.servicemanagement.model.Provider;

import java.util.List;
import java.util.Optional;


public interface LocationsService {

    List<District> getDistricts();
    Optional<List<City>> getCities(Optional<Long>  id);
    Optional<District> getDistricById(Long id);
    Optional<City> getCityById(Long id);
}