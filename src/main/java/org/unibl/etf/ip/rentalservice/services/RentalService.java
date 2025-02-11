package org.unibl.etf.ip.rentalservice.services;

import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.core.CrudService;
import org.unibl.etf.ip.rentalservice.model.dto.Rental;
import org.unibl.etf.ip.rentalservice.model.entities.RentalEntity;
import org.unibl.etf.ip.rentalservice.model.requests.RentalRequest;

import java.util.List;

public interface RentalService extends CrudService<Integer> {
    // Find rentals by client ID
    List<Rental> findRentalsByClientId(Integer clientId);

    // Find rentals by vehicle ID
    List<Rental> findRentalsByVehicleId(Integer vehicleId);

    // Find active rentals (those without an end date)
    List<Rental> findActiveRentals();

    // Update rental duration
    Rental updateRentalDuration(Integer rentalId, Integer totalDurationMinutes);

    // Create new rental
    Rental createRental(RentalRequest rentalRequest);

    // Complete a rental (set the end date)
    Rental completeRental(Integer rentalId, RentalRequest rentalRequest);

    // Generate invoice pdf
    byte[] generateInvoicePdf(Rental rental);
}