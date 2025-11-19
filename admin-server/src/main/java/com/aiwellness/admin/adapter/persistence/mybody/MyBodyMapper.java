package com.aiwellness.admin.adapter.persistence.mybody;

import com.aiwellness.admin.domain.model.mybody.MyBody;
import org.apache.ibatis.annotations.Mapper;

/**
 * com.aiwellness.admin.adapter.persistence.mybody
 * <p>
 * MyBodyMapper
 * <p>
 * MY BODY MyBatis Mapper 인터페이스
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
@Mapper
public interface MyBodyMapper {
    
    int insert(MyBody myBody);
    
    int update(MyBody myBody);
    
    MyBody findById(Long id);
    
    MyBody findByCustomerId(Long customerId);
    
    int deleteById(Long id);
}

