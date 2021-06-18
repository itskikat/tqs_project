package deti.tqs.g305.servicemanagement.restcontroller;

import deti.tqs.g305.servicemanagement.model.BusinessService;
import deti.tqs.g305.servicemanagement.model.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import deti.tqs.g305.servicemanagement.model.ServiceContract;
import deti.tqs.g305.servicemanagement.model.ServiceStatus;
import deti.tqs.g305.servicemanagement.service.ServiceService;

import java.util.List;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    // ProviderService
    @GetMapping("/services")
    public ResponseEntity<?> getProviderServices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required=false) String name,
            @RequestParam(defaultValue = "service_name") String sort,
            @RequestParam(defaultValue = "ASC") String order,
            HttpServletRequest request
    ) {
        Principal principal = request.getUserPrincipal();

        Pageable paging;
        Page<ProviderService> bsPage;

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
            bsPage = serviceService.getProviderProviderServices(principal.getName(), paging, Optional.of(name));
        }
        else {
            bsPage= serviceService.getProviderProviderServices(principal.getName(), paging, Optional.empty());
        }

        List <ProviderService> bsList;

        bsList = bsPage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("data", bsList);
        response.put("currentPage", bsPage.getNumber());
        response.put("totalItems", bsPage.getTotalElements());
        response.put("totalPages", bsPage.getTotalPages());

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/services")
    public ResponseEntity<?> createProviderService(@Valid @RequestBody(required = false) ProviderService bs){
        if(bs != null){
            Optional<ProviderService> optbs = serviceService.saveProviderService(bs);
            if(optbs.isPresent()){
                return new ResponseEntity<ProviderService>(bs, HttpStatus.OK);
            }
            return new ResponseEntity<String>("Bad Business Service!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Bad Business Service!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/services/{id}")
    public ResponseEntity<?> getProviderService(@PathVariable(value = "id") Long ProviderServiceId, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();

        Optional<ProviderService> optBs = serviceService.getProviderService(principal.getName(), ProviderServiceId);
        if(optBs.isPresent()){
            return new ResponseEntity<ProviderService>(optBs.get(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Could not find requested business service", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/services/{id}")
    public ResponseEntity<?> updateProviderService(@PathVariable(value = "id") Long ProviderServiceId, @Valid @RequestBody(required = false) ProviderService bs){
        if(bs != null){
            Optional<ProviderService> optBs = serviceService.updateProviderService(ProviderServiceId, bs);
            if(optBs.isPresent()){
                return new ResponseEntity<ProviderService>(bs, HttpStatus.OK);
            }
            return new ResponseEntity<String>("Could not find requested business service", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Bad Business Service!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/services/delete/{id}")
    public ResponseEntity<?> deleteProviderService(@PathVariable(value = "id") Long ProviderServiceId){
        if(ProviderServiceId != null) {
            boolean exists = serviceService.deleteProviderService(ProviderServiceId);
            if(exists){
                return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Business Service deleted");
            }
            return new ResponseEntity<String>("Could not find requested business service", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Could not find requested business service", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getProviderStatistics( @RequestParam(name = "start" , required=true) String start, 
     @RequestParam(name = "end" , required=true) String end, HttpServletRequest request){
        LocalDate end_date;
        LocalDate start_date;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            start_date= LocalDate.parse(start,dateTimeFormatter);
            end_date= LocalDate.parse(end,dateTimeFormatter);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad date format", HttpStatus.BAD_REQUEST);
        }
        

        Principal principal = request.getUserPrincipal();

        Map<String, Object> response = new HashMap();

        Optional<Double> profit= serviceService.getTotalProfit(principal.getName(), start_date, end_date);
        if(profit.isPresent()){
            response.put("TOTAL_PROFIT", profit);
        }

        Optional<Integer> finished= serviceService.getTotalFinished(principal.getName(), start_date, end_date);
        if(finished.isPresent()){
            response.put("TOTAL_FINISHED", finished);
        }

        Optional<ProviderService> ps= serviceService.getTotalMostProfitProviderService(principal.getName(), start_date, end_date);
        if(ps.isPresent()){
            response.put("PROFIT_SERVICE", ps);
        }

        Optional<ProviderService> ps1= serviceService.getTotalMostContractsProviderService(principal.getName(), start_date, end_date);
        if(ps.isPresent()){
            response.put("CONTRACTS_SERVICE", ps1);
        }

        Optional<Map<LocalDate,Double>> hist= serviceService.getProfitHistory(principal.getName(), start_date, end_date);
        if(hist.isPresent()){
            response.put("PROFIT_HISTORY", hist);
        }

        Optional<Map<LocalDate,Integer>> hist1= serviceService.getContractsHistory(principal.getName(), start_date, end_date);
        if(hist.isPresent()){
            response.put("CONTRACTS_HISTORY", hist1);
        }
        
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}