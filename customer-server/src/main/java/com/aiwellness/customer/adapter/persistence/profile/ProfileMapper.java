package com.aiwellness.customer.adapter.persistence.profile;

import com.aiwellness.customer.domain.model.profile.Profile;
import org.apache.ibatis.annotations.Mapper;

/**
 * com.aiwellness.customer.adapter.persistence.profile
 * <p>
 * ProfileMapper
 * <p>
 * 프로필 MyBatis Mapper 인터페이스
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
public interface ProfileMapper {
    
    int insert(Profile profile);
    
    int update(Profile profile);
    
    Profile findById(Long id);
    
    Profile findByCustomerId(Long customerId);
    
    int deleteById(Long id);
}

