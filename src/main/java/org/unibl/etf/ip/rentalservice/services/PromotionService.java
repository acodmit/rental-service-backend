package org.unibl.etf.ip.rentalservice.services;

import org.unibl.etf.ip.rentalservice.core.CrudService;
import org.unibl.etf.ip.rentalservice.model.dto.Promotion;
import org.unibl.etf.ip.rentalservice.model.requests.PromotionRequest;

import java.util.Date;
import java.util.List;

public interface PromotionService extends CrudService<Integer> {
    // find promotions by title containing a specific keyword
    List<Promotion> findByTitleContaining(String title);

    // find active promotions
    List<Promotion> findActivePromotions(Date currentDate);

    // Insert a new promotion
    Promotion insertPromotion(PromotionRequest request);
}
