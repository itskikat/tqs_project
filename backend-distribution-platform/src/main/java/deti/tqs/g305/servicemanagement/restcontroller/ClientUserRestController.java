package deti.tqs.g305.servicemanagement.restcontroller;

import deti.tqs.g305.servicemanagement.model.Business;
import deti.tqs.g305.servicemanagement.model.Client;
import deti.tqs.g305.servicemanagement.service.BusinessUserService;
import deti.tqs.g305.servicemanagement.service.ClientUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/api/client"})
public class ClientUserRestController {

    private static final Logger log = LoggerFactory.getLogger(ClientUserRestController.class);

    @Autowired
    private ClientUserService clientUserService;

    @PostMapping(path = {"/"})
    public Optional<Client> create(@RequestBody Client client){
        log.info("Creating client {} with password {}", client.getEmail(), client.getPassword());
        return clientUserService.create(client);
    }

    @PutMapping(value="/{email}")
    public ResponseEntity<?> update(@PathVariable("email") String email, @RequestBody Client client) {
        log.info("Updating client {} with data: {}", email, client);
        Optional<Client> b = clientUserService.update(email, client);
        if(b.isPresent()){
            return ResponseEntity.ok().body(b.get());
        }
        return new ResponseEntity<String>("Bad business", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = {"/"})
    public Optional<Client> get(HttpServletRequest request){
        return clientUserService.getLogged(request.getUserPrincipal().getName());
    }
}
