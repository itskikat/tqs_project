package deti.tqs.g305.servicemanagement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.repository.*;
import deti.tqs.g305.servicemanagement.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class LocationServiceUnitTest {


    @Mock( lenient = true)
    private DistrictRepository districtRepository;

    @Mock( lenient = true)
    private CityRepository cityRepository;

    @InjectMocks
    private LocationsServiceImpl serviceLocation;

    List<District> d;
    List<City> c;

    @BeforeEach
    public void setUp() {
        d = new ArrayList<District>(Arrays.asList(new District("Braga")));
        c = new ArrayList<City>(Arrays.asList(new City("Braga", new District("Braga")), new City("Porto", new District("Porto"))));
    }

    @Test
    public  void whenGetDistricts_DistrictsShoulBeReturned(){
        Mockito.when(districtRepository.findAll()).thenReturn(d);

        List<District> optD = serviceLocation.getDistricts();

        assertThat(optD).isEqualTo(d);
        verify(districtRepository, times(1)).findAll();
    }

    @Test
    public  void whenGetCities_CitiesShoulBeReturned(){
        Mockito.when(cityRepository.findAll()).thenReturn(c);

        Optional<List<City>> optD = serviceLocation.getCities(Optional.empty());

        assertThat(optD).isEqualTo(Optional.of(c));
        verify(cityRepository, times(1)).findAll();
    }

    @Test
    public  void whenGetCitiesbyDistrict_CitiesShoulBeReturned(){
        Mockito.when(cityRepository.findByDistrict_Id(1L)).thenReturn(Optional.of(c));

        Optional<List<City>> optD = serviceLocation.getCities(Optional.of(1L));

        assertThat(optD).isEqualTo(Optional.of(c));
        verify(cityRepository, times(1)).findByDistrict_Id(1L);
    }

    @Test
    public  void whenGetCitiesbyId_CityShoulBeReturned(){
        City c = new City("Braga", new District("Braga"));
        Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.of(c));

        Optional<City> optC= serviceLocation.getCityById(1L);

        assertThat(optC).isEqualTo(Optional.of(c));
        verify(cityRepository, times(1)).findById(1L);
    }

    @Test
    public  void whenGetDistrictbyId_DistrictShoulBeReturned(){
        District d = new District("Braga");
        Mockito.when(districtRepository.findById(1L)).thenReturn(Optional.of(d));

        Optional<District> optD = serviceLocation.getDistricById(1L);

        assertThat(optD).isEqualTo(Optional.of(d));
        verify(districtRepository, times(1)).findById(1L);
    }
 
    


    

    

}