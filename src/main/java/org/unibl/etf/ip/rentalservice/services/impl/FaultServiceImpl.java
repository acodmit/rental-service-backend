package org.unibl.etf.ip.rentalservice.services.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.exceptions.NotFoundException;
import org.unibl.etf.ip.rentalservice.model.dto.Fault;
import org.unibl.etf.ip.rentalservice.model.dto.Vehicle;
import org.unibl.etf.ip.rentalservice.model.entities.FaultEntity;
import org.unibl.etf.ip.rentalservice.model.entities.VehicleEntity;
import org.unibl.etf.ip.rentalservice.model.requests.FaultRequest;
import org.unibl.etf.ip.rentalservice.repositories.FaultEntityRepository;
import org.unibl.etf.ip.rentalservice.repositories.VehicleEntityRepository;
import org.unibl.etf.ip.rentalservice.services.FaultService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FaultServiceImpl extends CrudJpaService<FaultEntity, Integer> implements FaultService {

    private final FaultEntityRepository faultEntityRepository;
    private final VehicleEntityRepository vehicleEntityRepository;

    public FaultServiceImpl(FaultEntityRepository faultEntityRepository, VehicleEntityRepository vehicleEntityRepository, ModelMapper modelMapper) {
        super(faultEntityRepository, FaultEntity.class, modelMapper);
        this.faultEntityRepository = faultEntityRepository;
        this.vehicleEntityRepository = vehicleEntityRepository;
    }

    @Override
    public List<Fault> findByVehicleId(Integer vehicleId) {
        List<FaultEntity> faults = faultEntityRepository.findByVehicleId(vehicleId);
        return faults.stream()
                .map(fault -> getModelMapper().map(fault, Fault.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public Fault addFault(FaultRequest faultRequest) {
        // Check if the vehicle exists
        VehicleEntity vehicleEntity = vehicleEntityRepository.findById(faultRequest.getVehicleId())
                .orElseThrow(() -> new NotFoundException("Vehicle with ID " + faultRequest.getVehicleId() + " not found"));

        // Update isBroken if it's currently false
        if (Boolean.FALSE.equals(vehicleEntity.getIsBroken())) {
            vehicleEntity.setIsBroken(true);
            vehicleEntityRepository.save(vehicleEntity); // Persist the change
        }

        // Map the FaultRequest to Fault entity
        FaultEntity faultEntity = getModelMapper().map(faultRequest, FaultEntity.class);
        faultEntity.setDescription(faultRequest.getDescription());
        faultEntity.setReportedDate(faultRequest.getReportedDate());
        faultEntity.setVehicle(vehicleEntity); // Set the vehicle relationship

        // Save the fault
        FaultEntity savedFault = faultEntityRepository.save(faultEntity);

        return getModelMapper().map(savedFault, Fault.class);
    }

    public boolean deleteFault(Integer faultId) {
        // Check if the fault exists
        FaultEntity faultEntity = faultEntityRepository.findById(faultId)
                .orElseThrow(() -> new NotFoundException("Fault with ID " + faultId + " not found"));

        // Get the associated vehicle
        VehicleEntity vehicleEntity = faultEntity.getVehicle();

        // Delete the fault
        faultEntityRepository.delete(faultEntity);

        // Check if the vehicle has any remaining faults
        if (faultEntityRepository.findByVehicleId(vehicleEntity.getId()).isEmpty()) {
            vehicleEntity.setIsBroken(false);
            vehicleEntityRepository.save(vehicleEntity); // Persist the change
        }

        return true;
    }

    /*@Override
    public List<Fault> findByReportedDateBetween(Timestamp startDate, Timestamp endDate) {
        List<FaultEntity> faults = faultEntityRepository.findByReportedDateBetween(startDate, endDate);
        return faults.stream()
                .map(fault -> getModelMapper().map(fault, Fault.class))
                .collect(Collectors.toList());
    }*/

}
