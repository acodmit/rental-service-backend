package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Client;
import org.unibl.etf.ip.rentalservice.model.entities.ClientEntity;

import java.util.Optional;

public interface ClientEntityRepository extends JpaRepository<ClientEntity, Integer> {
    // Find by ID card number
    Optional<ClientEntity> findByIdCardNumber(String idCardNumber);
}
