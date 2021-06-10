package deti.tqs.g305.servicemanagement.restcontroller;


import deti.tqs.g305.servicemanagement.model.BusinessService;
import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.List;
import java.util.Map;
import java.security.Principal;
import java.util.HashMap;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

/**
 * BusinessRestController
 */

@RestController
@RequestMapping("/api/businesses")
public class BusinessRestController {

    @Autowired
    private ServiceService serviceService;


    @PostMapping("/services")
    public ResponseEntity<?> createBusinessService( @Valid @RequestBody(required = false) BusinessService bs){
        if(bs != null){
            Optional<BusinessService> optbs = serviceService.saveBusinessService(bs);
            if(optbs.isPresent()){
                return new ResponseEntity<BusinessService>(bs, HttpStatus.OK);
            }
            return new ResponseEntity<String>("Bad Business Service!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Bad Business Service!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/contracts")
    public ResponseEntity<?> getServiceContracts(@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size, HttpServletRequest request){
        
        Principal principal = request.getUserPrincipal();

        Pageable paging = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC, "date"));
        Page<ServiceContract> scPage = serviceService.getServiceContracts(principal.getName(), paging, "Business");
        List <ServiceContract> scList;

        scList = scPage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("data", scList);
        response.put("currentPage", scPage.getNumber());
        response.put("totalItems", scPage.getTotalElements());
        response.put("totalPages", scPage.getTotalPages());
        
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PutMapping("/services/{id}")
    public ResponseEntity<?> updateBusinessService(@PathVariable(value = "id") Long businessServiceId, @Valid @RequestBody(required = false) BusinessService bs){
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
            boolean exists = serviceService.deleteBusinessService(businessServiceId);
            if(exists){
                return new ResponseEntity<String>("Business Service deleted", HttpStatus.FOUND);
            }
            return new ResponseEntity<String>("Could not find requested business service", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Could not find requested business service", HttpStatus.BAD_REQUEST);
    }
}
