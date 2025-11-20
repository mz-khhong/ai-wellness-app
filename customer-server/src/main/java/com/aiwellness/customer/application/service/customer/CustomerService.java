package com.aiwellness.customer.application.service.customer;

import com.aiwellness.customer.adapter.web.customer.dto.request.CustomerCreateRequest;
import com.aiwellness.customer.adapter.web.customer.dto.request.CustomerUpdateRequest;
import com.aiwellness.customer.adapter.web.customer.dto.response.CustomerResponse;
import com.aiwellness.customer.domain.model.customer.Customer;
import com.aiwellness.customer.domain.model.enums.CustomerStatus;
import com.aiwellness.customer.domain.port.customer.CustomerRepositoryPort;
import com.aiwellness.customer.exception.CustomerBusinessException;
import com.aiwellness.customer.exception.CustomerErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED)
public class CustomerService {
    private final CustomerRepositoryPort customerRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public CustomerResponse getCustomer(Long id) {
        log.info("[Application/Service] CustomerService.getCustomer() - Use Case 시작: id={}", id);
        Customer customer = customerRepositoryPort.findById(id)
                .orElseThrow(() -> new CustomerBusinessException(CustomerErrorCode.CUSTOMER_NOT_FOUND));
        log.info("[Application/Service] CustomerService.getCustomer() - Use Case 완료: id={}, email={}", id, customer.getEmail());
        return CustomerResponse.from(customer);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public CustomerResponse createCustomer(CustomerCreateRequest request) {
        log.info("[Application/Service] CustomerService.createCustomer() - Use Case 시작: email={}", request.getEmail());
        customerRepositoryPort.findByEmail(request.getEmail())
                .ifPresent(existing -> {
                    log.warn("[Application/Service] CustomerService.createCustomer() - 이메일 중복: {}", request.getEmail());
                    throw new CustomerBusinessException(CustomerErrorCode.CUSTOMER_EMAIL_DUPLICATE);
                });
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Customer customer = Customer.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(encodedPassword)
                .facilityGroupId(request.getFacilityGroupId())
                .uuid(UUID.randomUUID().toString())
                .status(CustomerStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Customer saved = customerRepositoryPort.save(customer);
        log.info("[Application/Service] CustomerService.createCustomer() - Use Case 완료: id={}, email={}", 
                saved.getId(), saved.getEmail());
        return CustomerResponse.from(saved);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public CustomerResponse updateCustomer(Long id, CustomerUpdateRequest request) {
        log.info("[Application/Service] CustomerService.updateCustomer() - Use Case 시작: id={}", id);
        Customer existing = customerRepositoryPort.findById(id)
                .orElseThrow(() -> new CustomerBusinessException(CustomerErrorCode.CUSTOMER_NOT_FOUND));
        if (request.getEmail() != null) {
            existing.setEmail(request.getEmail());
        }
        if (request.getName() != null) {
            existing.setName(request.getName());
        }
        if (request.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            existing.setPassword(encodedPassword);
        }
        if (request.getFacilityGroupId() != null) {
            existing.setFacilityGroupId(request.getFacilityGroupId());
        }
        Customer updated = customerRepositoryPort.save(existing);
        log.info("[Application/Service] CustomerService.updateCustomer() - Use Case 완료: id={}, email={}", 
                updated.getId(), updated.getEmail());
        return CustomerResponse.from(updated);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteCustomer(Long id) {
        log.info("[Application/Service] CustomerService.deleteCustomer() - Use Case 시작: id={}", id);
        Customer existing = customerRepositoryPort.findById(id)
                .orElseThrow(() -> new CustomerBusinessException(CustomerErrorCode.CUSTOMER_NOT_FOUND));
        customerRepositoryPort.deleteById(id);
        log.info("[Application/Service] CustomerService.deleteCustomer() - Use Case 완료: id={}", id);
    }
}

