package com.lecuong.sourcebase.controller.user.employee;

import com.lecuong.sourcebase.modal.request.employee.EmployeeRequest;
import com.lecuong.sourcebase.modal.response.BaseResponse;
import com.lecuong.sourcebase.modal.response.EmployeeResponse;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author CuongLM18
 * @created 28/03/2023 - 10:58 AM
 * @project source-base
 */
@Data
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> save(@RequestBody EmployeeRequest employeeRequest) {
        employeeService.save(employeeRequest);
        return ResponseEntity.ok(BaseResponse.ofSuccess(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<EmployeeResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(BaseResponse.ofSuccess(employeeService.findById(id)));
    }
}
