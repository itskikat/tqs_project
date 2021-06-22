package deti.tqs.g305.servicemanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import deti.tqs.g305.servicemanagement.model.*;
import deti.tqs.g305.servicemanagement.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.io.BufferedReader;  
import java.io.FileReader;  

import java.util.Map;
import java.util.HashMap;

import java.time.*;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Autowired
  private PasswordEncoder bcryptEncoder;

  @Bean
  CommandLineRunner initDatabase(ClientRepository clientRepository, ServiceContractRepository serviceContractRepository, ProviderRepository providerRepository,
  ProviderServiceRepository providerServiceRepository, BusinessRepository businessRepository, BusinessServiceRepository businessServiceRepository, ServiceTypeRepository serviceTypeRepository,
  DistrictRepository districtRepository, CityRepository cityRepository) {

    // docker exec -it tqs_project_db_1 bash
    // psql -h 127.0.0.1 -d demo -U demo

    return args -> {
      Client c = new Client("xpto@ua.pt", bcryptEncoder.encode("abc"), "xpto xpta", "lala", LocalDate.now());
      clientRepository.save(c);

      Business b = new Business("plumber@plumber.com", "Plumber.com, LDA", bcryptEncoder.encode("abc"), "lala", "lele", "lili","lulu");
      businessRepository.save(b);

      ServiceType st = new ServiceType("canalização", false);
      serviceTypeRepository.save(st);

      BusinessService bs = new BusinessService(10, st, b);
      businessServiceRepository.save(bs);

      Provider p = new Provider("bob.hard@outlook.com", "Bob Dickard", bcryptEncoder.encode("abc"), null,null,null,"alal", LocalDate.now());
      providerRepository.save(p);

      ProviderService ps = new ProviderService("bla bla", p, st);
      providerServiceRepository.save(ps);

      ServiceContract sc = new ServiceContract(bs, ps, ServiceStatus.WAITING, c, 0);
      serviceContractRepository.save(sc);

      ServiceType st1 = new ServiceType("eletricidade", true);
      serviceTypeRepository.save(st1);

      ServiceType st2 = new ServiceType("jardinagem", true);
      serviceTypeRepository.save(st2);

      ServiceType st3 = new ServiceType("babysitting", true);
      serviceTypeRepository.save(st3);

      ServiceType st4 = new ServiceType("limpeza", true);
      serviceTypeRepository.save(st4);

      ServiceType st5 = new ServiceType("informatica", true);
      serviceTypeRepository.save(st5);

      ServiceType st6 = new ServiceType("eheue", true);
      serviceTypeRepository.save(st6);

      ProviderService ps1 = new ProviderService("bla bla", p, st1);
      providerServiceRepository.save(ps1);


      BusinessService bs1 = new BusinessService(15, st1, b);

      businessServiceRepository.save(bs1);

      ServiceContract sc1 = new ServiceContract(bs1, ps1, ServiceStatus.ACCEPTED, c, 5);
      serviceContractRepository.save(sc1);

      ServiceContract sc2 = new ServiceContract(bs1, ps1, ServiceStatus.FINNISHED, c, 2);
      serviceContractRepository.save(sc2);

      ServiceContract sc3 = new ServiceContract(bs1, ps1, ServiceStatus.REJECTED, c, 2);
      serviceContractRepository.save(sc3);

      BusinessService bs2 = new BusinessService(25, st1, b);
      businessServiceRepository.save(bs2);

      BusinessService bs3 = new BusinessService(45, st1, b);
      businessServiceRepository.save(bs3);

      String line = "";  

      //Load Locations

      Map<String, Long> dist_cods = new HashMap<String, Long>();
      try {
        BufferedReader br = new BufferedReader(new FileReader("distritos.csv"));  
        br.readLine();
        while ((line = br.readLine()) != null) {
          String[] dist_info = line.split(",");
          District d = new District(dist_info[1]);
          d= districtRepository.save(d);
          dist_cods.put(dist_info[0], d.getId());
        }
      } catch (Exception e) {
        e.printStackTrace();  
      }

      try {
        BufferedReader br = new BufferedReader(new FileReader("concelhos.csv"));  
        br.readLine();
        while ((line = br.readLine()) != null) {
          String[] city_info = line.split(",");        
          cityRepository.save( new City(city_info[2],districtRepository.findById(dist_cods.get(city_info[0])).get()));
        }
      } catch (Exception e) {
        e.printStackTrace();  
      }
     
      
    };
    
  }
  

}

