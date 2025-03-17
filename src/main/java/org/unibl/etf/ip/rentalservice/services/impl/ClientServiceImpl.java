package org.unibl.etf.ip.rentalservice.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.exceptions.NotFoundException;
import org.unibl.etf.ip.rentalservice.model.dto.Client;
import org.unibl.etf.ip.rentalservice.model.entities.ClientEntity;
import org.unibl.etf.ip.rentalservice.model.requests.ClientRequest;
import org.unibl.etf.ip.rentalservice.repositories.ClientEntityRepository;
import org.unibl.etf.ip.rentalservice.repositories.UserEntityRepository;
import org.unibl.etf.ip.rentalservice.services.ClientService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientServiceImpl extends CrudJpaService<ClientEntity, Integer> implements ClientService {

    private final ClientEntityRepository clientEntityRepository;
    private final UserEntityRepository userEntityRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ClientServiceImpl(ClientEntityRepository clientEntityRepository, ModelMapper modelMapper,
                             UserEntityRepository userEntityRepository) {
        super(clientEntityRepository, ClientEntity.class, modelMapper);
        this.clientEntityRepository = clientEntityRepository;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public Client findByIdCardNumber(String idCardNumber) {
        ClientEntity clientEntity = clientEntityRepository.findByCardNumber(idCardNumber)
                .orElseThrow(() -> new EntityNotFoundException("Client with ID card number " + idCardNumber + " not found"));
        return getModelMapper().map(clientEntity, Client.class);
    }

    @Override
    public Client insertClient(ClientRequest clientRequest) {
        if(userEntityRepository.existsByUsername(clientRequest.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        ClientEntity clientEntity = getModelMapper().map(clientRequest, ClientEntity.class);
        clientEntity.setId(null); // Ensure it's a new entity
        clientEntity.setPasswordHash(passwordEncoder.encode(clientRequest.getPassword()));
        clientEntity = clientEntityRepository.saveAndFlush(clientEntity);
        getEntityManager().refresh(clientEntity);
        return getModelMapper().map(clientEntity, Client.class);
    }

    public void toggleBlockStatus(Integer clientId, boolean isBlocked) {
        var client = clientEntityRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found with id: " + clientId));
        client.setIsBlocked(isBlocked);
        clientEntityRepository.save(client);
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