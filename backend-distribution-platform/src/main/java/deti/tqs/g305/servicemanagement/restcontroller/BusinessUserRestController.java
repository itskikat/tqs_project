package deti.tqs.g305.servicemanagement.restcontroller;

import deti.tqs.g305.servicemanagement.model.Business;
import deti.tqs.g305.servicemanagement.service.BusinessUserService;
import org.springframework.http.HttpStatus;

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

        Optional<Business> b = businessUserService.updateBusiness(email, business);

        if(b.isPresent()){
            return ResponseEntity.ok().body(b.get());
        }
        return new ResponseEntity<String>("Bad business", HttpStatus.BAD_REQUEST);
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
