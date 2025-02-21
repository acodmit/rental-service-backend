package org.unibl.etf.ip.rentalservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.ip.rentalservice.core.CrudController;
import org.unibl.etf.ip.rentalservice.exceptions.NotFoundException;
import org.unibl.etf.ip.rentalservice.model.dto.Promotion;
import org.unibl.etf.ip.rentalservice.model.requests.PromotionRequest;
import org.unibl.etf.ip.rentalservice.services.PromotionService;

@RestController
@RequestMapping("/promotions")
public class PromotionController extends CrudController<Integer, PromotionRequest, Promotion> {

    private final PromotionService promotionService;

    public PromotionController(final PromotionService promotionService) {
        super(Promotion.class, promotionService);
        this.promotionService = promotionService;
    }

    @Override
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody PromotionRequest request) throws NotFoundException {
        Promotion response = promotionService.insertPromotion(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
