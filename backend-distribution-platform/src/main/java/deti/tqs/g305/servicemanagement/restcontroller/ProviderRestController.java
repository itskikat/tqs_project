package deti.tqs.g305.servicemanagement.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.service.ServiceService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * ProviderRestController
 */

@RestController
@RequestMapping("/api/provider")
public class ProviderRestController {

    @Autowired
    private ServiceService serviceService;


    @GetMapping("/contracts")
    public ResponseEntity<?> getServiceContracts(){
        //TODO Provider login
        List<ServiceContract> scList = serviceService.getServiceContracts(0).get();
        return new ResponseEntity<List<ServiceContract>>(scList, HttpStatus.OK);
    }

    @GetMapping("/contracts/{id}")
    public ResponseEntity<?> getServiceContract(@PathVariable(value = "id") Long serviceContractId){
        Optional<ServiceContract> sc = serviceService.getServiceContract(0, serviceContractId);

        if( sc.isPresent()){
            return new ResponseEntity<ServiceContract>(sc.get(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Invalid id! Could not find contract.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/contracts/{id}")
    public ResponseEntity<?> updateServiceContract(@PathVariable(value = "id") Long serviceContractId, @RequestBody(required = false) ServiceContract sc){
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