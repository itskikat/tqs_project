package deti.tqs.g305.servicemanagement.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import deti.tqs.g305.servicemanagement.model.ServiceType;
import deti.tqs.g305.servicemanagement.service.ServiceServiceType;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * GenericRestController
 */

@RestController
@RequestMapping("/api")
public class GenericRestController {

    @Autowired
    private ServiceServiceType serviceServiceType;


    @PostMapping("/servicetypes")
    public ResponseEntity<?> saveServiceType(@RequestBody(required = false) ServiceType st){
        if(st!=null){
            Optional<ServiceType> optSt =  serviceServiceType.addServiceType(st);
            if(optSt.isPresent()){
                return new ResponseEntity<ServiceType>(optSt.get(), HttpStatus.OK);
            }
            return new ResponseEntity<String>("Invalid Service Type", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Service Type not in request body", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/servicetypes")
    public ResponseEntity<?> getServiceTypes(@RequestParam(required=false) String name){

        List<ServiceType> sts;

        if(name!=null){
            sts =  serviceServiceType.getServiceTypes(Optional.of(name));
        }
        else{
            sts =  serviceServiceType.getServiceTypes(Optional.empty());
        }
        return new ResponseEntity<List<ServiceType>>(sts, HttpStatus.OK);
    }

    @GetMapping("/dumbclient")
    public ResponseEntity<?> getDumbClient(HttpServletRequest request){
        return ResponseEntity.ok().body("SUCCESS");
    }


}