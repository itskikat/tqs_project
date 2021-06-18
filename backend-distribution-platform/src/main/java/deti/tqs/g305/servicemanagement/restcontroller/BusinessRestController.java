package deti.tqs.g305.servicemanagement.restcontroller;


import deti.tqs.g305.servicemanagement.model.BusinessService;
import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.model.ServiceType;
import deti.tqs.g305.servicemanagement.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.*;
import java.security.Principal;

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

    @GetMapping("/services")
    public ResponseEntity<?> getBusinessServices(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size, @RequestParam(required=false) String name,
                                                 @RequestParam(defaultValue = "service_name") String sort, @RequestParam(defaultValue = "ASC") String order, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();

        Pageable paging;
        Page<BusinessService> bsPage;

        if( (order.equals("ASC") || order.equals("DESC")) && (sort.equals("service_name") || sort.equals("price")) ) {
            if (order.equals("ASC")) {
                paging = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sort));
            }
            else {
                paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort));
            }
        }
        else {
            return new ResponseEntity<String>("Invalid order and sort parameters!", HttpStatus.BAD_REQUEST);
        }
        if (name != null) {
            bsPage = serviceService.getBusinessBusinessServices(principal.getName(), paging, Optional.of(name));
        }
        else {
            bsPage= serviceService.getBusinessBusinessServices(principal.getName(), paging, Optional.empty());
        }

        List <BusinessService> bsList;

        bsList = bsPage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("data", bsList);
        response.put("currentPage", bsPage.getNumber());
        response.put("totalItems", bsPage.getTotalElements());
        response.put("totalPages", bsPage.getTotalPages());

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }


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
        Page<ServiceContract> scPage = serviceService.getServiceContracts(principal.getName(), paging, "Business", Optional.empty(), Optional.empty());
        List <ServiceContract> scList;

        scList = scPage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("data", scList);
        response.put("currentPage", scPage.getNumber());
        response.put("totalItems", scPage.getTotalElements());
        response.put("totalPages", scPage.getTotalPages());
        
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("/services/{id}")
    public ResponseEntity<?> getBusinessService(@PathVariable(value = "id") Long businessServiceId, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();

        Optional<BusinessService> optBs = serviceService.getBusinessService(principal.getName(), businessServiceId);
        if(optBs.isPresent()){
            return new ResponseEntity<BusinessService>(optBs.get(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Could not find requested business service", HttpStatus.BAD_REQUEST);

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
                return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Business Service deleted");
            }
            return new ResponseEntity<String>("Could not find requested business service", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Could not find requested business service", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getStatistics(@RequestParam(required = false) LocalDate start_date, @RequestParam(required = false) LocalDate end_date, HttpServletRequest request) {
        
        Principal principal = request.getUserPrincipal();
        Map<String, Object> response = new HashMap<>();
        Double business_profit = 0.0;
        Integer business_contracts = 0;
        ServiceType business_most_requested = null;


        if (start_date != null && end_date != null) {
            response.put("start_date", start_date);
            response.put("end_date", end_date);
            business_profit = serviceService.getBusinessBusinessServiceProfit(principal.getName(), Optional.of(start_date), Optional.of(end_date));
            business_contracts = serviceService.getTotalBusinessServiceContracts(principal.getName(), Optional.of(start_date), Optional.of(end_date));
            business_most_requested = serviceService.getBusinessMostRequestedServiceType(principal.getName(), Optional.of(start_date), Optional.of(end_date));
        }
        else {
            business_profit = serviceService.getBusinessBusinessServiceProfit(principal.getName(), Optional.empty(), Optional.empty());
            business_contracts = serviceService.getTotalBusinessServiceContracts(principal.getName(), Optional.empty(), Optional.empty());
            business_most_requested = serviceService.getBusinessMostRequestedServiceType(principal.getName(), Optional.empty(), Optional.empty());
        }


        response.put("profit", business_profit);
        response.put("total-contracts", business_contracts);
        response.put("most-requested-ServiceType", business_most_requested);
        
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
