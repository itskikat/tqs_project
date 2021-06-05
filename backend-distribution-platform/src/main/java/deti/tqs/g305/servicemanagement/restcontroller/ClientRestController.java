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
 * ClientRestController
 */

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {

    @Autowired
    private ServiceService serviceService;

    @PostMapping("/contracts")
    public ResponseEntity<?> createServiceContract( @RequestBody(required = false) ServiceContract sc){
        if(sc != null){
            sc = serviceService.saveServiceContract(sc);
            return new ResponseEntity<ServiceContract>(sc, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Bad Service Contract", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/contracts")
    public ResponseEntity<?> getServiceContracts(){
        //TODO Client login
        List<ServiceContract> scList = serviceService.getServiceContracts(0).get();
        return new ResponseEntity<List<ServiceContract>>(scList, HttpStatus.OK);
    }

    @GetMapping("/contracts/{id}")
    public ResponseEntity<?> getServiceContract(@PathVariable(value = "id") Long businessServiceId){
        //TODO
        return new ResponseEntity<String>("", HttpStatus.OK);
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
