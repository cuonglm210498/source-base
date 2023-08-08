package com.lecuong.sourcebase.mapper;

import com.lecuong.sourcebase.entity.Employee;
import com.lecuong.sourcebase.modal.request.employee.EmployeeRequest;
import com.lecuong.sourcebase.modal.response.employee.EmployeeResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author CuongLM18
 * @created 28/03/2023 - 10:51 AM
 * @project source-base
 */
@Component
@AllArgsConstructor
public class EmployeeMapper {

    private ModelMapper modelMapper;

    public Employee to(EmployeeRequest employeeRequest) {
        return modelMapper.map(employeeRequest, Employee.class);
    }

    public EmployeeResponse to(Employee employee) {
        return modelMapper.map(employee, EmployeeResponse.class);
    }
}
