package com.aiwellness.customer.domain.port.customer;

import com.aiwellness.customer.domain.model.customer.Customer;

import java.util.Optional;

public interface CustomerRepositoryPort {
    Customer save(Customer customer);
    Optional<Customer> findById(Long id);
    Optional<Customer> findByEmail(String email);
    void deleteById(Long id);
}

