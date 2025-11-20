package com.aiwellness.manager.adapter.persistence.customer;

import com.aiwellness.manager.domain.model.customer.Customer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * com.aiwellness.manager.adapter.persistence.customer
 * <p>
 * CustomerMapper
 * <p>
 * MyBatis Mapper 인터페이스
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 20.
 */
@Mapper
public interface CustomerMapper {
    
    Customer findById(Long id);
    
    List<Customer> findByFacilityGroupId(Long facilityGroupId);
}

