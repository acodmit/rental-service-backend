package org.unibl.etf.ip.rentalservice.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.model.dto.Fault;
import org.unibl.etf.ip.rentalservice.model.dto.Vehicle;
import org.unibl.etf.ip.rentalservice.model.entities.FaultEntity;
import org.unibl.etf.ip.rentalservice.repositories.FaultEntityRepository;
import org.unibl.etf.ip.rentalservice.services.FaultService;
import org.unibl.etf.ip.rentalservice.services.VehicleService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FaultServiceImpl extends CrudJpaService<FaultEntity, Integer> implements FaultService {

    private final FaultEntityRepository faultEntityRepository;

    public FaultServiceImpl(FaultEntityRepository faultEntityRepository, ModelMapper modelMapper) {
        super(faultEntityRepository, FaultEntity.class, modelMapper);
        this.faultEntityRepository = faultEntityRepository;
    }

    @Override
    public List<Fault> findByVehicleId(Integer vehicleId) {
        List<FaultEntity> faults = faultEntityRepository.findByVehicleId(vehicleId);
        return faults.stream()
                .map(fault -> getModelMapper().map(fault, Fault.class))
                .collect(Collectors.toList());
    }

    /*@Override
    public List<Fault> findByReportedDateBetween(Timestamp startDate, Timestamp endDate) {
        List<FaultEntity> faults = faultEntityRepository.findByReportedDateBetween(startDate, endDate);
        return faults.stream()
                .map(fault -> getModelMapper().map(fault, Fault.class))
                .collect(Collectors.toList());
    }*/

}
