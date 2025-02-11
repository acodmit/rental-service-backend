package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Promotion;
import org.unibl.etf.ip.rentalservice.model.entities.PromotionEntity;

import java.util.List;

public interface PromotionEntityRepository extends JpaRepository<PromotionEntity, Integer> {
    // Find promotions by title
    List<PromotionEntity> findByTitleContaining(String title);

    // Find active promotions
    List<PromotionEntity> findByEndDateAfterOrEndDateIsNull(java.util.Date currentDate);
}
