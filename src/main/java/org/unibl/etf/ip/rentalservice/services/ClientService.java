package org.unibl.etf.ip.rentalservice.services;

import org.unibl.etf.ip.rentalservice.core.CrudService;
import org.unibl.etf.ip.rentalservice.model.dto.Client;
import org.unibl.etf.ip.rentalservice.model.requests.ClientRequest;

import java.util.List;

public interface ClientService extends CrudService<Integer> {
    // Find clients by ID card number
    Client findByIdCardNumber(String idCardNumber);

    // Get all clients with their user details
    //List<Client> findAllClientsWithUsers();

    // Insert a new client
    Client insertClient(ClientRequest clientRequest);
}
