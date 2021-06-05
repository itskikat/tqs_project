package deti.tqs.g305.servicemanagement.restcontroller;

import deti.tqs.g305.servicemanagement.model.BusinessService;
import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

/**
 * BusinessRestController
 */

@RestController
@RequestMapping("/api/businesses")
public class BusinessRestController {

    @Autowired
    private ServiceService serviceService;


    @PostMapping("/services")
    public ResponseEntity<?> createBusinessService(@RequestBody(required = false) BusinessService bs){
        if(bs != null){
            bs = serviceService.saveBusinessService(bs);
            return new ResponseEntity<BusinessService>(bs, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Bad Business Service!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/contracts")
    public ResponseEntity<?> getServiceContracts(){
        //TODO Businness login
        List<ServiceContract> scList = serviceService.getServiceContracts(0).get();
        return new ResponseEntity<List<ServiceContract>>(scList, HttpStatus.OK);
    }

    @PutMapping("/services/{id}")
    public ResponseEntity<?> updateBusinessService(@PathVariable(value = "id") Long businessServiceId, @RequestBody(required = false) BusinessService bs){
        if(bs != null){
            Optional<BusinessService> optBs = serviceService.updateBusinessService(businessServiceId, bs);
            if(optBs.isPresent()){
                return new ResponseEntity<BusinessService>(bs, HttpStatus.OK);
            }
            return new ResponseEntity<String>("Could not find requested business service", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Bad Business Service!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/services/delete/{id}")
    public ResponseEntity<?> deleteBusinessService(@PathVariable(value = "id") Long businessServiceId){
        if(businessServiceId != null) {
            serviceService.deleteBusinessService(businessServiceId);
            return new ResponseEntity<String>("Business Service deleted", HttpStatus.FOUND);
        }
        return new ResponseEntity<String>("Could not find requested business service", HttpStatus.BAD_REQUEST);
    }
}
