package com.lecuong.sourcebase.repository;

import com.lecuong.sourcebase.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author CuongLM18
 * @created 28/03/2023 - 10:48 AM
 * @project source-base
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
