package deti.tqs.g305.servicemanagement.restcontroller;

import deti.tqs.g305.servicemanagement.model.Business;
import deti.tqs.g305.servicemanagement.service.BusinessUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/api"})
public class BusinessUserRestController {

    @Autowired
    private BusinessUserService businessUserService;

    //Obtendo a lista de business (GET /business)
    @GetMapping(path = {"/business"})
    public List<?> getBusiness(){
        System.out.println("Careful...");
        return businessUserService.getBusiness();
    }

    //Obtendo um business específico pelo email (GET /business/{email})
    @GetMapping(path = {"/business/{email}"})
    public ResponseEntity<?> findByEmail(@PathVariable String email){
        System.out.println("You made it!");
        return businessUserService.findByEmail(email)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    //Criando um novo business (POST /provider)
    @PostMapping(path = {"/business"})
    public Optional<Business> createBusiness(@RequestBody Business business){
        return businessUserService.createBusiness(business);
    }

    //Atualizando um business (PUT /provider)
    @PutMapping(value="/business/{email}")
    public ResponseEntity<?> updateBusiness(@PathVariable("email") String email,
                                            @RequestBody Business business) {
        return businessUserService.findByEmail(email)
                .map(record -> {
                    record.setFull_name(business.getFull_name());
                    record.setEmail(business.getEmail());
                    record.setPassword(business.getPassword());
                    record.setApikey(business.getApikey());
                    record.setName(business.getName());
                    record.setAddress(business.getAddress());
                    record.setNif(business.getNif());
                    Optional<Business> updated = businessUserService.createBusiness(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    //Removendo um business pelo email (DELETE /business/{email})
    @DeleteMapping(path ={"/business/{email}"})
    public ResponseEntity<?> deleteBusiness(@PathVariable String email) {
        return businessUserService.findByEmail(email)
                .map(record -> {
                    businessUserService.deleteBusiness(email);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
