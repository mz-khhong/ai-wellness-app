package com.aiwellness.customer.domain.port.profile;

import com.aiwellness.customer.domain.model.profile.Profile;
import java.util.Optional;

/**
 * com.aiwellness.customer.domain.port.profile
 * <p>
 * ProfileRepositoryPort
 * <p>
 * 프로필 도메인 저장소 포트 (인터페이스)
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 18.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 18.    메가존 시스템            최초 생성
 * </pre>
 */
public interface ProfileRepositoryPort {
    
    /**
     * 프로필 저장
     *
     * @param profile 저장할 프로필 도메인 모델
     * @return 저장된 프로필 도메인 모델
     */
    Profile save(Profile profile);
    
    /**
     * ID로 프로필 조회
     *
     * @param id 프로필 ID
     * @return 조회된 프로필 도메인 모델 (Optional)
     */
    Optional<Profile> findById(Long id);
    
    /**
     * 고객 ID로 프로필 조회
     *
     * @param customerId 고객 ID
     * @return 조회된 프로필 도메인 모델 (Optional)
     */
    Optional<Profile> findByCustomerId(Long customerId);
    
    /**
     * ID로 프로필 삭제
     *
     * @param id 프로필 ID
     */
    void deleteById(Long id);
}

