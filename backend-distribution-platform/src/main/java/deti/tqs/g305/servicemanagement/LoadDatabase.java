package deti.tqs.g305.servicemanagement;

import java.util.Optional;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import deti.tqs.g305.servicemanagement.model.*;
import deti.tqs.g305.servicemanagement.repository.*;

import java.time.*;
import java.time.temporal.ChronoUnit;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(ClientRepository clientRepository, ServiceContractRepository sContractRepository, ProviderRepository providerRepository,
  ProviderServiceRepository providerServiceRepository, BusinessRepository businessRepository, BusinessServiceRepository businessServiceRepository) {

    return args -> {
      Client c = new Client("String google_id", "xpto", "xpto@ua.pt", "xpto xpta", "lala", LocalDate.now());
      clientRepository.save(c);
    };
    
  }
  

}

