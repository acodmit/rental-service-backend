package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Promotion;

import java.util.List;

public interface PromotionEntityRepository extends JpaRepository<Promotion, Integer> {
    // Find promotions by title
    List<Promotion> findByTitleContaining(String title);

    // Find active promotions
    List<Promotion> findByEndDateAfterOrEndDateIsNull(java.util.Date currentDate);
}
