package com.aiwellness.customer.adapter.persistence.customer;

import com.aiwellness.customer.domain.model.customer.Customer;
import com.aiwellness.customer.domain.port.customer.CustomerRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomerAdapter implements CustomerRepositoryPort {

    private final CustomerMapper customerMapper;

    @Override
    public Customer save(Customer customer) {
        log.info("[Adapter/Persistence] CustomerAdapter.save() - Domain Port 구현 호출: id={}", customer.getId());
        LocalDateTime now = LocalDateTime.now();
        if (customer.getId() == null) {
            customer.setCreatedAt(now);
            customer.setUpdatedAt(now);
            customerMapper.insert(customer);
            log.info("[Adapter/Persistence] CustomerAdapter.save() - INSERT 완료: id={}", customer.getId());
        } else {
            customer.setUpdatedAt(now);
            customerMapper.update(customer);
            log.info("[Adapter/Persistence] CustomerAdapter.save() - UPDATE 완료: id={}", customer.getId());
        }
        return customer;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        log.info("[Adapter/Persistence] CustomerAdapter.findById() - Domain Port 구현 호출: id={}", id);
        Customer customer = customerMapper.findById(id);
        log.info("[Adapter/Persistence] CustomerAdapter.findById() - 조회 완료: id={}, found={}", id, customer != null);
        return Optional.ofNullable(customer);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        log.info("[Adapter/Persistence] CustomerAdapter.findByEmail() - Domain Port 구현 호출: email={}", email);
        Customer customer = customerMapper.findByEmail(email);
        log.info("[Adapter/Persistence] CustomerAdapter.findByEmail() - 조회 완료: email={}, found={}", email, customer != null);
        return Optional.ofNullable(customer);
    }

    @Override
    public void deleteById(Long id) {
        log.info("[Adapter/Persistence] CustomerAdapter.deleteById() - Domain Port 구현 호출: id={}", id);
        customerMapper.deleteById(id);
        log.info("[Adapter/Persistence] CustomerAdapter.deleteById() - DELETE 완료: id={}", id);
    }
}

