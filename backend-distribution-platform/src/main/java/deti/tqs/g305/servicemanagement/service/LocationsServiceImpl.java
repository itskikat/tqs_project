package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.Provider;
import deti.tqs.g305.servicemanagement.repository.DistrictRepository;
import deti.tqs.g305.servicemanagement.repository.CityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import deti.tqs.g305.servicemanagement.model.City;
import deti.tqs.g305.servicemanagement.model.District;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LocationsServiceImpl  implements LocationsService{

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private CityRepository cityRepository;
    
    @Override
    public List<District> getDistricts(){
        return districtRepository.findAll();
    }

    @Override
    public Optional<List<City>> getCities(Optional<Long>  id){
        if(id.isPresent()){
            return cityRepository.findByDistrict_Id(id.get());
        }
        else{
            return Optional.of(cityRepository.findAll());
        }
    }

    @Override
    public Optional<District> getDistricById(Long id){
        return districtRepository.findById(id);
    }

    @Override
    public Optional<City> getCityById(Long id){
        return cityRepository.findById(id);
    }
}
