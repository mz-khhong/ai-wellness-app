package com.aiwellness.common.persistence;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * com.aiwellness.common.persistence
 * <p>
 * MyBatisRepository
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 14.    메가존 시스템            최초 생성
 * </pre>
 */
public interface MyBatisRepository<T, ID> {
    
    /**
     * ID로 엔티티 조회
     */
    T findById(@Param("id") ID id);
    
    /**
     * 모든 엔티티 조회
     */
    List<T> findAll();
    
    /**
     * 조건으로 엔티티 조회
     */
    List<T> findByCondition(@Param("condition") Map<String, Object> condition);
    
    /**
     * 엔티티 저장
     */
    int save(T entity);
    
    /**
     * 엔티티 업데이트
     */
    int update(T entity);
    
    /**
     * ID로 엔티티 삭제
     */
    int deleteById(@Param("id") ID id);
    
    /**
     * 조건으로 엔티티 삭제
     */
    int deleteByCondition(@Param("condition") Map<String, Object> condition);
    
    /**
     * 조건으로 카운트 조회
     */
    long countByCondition(@Param("condition") Map<String, Object> condition);
    
    /**
     * 페이징 조회
     */
    List<T> findWithPaging(@Param("condition") Map<String, Object> condition, 
                          @Param("offset") int offset, 
                          @Param("limit") int limit);
}

