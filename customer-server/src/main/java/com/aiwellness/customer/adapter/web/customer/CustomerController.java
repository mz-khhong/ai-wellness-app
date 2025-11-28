package com.aiwellness.customer.adapter.web.customer;

import com.aiwellness.customer.adapter.web.customer.dto.request.CustomerCreateRequest;
import com.aiwellness.customer.adapter.web.customer.dto.request.CustomerUpdateRequest;
import com.aiwellness.customer.adapter.web.customer.dto.response.CustomerResponse;
import com.aiwellness.customer.application.service.customer.CustomerService;
import com.aiwellness.customer.code.CustomerResponseCode;
import com.aiwellness.common.response.ApiResponseWellness;
import com.aiwellness.common.support.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customer", description = "고객 API")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{id}")
    @Operation(summary = "고객 조회", description = "ID로 고객 정보를 조회합니다.")
    public ApiResponseWellness<CustomerResponse> getCustomer(@PathVariable Long id) {
        return ApiResponseGenerator.success(customerService.getCustomer(id));
    }

    @PostMapping
    @Operation(summary = "고객 생성", description = "새로운 고객을 생성합니다.")
    public ResponseEntity<ApiResponseWellness<CustomerResponse>> createCustomer(
            @Valid @RequestBody CustomerCreateRequest request) {
        return ApiResponseGenerator.success(
                CustomerResponseCode.CUSTOMER_CREATE_SUCCESS, 
                customerService.createCustomer(request), 
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "고객 수정", description = "고객 정보를 수정합니다.")
    public ApiResponseWellness<CustomerResponse> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerUpdateRequest request) {
        return ApiResponseGenerator.success(
                CustomerResponseCode.CUSTOMER_UPDATE_SUCCESS, 
                customerService.updateCustomer(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "고객 삭제", description = "고객을 삭제합니다.")
    public ApiResponseWellness<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ApiResponseGenerator.success();
    }
}

