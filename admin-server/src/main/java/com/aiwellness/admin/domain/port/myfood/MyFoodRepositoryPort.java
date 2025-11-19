package com.aiwellness.admin.domain.port.myfood;

import com.aiwellness.admin.domain.model.myfood.MyFood;

import java.util.Optional;

/**
 * com.aiwellness.admin.domain.port.myfood
 * <p>
 * MyFoodRepositoryPort
 * <p>
 * MY FOOD 도메인 저장소 포트 (인터페이스)
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
public interface MyFoodRepositoryPort {
    
    /**
     * MY FOOD 저장
     *
     * @param myFood 저장할 MY FOOD 도메인 모델
     * @return 저장된 MY FOOD 도메인 모델
     */
    MyFood save(MyFood myFood);
    
    /**
     * ID로 MY FOOD 조회
     *
     * @param id MY FOOD ID
     * @return 조회된 MY FOOD 도메인 모델 (Optional)
     */
    Optional<MyFood> findById(Long id);
    
    /**
     * 고객 ID로 MY FOOD 조회
     *
     * @param customerId 고객 ID
     * @return 조회된 MY FOOD 도메인 모델 (Optional)
     */
    Optional<MyFood> findByCustomerId(Long customerId);
    
    /**
     * ID로 MY FOOD 삭제
     *
     * @param id MY FOOD ID
     */
    void deleteById(Long id);
}

