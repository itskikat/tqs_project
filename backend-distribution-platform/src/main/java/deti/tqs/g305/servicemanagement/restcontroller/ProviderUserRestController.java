package deti.tqs.g305.servicemanagement.restcontroller;

import deti.tqs.g305.servicemanagement.model.Provider;
import deti.tqs.g305.servicemanagement.service.ProviderUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/api"})
public class ProviderUserRestController {

    @Autowired
    private ProviderUserService providerUserService;

    //Obtendo a lista de providers (GET /provider)
    @GetMapping(path = {"/provider"})
    public List<?> getProviders(){
        System.out.println("Careful...");
        return providerUserService.getProviders();
    }

    //Obtendo um provider específico pelo email (GET /provider/{email})
    @GetMapping(path = {"/provider/{email}"})
    public ResponseEntity<?> findByEmail(@PathVariable String email){
        System.out.println("You made it!");
        return providerUserService.findByEmail(email)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    //Criando um novo provider (POST /provider)
    @PostMapping
    public Optional<Provider> createProvider(@RequestBody Provider provider){
        return providerUserService.createProvider(provider);
    }

    //Atualizando um provider (PUT /provider)
    @PutMapping(value="/provider/{email}")
    public ResponseEntity<?> updateProvider(@PathVariable("email") String email,
                                 @RequestBody Provider provider) {
        return providerUserService.findByEmail(email)
                .map(record -> {
                    record.setFull_name(provider.getFull_name());
                    record.setEmail(provider.getEmail());
                    record.setPassword(provider.getPassword());
                    record.setCategory(provider.getCategory());
                    record.setWorking_hours(provider.getWorking_hours());
                    record.setLocation_city(provider.getLocation_city());
                    record.setLocation_district(provider.getLocation_district());
                    record.setNif(provider.getNif());
                    record.setBirthdate(provider.getBirthdate());
                    Optional<Provider> updated = providerUserService.createProvider(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    //Removendo um provider pelo email (DELETE /provider/{email})
    @DeleteMapping(path ={"/provider/{email}"})
    public ResponseEntity<?> deleteProvider(@PathVariable String email) {
        return providerUserService.findByEmail(email)
                .map(record -> {
                    providerUserService.deleteProvider(email);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
