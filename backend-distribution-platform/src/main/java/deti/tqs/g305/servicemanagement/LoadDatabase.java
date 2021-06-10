package deti.tqs.g305.servicemanagement;

import java.util.Optional;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import deti.tqs.g305.servicemanagement.model.*;
import deti.tqs.g305.servicemanagement.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.*;
import java.time.temporal.ChronoUnit;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Autowired
  private PasswordEncoder bcryptEncoder;

  @Bean
  CommandLineRunner initDatabase(ClientRepository clientRepository, ServiceContractRepository serviceContractRepository, ProviderRepository providerRepository,
  ProviderServiceRepository providerServiceRepository, BusinessRepository businessRepository, BusinessServiceRepository businessServiceRepository, ServiceTypeRepository serviceTypeRepository) {

    //docker exec -it tqs_project_db_1 bash
    // psql -h 127.0.0.1 -d demo -U demo


    return args -> {
      Client c = new Client("xpto@ua.pt", bcryptEncoder.encode("abc"), "xpto xpta", "lala", LocalDate.now());
      clientRepository.save(c);

      Business b = new Business("plumber@plumber.com", "Plumber.com, LDA", bcryptEncoder.encode("abc"), "lala", "lele", "lili","lulu");
      businessRepository.save(b);

      ServiceType st = new ServiceType("canalização", true);
      serviceTypeRepository.save(st);

      BusinessService bs = new BusinessService(10, st, b);
      businessServiceRepository.save(bs);

      Provider p = new Provider("bob.hard@outlook.com", "Bob Dickard", bcryptEncoder.encode("abc"), null,null,null,"alal", LocalDate.now());
      providerRepository.save(p);

      ProviderService ps = new ProviderService("bla bla", p, st);
      providerServiceRepository.save(ps);

      ServiceContract sc = new ServiceContract(bs, ps, ServiceStatus.Waiting, c, 0);
      serviceContractRepository.save(sc);
      
    };
    
  }
  

}

