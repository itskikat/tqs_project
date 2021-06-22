package deti.tqs.g305.handymanservicesapp.controller;

import deti.tqs.g305.handymanservicesapp.service.GeneralService;
import deti.tqs.g305.handymanservicesapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GeneralController {

    private static final Logger log = LoggerFactory.getLogger(GeneralController.class);

    @Autowired
    private GeneralService generalService;

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

}
