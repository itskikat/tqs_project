package deti.tqs.g305.servicemanagement.restcontroller;

import deti.tqs.g305.servicemanagement.model.Client;
import deti.tqs.g305.servicemanagement.service.ClientUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping({"/api"})
public class ClientUserController {

    @Autowired
    private ClientUserService clientUserService;

    //Obtendo um client específico pelo email (GET /client/{email})
    @GetMapping(path = {"/client/{email}"})
    public ResponseEntity<?> findByEmail(@PathVariable String email){
        return clientUserService.findByEmail(email)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }


    //Criando um novo client (POST /client)
    @PostMapping(path = {"/client"})
    public Optional<Client> create(@RequestBody Client client){
        return clientUserService.create(client);
    }

    //Atualizando um client (PUT /client)
    @PutMapping(value={"/client/{email}"})
    public ResponseEntity<?> update(@PathVariable("email") String email,
                                            @RequestBody Client client) {
        return ResponseEntity.ok().body(clientUserService.update(email, client));
    }

    //Removendo um client pelo email (DELETE /client/{email})
    @DeleteMapping(path ={"/client/{email}"})
    public ResponseEntity<?> delete(@PathVariable String email) {
        return clientUserService.findByEmail(email)
                .map(record -> {
                    clientUserService.delete(email);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
