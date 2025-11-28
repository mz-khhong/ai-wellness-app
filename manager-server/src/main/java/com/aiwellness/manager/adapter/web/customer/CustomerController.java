package com.aiwellness.manager.adapter.web.customer;

import com.aiwellness.common.response.ApiResponseWellness;
import com.aiwellness.common.security.CurrentUser;
import com.aiwellness.common.security.annotation.AuthenticatedUser;
import com.aiwellness.common.support.ApiResponseGenerator;
import com.aiwellness.manager.adapter.web.customer.dto.response.CustomerResponse;
import com.aiwellness.manager.application.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * com.aiwellness.manager.adapter.web.customer
 * <p>
 * CustomerController
 * <p>
 * 고객 REST API 컨트롤러
 * <p>
 * 매니저가 자신의 시설 그룹에 속한 고객을 조회할 수 있는 API를 제공합니다.
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 20.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 20.    메가존 시스템            최초 생성
 * </pre>
 */
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customer", description = "고객 관리 API")
public class CustomerController {
    
    private final CustomerService customerService;
    
    @GetMapping("/{id}")
    @Operation(summary = "고객 조회", description = "ID로 고객 정보를 조회합니다.")
    public ApiResponseWellness<CustomerResponse> getCustomer(
            @PathVariable Long id,
            @AuthenticatedUser CurrentUser user) {
        return ApiResponseGenerator.success(customerService.getCustomer(id, user));
    }
    
    @GetMapping("/facility-group/{facilityGroupId}")
    @Operation(summary = "시설 그룹별 고객 목록 조회", description = "시설 그룹 ID로 고객 목록을 조회합니다.")
    public ApiResponseWellness<List<CustomerResponse>> getCustomersByFacilityGroup(
            @PathVariable Long facilityGroupId,
            @AuthenticatedUser CurrentUser user) {
        return ApiResponseGenerator.success(customerService.getCustomersByFacilityGroupId(facilityGroupId, user));
    }
    
    @GetMapping("/my-facility-group")
    @Operation(summary = "내 시설 그룹 고객 목록 조회", description = "현재 로그인한 매니저의 시설 그룹에 속한 고객 목록을 조회합니다.")
    public ApiResponseWellness<List<CustomerResponse>> getMyFacilityGroupCustomers(
            @AuthenticatedUser CurrentUser user) {
        return ApiResponseGenerator.success(customerService.getCustomersByFacilityGroupId(user.getFacilityGroupId(), user));
    }
}

