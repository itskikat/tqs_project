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
        return providerUserService.getProviders();
    }

    //Obtendo um provider específico pelo email (GET /provider/{email})
    @GetMapping(path = {"/provider/{email}"})
    public ResponseEntity<?> findByEmail(@PathVariable String email){
        return providerUserService.findByEmail(email)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    //Criando um novo provider (POST /provider)
    @PostMapping(path = {"/provider"})
    public Optional<Provider> createProvider(@RequestBody Provider provider){
        return providerUserService.createProvider(provider);
    }

    //Atualizando um provider (PUT /provider)
    @PutMapping(value="/provider/{email}")
    public ResponseEntity<?> updateProvider(@PathVariable("email") String email,
                                 @RequestBody Provider provider) {
        return ResponseEntity.ok().body(providerUserService.updateProvider(email, provider));
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
