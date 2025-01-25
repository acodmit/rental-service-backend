package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Client;

public interface ClientEntityRepository extends JpaRepository<Client, Integer> {
    // Find by ID card number
    Client findByIdCardNumber(String idCardNumber);
}
