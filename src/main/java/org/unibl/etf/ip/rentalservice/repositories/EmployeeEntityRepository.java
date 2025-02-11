package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Employee;
import org.unibl.etf.ip.rentalservice.model.entities.EmployeeEntity;
import org.unibl.etf.ip.rentalservice.model.enums.UserType;

import java.util.List;

public interface EmployeeEntityRepository extends JpaRepository<EmployeeEntity, Integer> {
    // Find employees by role
    List<EmployeeEntity> findByRole(UserType role);
}
