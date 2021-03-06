package deti.tqs.g305.handymanservicesapp.controller;

import deti.tqs.g305.handymanservicesapp.model.District;
import deti.tqs.g305.handymanservicesapp.model.ServiceContract;
import deti.tqs.g305.handymanservicesapp.service.GeneralService;
import deti.tqs.g305.handymanservicesapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GeneralController {

    private static final Logger log = LoggerFactory.getLogger(GeneralController.class);

    @Autowired
    private GeneralService generalService;

    // Register
    @GetMapping("/districts")
    public ResponseEntity<List> getDistricts(HttpServletRequest request){
        return ResponseEntity.ok().body(generalService.getDistricts(request));
    }

    @GetMapping("/districts/{id}")
    public ResponseEntity<List> getDistricts(@PathVariable(value = "id") Long districtId, HttpServletRequest request){
        return ResponseEntity.ok().body(generalService.getDistrictCities(districtId, request));
    }

    // Past services
    @GetMapping("/contracts")
    public ResponseEntity<Map> getServiceContracts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(required=false) String status,
        @RequestParam(defaultValue = "date") String sort,
        @RequestParam(defaultValue = "ASC") String order,
        @RequestParam(defaultValue = "10") int size,
        HttpServletRequest request
    ) {
        return ResponseEntity.ok(generalService.getContracts(page, status, sort, order, size, request));
    }

    @GetMapping("/contracts/{id}")
    public ResponseEntity<?> getServiceContract(
        @PathVariable(value = "id") Long serviceContractId,
        HttpServletRequest request
    ){
        return ResponseEntity.ok(generalService.getContract(serviceContractId, request));
    }

    @PutMapping("/contracts/{id}")
    public ResponseEntity<?> updateServiceContract(
        @PathVariable(value = "id") Long serviceContractId,
        @Valid @RequestBody(required = true) ServiceContract sc,
        HttpServletRequest request
    ){
        return ResponseEntity.ok(generalService.updateContract(serviceContractId, sc, request));
    }

    // Dynamic matching
    @GetMapping("/services")
    public ResponseEntity<List> getBusinessServices(HttpServletRequest request) { // Returns List<BusinessService>
        return ResponseEntity.ok(generalService.services(request));
    }

    @GetMapping("/matches/{id}")
    public ResponseEntity<List> getMatchingServiceProviders( // Returns List<ProviderService>
        @PathVariable(value = "id") Long serviceTypeId,
        HttpServletRequest request
    ) {
        return ResponseEntity.ok(generalService.match(serviceTypeId, request));
    }

    @GetMapping("/services/{id}")
    public ResponseEntity<Optional> getService(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request
    ) { // Returns Optional<ProviderService>
        return ResponseEntity.ok(generalService.getService(id, request));
    }

    @PostMapping("/contracts")
    public ResponseEntity<?> createServiceContract(
        @Valid @RequestBody(required = false) ServiceContract sc,
        HttpServletRequest request
    ) {
        return ResponseEntity.ok(generalService.createContract(sc, request));
    }

}
