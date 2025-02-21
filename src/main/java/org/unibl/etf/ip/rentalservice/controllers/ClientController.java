package org.unibl.etf.ip.rentalservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.ip.rentalservice.core.CrudController;
import org.unibl.etf.ip.rentalservice.exceptions.NotFoundException;
import org.unibl.etf.ip.rentalservice.model.dto.Client;
import org.unibl.etf.ip.rentalservice.model.requests.ClientRequest;
import org.unibl.etf.ip.rentalservice.services.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController extends CrudController<Integer, ClientRequest, Client> {

    private final ClientService clientService;

    public ClientController(final ClientService clientService) {
        super(Client.class, clientService);
        this.clientService = clientService;
    }

    @Override
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody ClientRequest request) throws NotFoundException {
        Client response = clientService.insertClient(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
