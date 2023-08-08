package com.lecuong.sourcebase.service;

import com.lecuong.sourcebase.modal.request.employee.EmployeeRequest;
import com.lecuong.sourcebase.modal.response.employee.EmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author CuongLM18
 * @created 28/03/2023 - 10:47 AM
 * @project source-base
 */
public interface EmployeeService {

    void save(EmployeeRequest employeeRequest);

    EmployeeResponse findById(Long id);

    Page<EmployeeResponse> getAll(Pageable pageable);
}
