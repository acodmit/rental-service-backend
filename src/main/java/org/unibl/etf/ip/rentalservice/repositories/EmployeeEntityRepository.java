package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Employee;
import org.unibl.etf.ip.rentalservice.model.enums.UserType;

import java.util.List;

public interface EmployeeEntityRepository extends JpaRepository<Employee, Integer> {
    // Find employees by role
    List<Employee> findByRole(UserType role);
}
