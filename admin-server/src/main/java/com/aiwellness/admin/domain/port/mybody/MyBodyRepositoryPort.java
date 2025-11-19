package com.aiwellness.admin.domain.port.mybody;

import com.aiwellness.admin.domain.model.mybody.MyBody;

import java.util.Optional;

/**
 * com.aiwellness.admin.domain.port.mybody
 * <p>
 * MyBodyRepositoryPort
 * <p>
 * MY BODY 도메인 저장소 포트 (인터페이스)
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
public interface MyBodyRepositoryPort {
    
    /**
     * MY BODY 저장
     *
     * @param myBody 저장할 MY BODY 도메인 모델
     * @return 저장된 MY BODY 도메인 모델
     */
    MyBody save(MyBody myBody);
    
    /**
     * ID로 MY BODY 조회
     *
     * @param id MY BODY ID
     * @return 조회된 MY BODY 도메인 모델 (Optional)
     */
    Optional<MyBody> findById(Long id);
    
    /**
     * 고객 ID로 MY BODY 조회
     *
     * @param customerId 고객 ID
     * @return 조회된 MY BODY 도메인 모델 (Optional)
     */
    Optional<MyBody> findByCustomerId(Long customerId);
    
    /**
     * ID로 MY BODY 삭제
     *
     * @param id MY BODY ID
     */
    void deleteById(Long id);
}

