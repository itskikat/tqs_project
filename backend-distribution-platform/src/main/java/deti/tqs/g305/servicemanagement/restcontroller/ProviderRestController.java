package deti.tqs.g305.servicemanagement.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.model.ServiceStatus;
import deti.tqs.g305.servicemanagement.service.ServiceService;

import java.util.List;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * ProviderRestController
 */

@RestController
@RequestMapping("/api/provider")
public class ProviderRestController {

    @Autowired
    private ServiceService serviceService;


    @GetMapping("/contracts")
    public ResponseEntity<?> getServiceContracts(@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size, @RequestParam(required=false) String status, @RequestParam(required=false) Long type, 
    @RequestParam(defaultValue = "date") String sort,@RequestParam(defaultValue = "ASC") String order,HttpServletRequest request){
        Principal principal = request.getUserPrincipal();

        Pageable paging;
        Page<ServiceContract> scPage;

        if((order.equals("ASC") || order.equals("DESC")) && (sort.equals("date") || sort.equals("review"))){
            if(order.equals("ASC")){
                paging = PageRequest.of(page, size,Sort.by(Sort.Direction.ASC, sort));
            }
            else{
                paging = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC, sort));
            }
        }
        else{
            return new ResponseEntity<String>("Invalid order and sort parameters!", HttpStatus.BAD_REQUEST);
        }
        if(status != null && type!=null){
            try {
                ServiceStatus sv = ServiceStatus.valueOf(status.toUpperCase());
                scPage= serviceService.getServiceContracts(principal.getName(), paging, "Provider", Optional.of(sv), Optional.of(type));
            } catch (Exception e) {
                return new ResponseEntity<String>("Invalid Status", HttpStatus.BAD_REQUEST);
            }   
        }
        else if(status!=null){
            try {
                ServiceStatus sv = ServiceStatus.valueOf(status.toUpperCase());
                scPage= serviceService.getServiceContracts(principal.getName(), paging, "Provider", Optional.of(sv), Optional.empty());
            } catch (Exception e) {
                return new ResponseEntity<String>("Invalid Status", HttpStatus.BAD_REQUEST);
            } 
        }
        else if(type!=null){
            scPage= serviceService.getServiceContracts(principal.getName(), paging, "Provider", Optional.empty(), Optional.of(type));
        }
        else{
            scPage= serviceService.getServiceContracts(principal.getName(), paging, "Provider", Optional.empty(), Optional.empty());
        }
        
        List <ServiceContract> scList;

        scList = scPage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("data", scList);
        response.put("currentPage", scPage.getNumber());
        response.put("totalItems", scPage.getTotalElements());
        response.put("totalPages", scPage.getTotalPages());
        
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("/contracts/{id}")
    public ResponseEntity<?> getServiceContract(@PathVariable(value = "id") long serviceContractId, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Optional<ServiceContract> sc = serviceService.getServiceContract(principal.getName(), serviceContractId);

        if( sc.isPresent()){
            return new ResponseEntity<ServiceContract>(sc.get(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Invalid id! Could not find contract.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/contracts/{id}")
    public ResponseEntity<?> updateServiceContract(@PathVariable(value = "id") long serviceContractId, @RequestBody(required = false) ServiceContract sc){
        if(sc != null){
            Optional<ServiceContract>  optSc= serviceService.updateServiceContract(serviceContractId, sc);
            if(optSc.isPresent()){
                return new ResponseEntity<ServiceContract>(sc, HttpStatus.OK);
            }
            return new ResponseEntity<String>("Could not find service contract", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Bad Service Contract", HttpStatus.BAD_REQUEST);
    }
}