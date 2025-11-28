package com.aiwellness.manager.application.service.customer;

import com.aiwellness.common.security.CurrentUser;
import com.aiwellness.manager.adapter.web.customer.dto.response.CustomerResponse;
import com.aiwellness.manager.domain.model.customer.Customer;
import com.aiwellness.manager.domain.port.customer.CustomerRepositoryPort;
import com.aiwellness.manager.exception.ManagerBusinessException;
import com.aiwellness.manager.exception.ManagerErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * com.aiwellness.manager.application.service.customer
 * <p>
 * CustomerService
 * <p>
 * 고객 서비스 (Use Case 구현)
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
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED)
public class CustomerService {
    
    private final CustomerRepositoryPort customerRepositoryPort;
    
    /**
     * ID로 고객 조회
     *
     * @param id 고객 ID
     * @param user 현재 로그인한 사용자
     * @return CustomerResponse
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public CustomerResponse getCustomer(Long id, CurrentUser user) {
        log.info("[Application/Service] CustomerService.getCustomer() - Use Case 시작: id={}, userId={}", 
                id, user.getUserId());
        
        Customer customer = customerRepositoryPort.findById(id)
                .orElseThrow(() -> {
                    log.warn("[Application/Service] CustomerService.getCustomer() - 고객을 찾을 수 없음: id={}", id);
                    return new ManagerBusinessException(ManagerErrorCode.CUSTOMER_NOT_FOUND);
                });
        
        log.info("[Application/Service] CustomerService.getCustomer() - Use Case 완료: id={}, email={}", 
                customer.getId(), customer.getEmail());
        
        return CustomerResponse.from(customer);
    }
    
    /**
     * 시설 그룹 ID로 고객 목록 조회
     * <p>
     * 현재 로그인한 매니저의 시설 그룹과 요청한 시설 그룹이 일치하는지 확인합니다.
     *
     * @param facilityGroupId 시설 그룹 ID
     * @param user 현재 로그인한 사용자
     * @return 고객 목록
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<CustomerResponse> getCustomersByFacilityGroupId(Long facilityGroupId, CurrentUser user) {
        log.info("[Application/Service] CustomerService.getCustomersByFacilityGroupId() - Use Case 시작: facilityGroupId={}, userId={}", 
                facilityGroupId, user.getUserId());
        
        // 현재 로그인한 매니저의 시설 그룹과 요청한 시설 그룹이 일치하는지 확인
        if (!user.getFacilityGroupId().equals(facilityGroupId)) {
            log.warn("[Application/Service] CustomerService.getCustomersByFacilityGroupId() - 시설 그룹 불일치: userFacilityGroupId={}, requestedFacilityGroupId={}", 
                    user.getFacilityGroupId(), facilityGroupId);
            throw new ManagerBusinessException(ManagerErrorCode.CUSTOMER_FACILITY_GROUP_MISMATCH);
        }
        
        List<Customer> customers = customerRepositoryPort.findByFacilityGroupId(facilityGroupId);
        
        log.info("[Application/Service] CustomerService.getCustomersByFacilityGroupId() - Use Case 완료: facilityGroupId={}, count={}", 
                facilityGroupId, customers.size());
        
        return customers.stream()
                .map(CustomerResponse::from)
                .collect(Collectors.toList());
    }
}

