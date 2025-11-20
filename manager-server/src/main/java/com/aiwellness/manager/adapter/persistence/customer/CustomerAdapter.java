package com.aiwellness.manager.adapter.persistence.customer;

import com.aiwellness.manager.domain.model.customer.Customer;
import com.aiwellness.manager.domain.port.customer.CustomerRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * com.aiwellness.manager.adapter.persistence.customer
 * <p>
 * CustomerAdapter
 * <p>
 * 고객 도메인 저장소 어댑터 (Persistence)
 * <p>
 * CustomerRepositoryPort를 구현하여 데이터베이스 접근을 담당합니다.
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
@Repository
@RequiredArgsConstructor
public class CustomerAdapter implements CustomerRepositoryPort {
    
    private final CustomerMapper customerMapper;
    
    @Override
    public Optional<Customer> findById(Long id) {
        log.info("[Adapter/Persistence] CustomerAdapter.findById() - Domain Port 구현 호출: id={}", id);
        Customer customer = customerMapper.findById(id);
        log.info("[Adapter/Persistence] CustomerAdapter.findById() - 조회 완료: id={}, found={}", id, customer != null);
        return Optional.ofNullable(customer);
    }
    
    @Override
    public List<Customer> findByFacilityGroupId(Long facilityGroupId) {
        log.info("[Adapter/Persistence] CustomerAdapter.findByFacilityGroupId() - Domain Port 구현 호출: facilityGroupId={}", facilityGroupId);
        List<Customer> customers = customerMapper.findByFacilityGroupId(facilityGroupId);
        log.info("[Adapter/Persistence] CustomerAdapter.findByFacilityGroupId() - 조회 완료: facilityGroupId={}, count={}", 
                facilityGroupId, customers.size());
        return customers;
    }
}

