package com.aiwellness.manager.application.service.customer;

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
     * @return CustomerResponse
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public CustomerResponse getCustomer(Long id) {
        log.info("[Application/Service] CustomerService.getCustomer() - Use Case 시작: id={}", id);
        
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
     * 현재 로그인한 매니저의 시설 그룹에 속한 고객 목록을 조회합니다.
     *
     * @param facilityGroupId 시설 그룹 ID
     * @return 고객 목록
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<CustomerResponse> getCustomersByFacilityGroupId(Long facilityGroupId) {
        log.info("[Application/Service] CustomerService.getCustomersByFacilityGroupId() - Use Case 시작: facilityGroupId={}", 
                facilityGroupId);
        
        List<Customer> customers = customerRepositoryPort.findByFacilityGroupId(facilityGroupId);
        
        log.info("[Application/Service] CustomerService.getCustomersByFacilityGroupId() - Use Case 완료: facilityGroupId={}, count={}", 
                facilityGroupId, customers.size());
        
        return customers.stream()
                .map(CustomerResponse::from)
                .collect(Collectors.toList());
    }
}

