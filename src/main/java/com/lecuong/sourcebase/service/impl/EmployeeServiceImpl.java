package com.lecuong.sourcebase.service.impl;

import com.lecuong.sourcebase.entity.Employee;
import com.lecuong.sourcebase.exception.BusinessException;
import com.lecuong.sourcebase.exception.StatusTemplate;
import com.lecuong.sourcebase.mapper.EmployeeMapper;
import com.lecuong.sourcebase.modal.request.employee.EmployeeRequest;
import com.lecuong.sourcebase.modal.response.EmployeeResponse;
import com.lecuong.sourcebase.repository.EmployeeRepository;
import com.lecuong.sourcebase.service.EmployeeService;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author CuongLM18
 * @created 28/03/2023 - 10:48 AM
 * @project source-base
 */
@Data
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    @Override
    public void save(EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.to(employeeRequest);
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeResponse findById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employeeMapper.to(employee.get());
        } else {
            throw new BusinessException(StatusTemplate.EMPLOYEE_NOT_FOUND);
        }
    }

    @Override
    public Page<EmployeeResponse> getAll(Pageable pageable) {
        Page<Employee> employees = employeeRepository.findAll(pageable.previousOrFirst());
        return employees.map(employeeMapper::to);
    }
}
