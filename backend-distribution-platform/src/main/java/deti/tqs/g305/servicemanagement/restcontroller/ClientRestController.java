package deti.tqs.g305.servicemanagement.restcontroller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import deti.tqs.g305.servicemanagement.service.ServiceService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * ClientRestController
 */

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {

    @Autowired
    private ServiceService serviceService;
}
