package org.unibl.etf.ip.rentalservice.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.model.dto.Client;
import org.unibl.etf.ip.rentalservice.model.entities.ClientEntity;
import org.unibl.etf.ip.rentalservice.repositories.ClientEntityRepository;
import org.unibl.etf.ip.rentalservice.services.ClientService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientServiceImpl extends CrudJpaService<ClientEntity, Integer> implements ClientService {

    private final ClientEntityRepository clientEntityRepository;

    public ClientServiceImpl(ClientEntityRepository clientEntityRepository, ModelMapper modelMapper) {
        super(clientEntityRepository, ClientEntity.class, modelMapper);
        this.clientEntityRepository = clientEntityRepository;
    }

    @Override
    public Client findByIdCardNumber(String idCardNumber) {
        ClientEntity clientEntity = clientEntityRepository.findByIdCardNumber(idCardNumber)
                .orElseThrow(() -> new EntityNotFoundException("Client with ID card number " + idCardNumber + " not found"));
        return getModelMapper().map(clientEntity, Client.class);
    }

//    @Override
//    public List<Client> findAllClientsWithUsers() {
//        List<ClientEntity> clientEntities = clientEntityRepository.findAll();
//        return clientEntities.stream()
//                .map(client -> {
//                    Client dto = getModelMapper().map(client, Client.class);
//                    dto.setId(client.getUser().getId()); // Mapping UserEntity ID
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
}