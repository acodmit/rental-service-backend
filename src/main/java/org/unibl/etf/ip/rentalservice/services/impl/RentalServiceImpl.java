package org.unibl.etf.ip.rentalservice.services.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.exceptions.NotFoundException;
import org.unibl.etf.ip.rentalservice.model.dto.Rental;
import org.unibl.etf.ip.rentalservice.model.entities.RentalEntity;
import org.unibl.etf.ip.rentalservice.model.requests.RentalRequest;
import org.unibl.etf.ip.rentalservice.repositories.*;
import org.unibl.etf.ip.rentalservice.services.RentalService;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RentalServiceImpl extends CrudJpaService<RentalEntity, Integer> implements RentalService {

    private final RentalEntityRepository rentalEntityRepository;
    private final VehicleEntityRepository vehicleEntityRepository;
    private final ClientEntityRepository clientEntityRepository;
    private final LocationEntityRepository locationEntityRepository;

    public RentalServiceImpl(RentalEntityRepository rentalEntityRepository, ModelMapper modelMapper,
                             VehicleEntityRepository vehicleEntityRepository, ClientEntityRepository clientEntityRepository,
                             LocationEntityRepository locationEntityRepository) {
        super(rentalEntityRepository, RentalEntity.class, modelMapper);
        this.rentalEntityRepository = rentalEntityRepository;
        this.vehicleEntityRepository = vehicleEntityRepository;
        this.clientEntityRepository = clientEntityRepository;
        this.locationEntityRepository = locationEntityRepository;
    }

    @Override
    public List<Rental> findRentalsByClientId(Integer clientId) {
        List<RentalEntity> rentals = rentalEntityRepository.findByClientId(clientId);
        return rentals.stream()
                .map(rental -> getModelMapper().map(rental, Rental.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Rental> findRentalsByVehicleId(Integer vehicleId) {
        List<RentalEntity> rentals = rentalEntityRepository.findByVehicleId(vehicleId);
        return rentals.stream()
                .map(rental -> getModelMapper().map(rental, Rental.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Rental> findActiveRentals() {
        List<RentalEntity> activeRentals = rentalEntityRepository.findByEndDateIsNull();
        return activeRentals.stream()
                .map(rental -> getModelMapper().map(rental, Rental.class))
                .collect(Collectors.toList());
    }

    @Override
    public Rental updateRentalDuration(Integer rentalId, Integer totalDurationMinutes) {
        Rental rental = findById(rentalId, Rental.class);
        RentalEntity rentalEntity = getModelMapper().map(rental, RentalEntity.class);
        rentalEntity.setTotalDurationMinutes(totalDurationMinutes);
        RentalEntity updatedRentalEntity = rentalEntityRepository.saveAndFlush(rentalEntity);
        return getModelMapper().map(updatedRentalEntity, Rental.class);
    }

    public Rental addRental(RentalRequest rentalRequest) {
        // Validating if the arguments exist in the database
        if (!clientEntityRepository.existsById(rentalRequest.getClientId()) ||
                !vehicleEntityRepository.existsById(rentalRequest.getVehicleId()) ||
                !locationEntityRepository.existsById(rentalRequest.getPickUpLocationId()) ||
                !locationEntityRepository.existsById(rentalRequest.getDropOffLocationId())) {
            throw new IllegalArgumentException("Client or Vehicle not found.");
        }

        // Set the start date (current time)
        rentalRequest.setStartDate(Timestamp.valueOf(LocalDateTime.now()));
        rentalRequest.setTotalDurationMinutes(0); // Will be updated later when the endDate is available
        rentalRequest.setTotalPrice(BigDecimal.valueOf(0)); // Initially 0, will be updated later

        // Save and return the created rental
        RentalEntity rentalEntity = getModelMapper().map(rentalRequest, RentalEntity.class);

        RentalEntity createdRentalEntity = rentalEntityRepository.save(rentalEntity);
        return getModelMapper().map(createdRentalEntity, Rental.class);
    }


    @Override
    public Rental completeRental(Integer rentalId, RentalRequest rentalRequest) {
        RentalEntity rentalEntity = rentalEntityRepository.findById(rentalId)
                .orElseThrow(() -> new NotFoundException("Rental not found"));

        Rental rental = getModelMapper().map(rentalEntity, Rental.class);

        // Set the end date (current time)
        rental.setEndDate(new Timestamp(System.currentTimeMillis()));

        // Calculate total duration in minutes
        long durationMinutes = (rental.getEndDate().getTime() - rental.getStartDate().getTime()) / (60 * 1000);
        rental.setTotalDurationMinutes((int) durationMinutes);

        // Calculate total price
        BigDecimal hourlyRate = rental.getVehicle().getHourlyRate();
        BigDecimal durationInHours = BigDecimal.valueOf(durationMinutes).divide(BigDecimal.valueOf(60), RoundingMode.HALF_UP);
        rental.setTotalPrice(hourlyRate.multiply(durationInHours.compareTo(BigDecimal.ONE) < 1 ? BigDecimal.ONE : durationInHours));

        RentalEntity updatedRentalEntity = getModelMapper().map(rental, RentalEntity.class);

        updatedRentalEntity = rentalEntityRepository.saveAndFlush(updatedRentalEntity);
        return getModelMapper().map(updatedRentalEntity, Rental.class);
    }


    @Override
    public byte[] generateInvoicePdf(Rental rental) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Create PDF document
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Add content to the PDF
            addInvoiceContent(document, rental);

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating invoice PDF", e);
        }
    }

    private void addInvoiceContent(Document document, Rental rental) throws DocumentException {
        // Add content to the PDF
        document.add(new Paragraph("Rental Invoice"));
        document.add(new Paragraph("Invoice ID: " + rental.getId()));
        document.add(new Paragraph("Client: " + rental.getClient().getFirstName() + " " + rental.getClient().getLastName()));
        document.add(new Paragraph("Vehicle: " + rental.getVehicle().getModel() + " (" + rental.getVehicle().getManufacturer().getName() + ")"));
        document.add(new Paragraph("Start Date: " + rental.getStartDate()));
        document.add(new Paragraph("End Date: " + rental.getEndDate()));
        document.add(new Paragraph("Total Duration: " + rental.getTotalDurationMinutes() + " minutes"));
        document.add(new Paragraph("Total Price: $" + rental.getTotalPrice()));
    }

}
