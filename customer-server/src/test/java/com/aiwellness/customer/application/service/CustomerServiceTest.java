package com.aiwellness.customer.application.service;

import com.aiwellness.customer.adapter.web.customer.dto.request.CustomerCreateRequest;
import com.aiwellness.customer.adapter.web.customer.dto.response.CustomerResponse;
import com.aiwellness.customer.application.service.customer.CustomerService;
import com.aiwellness.customer.domain.model.customer.Customer;
import com.aiwellness.customer.domain.port.customer.CustomerRepositoryPort;
import com.aiwellness.customer.exception.CustomerBusinessException;
import com.aiwellness.customer.exception.CustomerErrorCode;
import com.aiwellness.customer.fixture.MockData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * com.aiwellness.customer.application.service
 * <p>
 * CustomerServiceTest
 * <p>
 * CustomerService 단위 테스트
 * MockData를 사용한 테스트 예시
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
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    
    @Mock
    private CustomerRepositoryPort customerRepositoryPort;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private CustomerService customerService;
    
    private Customer mockCustomer;
    
    @BeforeEach
    void setUp() {
        // MockData를 사용하여 테스트용 Customer 생성
        mockCustomer = MockData.createCustomer();
        // ID 설정
        mockCustomer = Customer.builder()
                .id(1L)
                .facilityGroupId(1L)
                .uuid("test-uuid-123")
                .email(mockCustomer.getEmail())
                .name(mockCustomer.getName())
                .password(mockCustomer.getPassword())
                .status(mockCustomer.getStatus())
                .createdAt(mockCustomer.getCreatedAt())
                .updatedAt(mockCustomer.getUpdatedAt())
                .build();
    }
    
    @Test
    void getCustomer_shouldReturnCustomer_whenExists() {
        // given
        when(customerRepositoryPort.findById(1L)).thenReturn(Optional.of(mockCustomer));
        
        // when
        CustomerResponse result = customerService.getCustomer(1L);
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("customer@wellness.com");
        assertThat(result.getName()).isEqualTo("Customer User");
    }
    
    @Test
    void getCustomer_shouldThrowException_whenNotFound() {
        // given
        when(customerRepositoryPort.findById(1L)).thenReturn(Optional.empty());
        
        // when & then
        assertThatThrownBy(() -> customerService.getCustomer(1L))
                .isInstanceOf(CustomerBusinessException.class)
                .satisfies(exception -> {
                    CustomerBusinessException customerException = (CustomerBusinessException) exception;
                    assertThat(customerException.getCustomerErrorCode()).isEqualTo(CustomerErrorCode.CUSTOMER_NOT_FOUND);
                });
    }
    
    @Test
    void createCustomer_shouldReturnCreatedCustomer() {
        // given
        CustomerCreateRequest request = new CustomerCreateRequest();
        request.setEmail("newcustomer@wellness.com");
        request.setName("New Customer");
        request.setPassword("password123");
        request.setFacilityGroupId(1L);
        
        // 이메일 중복 체크: 존재하지 않음
        when(customerRepositoryPort.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        // 비밀번호 암호화 Mock
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        // 저장 Mock
        when(customerRepositoryPort.save(any(Customer.class))).thenReturn(mockCustomer);
        
        // when
        CustomerResponse result = customerService.createCustomer(request);
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("customer@wellness.com");
    }
    
    @Test
    void createCustomer_shouldThrowException_whenEmailDuplicate() {
        // given
        CustomerCreateRequest request = new CustomerCreateRequest();
        request.setEmail("customer@wellness.com");
        request.setName("Customer User");
        request.setPassword("password123");
        request.setFacilityGroupId(1L);
        
        // 이메일 중복: 이미 존재함
        when(customerRepositoryPort.findByEmail(request.getEmail())).thenReturn(Optional.of(mockCustomer));
        
        // when & then
        assertThatThrownBy(() -> customerService.createCustomer(request))
                .isInstanceOf(CustomerBusinessException.class)
                .satisfies(exception -> {
                    CustomerBusinessException customerException = (CustomerBusinessException) exception;
                    assertThat(customerException.getCustomerErrorCode()).isEqualTo(CustomerErrorCode.CUSTOMER_EMAIL_DUPLICATE);
                });
    }
    
    @Test
    void createCustomer_shouldUseMockDataWithEmail() {
        // given
        String customEmail = "custom@wellness.com";
        Customer customCustomer = MockData.createCustomerWithEmail(customEmail);
        customCustomer = Customer.builder()
                .id(2L)
                .facilityGroupId(1L)
                .uuid("custom-uuid-456")
                .email(customCustomer.getEmail())
                .name(customCustomer.getName())
                .password(customCustomer.getPassword())
                .status(customCustomer.getStatus())
                .createdAt(customCustomer.getCreatedAt())
                .updatedAt(customCustomer.getUpdatedAt())
                .build();
        
        CustomerCreateRequest request = new CustomerCreateRequest();
        request.setEmail(customEmail);
        request.setName("Custom Customer");
        request.setPassword("password123");
        request.setFacilityGroupId(1L);
        
        when(customerRepositoryPort.findByEmail(customEmail)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(customerRepositoryPort.save(any(Customer.class))).thenReturn(customCustomer);
        
        // when
        CustomerResponse result = customerService.createCustomer(request);
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(customEmail);
    }
}

