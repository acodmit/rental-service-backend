package org.unibl.etf.ip.rentalservice.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.exceptions.NotFoundException;
import org.unibl.etf.ip.rentalservice.model.dto.Promotion;
import org.unibl.etf.ip.rentalservice.model.entities.ClientEntity;
import org.unibl.etf.ip.rentalservice.model.entities.EmployeeEntity;
import org.unibl.etf.ip.rentalservice.model.entities.PromotionEntity;
import org.unibl.etf.ip.rentalservice.model.entities.UserEntity;
import org.unibl.etf.ip.rentalservice.model.requests.PromotionRequest;
import org.unibl.etf.ip.rentalservice.repositories.ClientEntityRepository;
import org.unibl.etf.ip.rentalservice.repositories.EmployeeEntityRepository;
import org.unibl.etf.ip.rentalservice.repositories.PromotionEntityRepository;
import org.unibl.etf.ip.rentalservice.repositories.UserEntityRepository;
import org.unibl.etf.ip.rentalservice.services.PromotionService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PromotionServiceImpl extends CrudJpaService<PromotionEntity, Integer> implements PromotionService {

    private final PromotionEntityRepository promotionEntityRepository;
    private final EmployeeEntityRepository employeeEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final ClientEntityRepository clientEntityRepository;

    public PromotionServiceImpl(PromotionEntityRepository promotionEntityRepository, ModelMapper modelMapper, EmployeeEntityRepository employeeEntityRepository, UserEntityRepository userEntityRepository, ClientEntityRepository clientEntityRepository) {
        super(promotionEntityRepository, PromotionEntity.class, modelMapper);
        this.promotionEntityRepository = promotionEntityRepository;
        this.employeeEntityRepository = employeeEntityRepository;
        this.userEntityRepository = userEntityRepository;
        this.clientEntityRepository = clientEntityRepository;
    }

    @Override
    public List<Promotion> findByTitleContaining(String title) {
        List<PromotionEntity> promotions = promotionEntityRepository.findByTitleContaining(title);
        return promotions.stream()
                .map(promotion -> getModelMapper().map(promotion, Promotion.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Promotion> findActivePromotions(Date currentDate) {
        List<PromotionEntity> activePromotions = promotionEntityRepository.findByEndDateAfterOrEndDateIsNull(currentDate);
        return activePromotions.stream()
                .map(promotion -> getModelMapper().map(promotion, Promotion.class))
                .collect(Collectors.toList());
    }

    @Override
    public Promotion insertPromotion(PromotionRequest request) {
        PromotionEntity promotionEntity = getModelMapper().map(request, PromotionEntity.class);

        // Get the current authenticated user's username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String employeeUsername = ((UserDetails) authentication.getPrincipal()).getUsername();

        // Find the EmployeeEntity
        UserEntity userEntity = userEntityRepository.findByUsername(employeeUsername)
                .orElseThrow(() -> new NotFoundException("User not found"));
        EmployeeEntity employeeEntity = employeeEntityRepository.findById(userEntity.getId())
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        // Set employee entity in promotion
        promotionEntity.setEmployee(employeeEntity);

        // Save and return the promotion
        promotionEntity = promotionEntityRepository.saveAndFlush(promotionEntity);
        getEntityManager().refresh(promotionEntity);
        return getModelMapper().map(promotionEntity, Promotion.class);
    }
}