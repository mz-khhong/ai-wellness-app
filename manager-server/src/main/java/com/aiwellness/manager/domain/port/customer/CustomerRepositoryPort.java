package com.aiwellness.manager.domain.port.customer;

import com.aiwellness.manager.domain.model.customer.Customer;

import java.util.List;
import java.util.Optional;

/**
 * com.aiwellness.manager.domain.port.customer
 * <p>
 * CustomerRepositoryPort
 * <p>
 * 고객 도메인 저장소 포트 (인터페이스)
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
public interface CustomerRepositoryPort {
    
    /**
     * ID로 고객 조회
     *
     * @param id 고객 ID
     * @return 조회된 고객 도메인 모델 (Optional)
     */
    Optional<Customer> findById(Long id);
    
    /**
     * 시설 그룹 ID로 고객 목록 조회
     *
     * @param facilityGroupId 시설 그룹 ID
     * @return 고객 목록
     */
    List<Customer> findByFacilityGroupId(Long facilityGroupId);
}

