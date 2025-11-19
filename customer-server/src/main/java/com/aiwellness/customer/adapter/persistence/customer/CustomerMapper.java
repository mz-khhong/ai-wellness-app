package com.aiwellness.customer.adapter.persistence.customer;

import com.aiwellness.customer.domain.model.customer.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {
    int insert(Customer customer);
    int update(Customer customer);
    Customer findById(Long id);
    Customer findByEmail(String email);
    int deleteById(Long id);
}

