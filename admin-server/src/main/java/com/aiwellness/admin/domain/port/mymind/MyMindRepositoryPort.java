package com.aiwellness.admin.domain.port.mymind;

import com.aiwellness.admin.domain.model.mymind.MyMind;

import java.util.Optional;

/**
 * com.aiwellness.admin.domain.port.mymind
 * <p>
 * MyMindRepositoryPort
 * <p>
 * MY MIND 도메인 저장소 포트 (인터페이스)
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
public interface MyMindRepositoryPort {
    
    /**
     * MY MIND 저장
     *
     * @param myMind 저장할 MY MIND 도메인 모델
     * @return 저장된 MY MIND 도메인 모델
     */
    MyMind save(MyMind myMind);
    
    /**
     * ID로 MY MIND 조회
     *
     * @param id MY MIND ID
     * @return 조회된 MY MIND 도메인 모델 (Optional)
     */
    Optional<MyMind> findById(Long id);
    
    /**
     * 고객 ID로 MY MIND 조회
     *
     * @param customerId 고객 ID
     * @return 조회된 MY MIND 도메인 모델 (Optional)
     */
    Optional<MyMind> findByCustomerId(Long customerId);
    
    /**
     * ID로 MY MIND 삭제
     *
     * @param id MY MIND ID
     */
    void deleteById(Long id);
}

