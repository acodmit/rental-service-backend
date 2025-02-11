package org.unibl.etf.ip.rentalservice.services.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.model.dto.Rental;
import org.unibl.etf.ip.rentalservice.model.dto.User;
import org.unibl.etf.ip.rentalservice.model.dto.Vehicle;
import org.unibl.etf.ip.rentalservice.model.entities.RentalEntity;
import org.unibl.etf.ip.rentalservice.model.requests.RentalRequest;
import org.unibl.etf.ip.rentalservice.repositories.RentalEntityRepository;
import org.unibl.etf.ip.rentalservice.services.RentalService;
import org.unibl.etf.ip.rentalservice.services.UserService;
import org.unibl.etf.ip.rentalservice.services.VehicleService;

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
    private final UserService userService;
    private final VehicleService vehicleService;

    public RentalServiceImpl(RentalEntityRepository rentalEntityRepository, ModelMapper modelMapper, UserService userService,
                             VehicleService vehicleService) {
        super(rentalEntityRepository, RentalEntity.class, modelMapper);
        this.rentalEntityRepository = rentalEntityRepository;
        this.userService = userService;
        this.vehicleService = vehicleService;
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

    public Rental createRental(RentalRequest rentalRequest) {
        // Validating if the user and vehicle exist
        if (userService.findById(rentalRequest.getClientId(), User.class) == null ||
                vehicleService.findById(rentalRequest.getVehicleId(), Vehicle.class) == null) {
            throw new IllegalArgumentException("User or Vehicle not found.");
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
        Rental rental = findById(rentalId, Rental.class);
        if (rental == null || rental.getEndDate() != null) {
            return null; // Already finished or not found
        }

        RentalEntity rentalEntity = getModelMapper().map(rental, RentalEntity.class);
        rentalEntity.setEndDate(new Timestamp(System.currentTimeMillis()));

        // Calculate total duration in minutes
        long durationMinutes = (rentalEntity.getEndDate().getTime() - rentalEntity.getStartDate().getTime()) / (60 * 1000);
        rentalEntity.setTotalDurationMinutes((int) durationMinutes);

        BigDecimal totalPrice;
        BigDecimal hourlyRate = rentalEntity.getVehicle().getHourlyRate();
        BigDecimal durationInHours = BigDecimal.valueOf(durationMinutes).divide(BigDecimal.valueOf(60), RoundingMode.HALF_UP);

        totalPrice = hourlyRate.multiply(durationInHours.compareTo(BigDecimal.ONE) < 1 ? BigDecimal.ONE : durationInHours);
        rentalEntity.setTotalPrice(totalPrice);

        RentalEntity updatedRentalEntity = rentalEntityRepository.saveAndFlush(rentalEntity);
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
