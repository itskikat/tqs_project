package deti.tqs.g305.servicemanagement.restcontroller;

import deti.tqs.g305.servicemanagement.model.*;
import deti.tqs.g305.servicemanagement.repository.ProviderRepository;
import deti.tqs.g305.servicemanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import deti.tqs.g305.servicemanagement.service.ServiceService;

import java.util.*;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * ClientRestController
 */

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {

    private static final Logger log = LoggerFactory.getLogger(ClientRestController.class);

    @Autowired
    private ServiceService serviceService;


    @PostMapping("/contracts")
    public ResponseEntity<?> createServiceContract( @Valid @RequestBody(required = false) ServiceContract sc){
        if(sc != null){

            Optional<ServiceContract> optSc = serviceService.saveServiceContract(sc);
            if(optSc.isPresent()){
                return new ResponseEntity<ServiceContract>(sc, HttpStatus.OK);
            }
            return new ResponseEntity<String>("Bad Service Contract", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Bad Service Contract", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/contracts")
    public ResponseEntity<?> getServiceContracts(@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,@RequestParam(required=false) String status, @RequestParam(required=false) Long type, 
    @RequestParam(defaultValue = "date") String sort,@RequestParam(defaultValue = "ASC") String order,
     HttpServletRequest request){
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
                scPage= serviceService.getServiceContracts(principal.getName(), paging, "Client", Optional.of(sv), Optional.of(type));
            } catch (Exception e) {
                return new ResponseEntity<String>("Invalid Status", HttpStatus.BAD_REQUEST);
            }
        }
        else if(status!=null){
            try {
                ServiceStatus sv = ServiceStatus.valueOf(status.toUpperCase());
                scPage= serviceService.getServiceContracts(principal.getName(), paging, "Client", Optional.of(sv), Optional.empty());
            } catch (Exception e) {
                return new ResponseEntity<String>("Invalid Status", HttpStatus.BAD_REQUEST);
            }
        }
        else if(type!=null){
            scPage= serviceService.getServiceContracts(principal.getName(), paging, "Client", Optional.empty(), Optional.of(type));
        }
        else{
            scPage= serviceService.getServiceContracts(principal.getName(), paging, "Client", Optional.empty(), Optional.empty());
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
    public ResponseEntity<?> getServiceContract(@PathVariable(value = "id") Long serviceContractId, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();

        Optional<ServiceContract> sc = serviceService.getServiceContract(principal.getName(), serviceContractId);

        if( sc.isPresent()){
            return new ResponseEntity<ServiceContract>(sc.get(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Invalid id! Could not find contract.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/contracts/{id}")
    public ResponseEntity<?> updateServiceContract(@PathVariable(value = "id") Long serviceContractId, @Valid @RequestBody(required = false) ServiceContract sc){
        if(sc != null){
            Optional<ServiceContract>  optSc= serviceService.updateServiceContract(serviceContractId, sc);
            if(optSc.isPresent()){
                return new ResponseEntity<ServiceContract>(sc, HttpStatus.OK);
            }
            return new ResponseEntity<String>("Could not find service contract", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Bad Service Contract", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/matches/{id}")
    public ResponseEntity<?> getMatchingServiceProviders(@PathVariable(value = "id") Long serviceTypeId, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();
        List<ProviderService>  ps = serviceService.getMatches(principal.getName(),serviceTypeId);

        return new ResponseEntity<List<ProviderService>>(ps, HttpStatus.OK);
    }

    @GetMapping("/services/{id}")
    public ResponseEntity<?> getProviderService(@PathVariable(value = "id") Long ProviderServiceId, HttpServletRequest request){
        return new ResponseEntity<Optional<ProviderService>>(serviceService.getProviderService(request.getUserPrincipal().getName(), ProviderServiceId), HttpStatus.OK);
    }


}
