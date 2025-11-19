package com.aiwellness.customer.fixture;

import com.aiwellness.customer.domain.model.customer.Customer;
import com.aiwellness.customer.domain.model.enums.CustomerStatus;

import java.time.LocalDateTime;

/**
 * com.aiwellness.customer.fixture
 * <p>
 * MockData
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
public class MockData {
    public static Customer createCustomer() {
        return Customer.builder()
                .email("customer@wellness.com")
                .name("Customer User")
                .password("password123")
                .status(CustomerStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Customer createCustomerWithEmail(String email) {
        return Customer.builder()
                .email(email)
                .name("Customer User")
                .password("password123")
                .status(CustomerStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}

