package org.unibl.etf.ip.rentalservice.controllers;

import jakarta.validation.Valid;
import org.springframework.http.*;
import org.unibl.etf.ip.rentalservice.core.CrudController;
import org.unibl.etf.ip.rentalservice.model.dto.Rental;
import org.unibl.etf.ip.rentalservice.model.requests.RentalRequest;
import org.unibl.etf.ip.rentalservice.services.RentalService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rentals")
public class RentalController extends CrudController<Integer, RentalRequest, Rental> {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        super(Rental.class, rentalService);
        this.rentalService = rentalService;
    }

    @Override
    @PostMapping
    public ResponseEntity<Rental> insert(@Valid @RequestBody RentalRequest rentalRequest) {
        try {
            Rental createdRental = rentalService.createRental(rentalRequest);
            return new ResponseEntity<>(createdRental, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Rental> complete(@PathVariable int id, @RequestBody RentalRequest rentalRequest) {
        Rental updatedRental = rentalService.completeRental(id, rentalRequest);
        return updatedRental != null ? new ResponseEntity<>(updatedRental, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/invoice")
    public ResponseEntity<byte[]> generateInvoice(@PathVariable int id) {
        Rental rental = rentalService.findById(id, Rental.class);
        if (rental == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Call the service method to generate the invoice PDF
        byte[] invoicePdf = rentalService.generateInvoicePdf(rental);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("invoice.pdf").build());

        return new ResponseEntity<>(invoicePdf, headers, HttpStatus.OK);
    }

}