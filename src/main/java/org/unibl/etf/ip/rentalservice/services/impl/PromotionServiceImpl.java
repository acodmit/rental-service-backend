package org.unibl.etf.ip.rentalservice.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.model.dto.Promotion;
import org.unibl.etf.ip.rentalservice.model.entities.PromotionEntity;
import org.unibl.etf.ip.rentalservice.repositories.PromotionEntityRepository;
import org.unibl.etf.ip.rentalservice.services.PromotionService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PromotionServiceImpl extends CrudJpaService<PromotionEntity, Integer> implements PromotionService {

    private final PromotionEntityRepository promotionEntityRepository;

    public PromotionServiceImpl(PromotionEntityRepository promotionEntityRepository, ModelMapper modelMapper) {
        super(promotionEntityRepository, PromotionEntity.class, modelMapper);
        this.promotionEntityRepository = promotionEntityRepository;
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
}

