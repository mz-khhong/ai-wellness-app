package com.aiwellness.customer.domain.model.customer;

import com.aiwellness.customer.domain.model.enums.CustomerStatus;
import com.aiwellness.customer.fixture.MockData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * com.aiwellness.customer.domain.model.customer
 * <p>
 * CustomerTest
 * <p>
 * Customer 도메인 모델 단위 테스트
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
@DisplayName("Customer 도메인 모델 테스트")
class CustomerTest {
    
    @Test
    @DisplayName("Builder 패턴으로 Customer 객체를 생성할 수 있다")
    void shouldCreateCustomerUsingBuilder() {
        // given & when
        Customer customer = Customer.builder()
                .id(1L)
                .facilityGroupId(1L)
                .uuid("test-uuid-123")
                .email("customer@example.com")
                .name("고객")
                .password("encodedPassword")
                .status(CustomerStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        // then
        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isEqualTo(1L);
        assertThat(customer.getEmail()).isEqualTo("customer@example.com");
        assertThat(customer.getName()).isEqualTo("고객");
        assertThat(customer.getStatus()).isEqualTo(CustomerStatus.ACTIVE);
    }
    
    @Test
    @DisplayName("NoArgsConstructor로 Customer 객체를 생성할 수 있다")
    void shouldCreateCustomerUsingNoArgsConstructor() {
        // given & when
        Customer customer = new Customer();
        
        // then
        assertThat(customer).isNotNull();
    }
    
    @Test
    @DisplayName("AllArgsConstructor로 Customer 객체를 생성할 수 있다")
    void shouldCreateCustomerUsingAllArgsConstructor() {
        // given
        Long id = 1L;
        Long facilityGroupId = 1L;
        String uuid = "test-uuid-123";
        String email = "customer@example.com";
        String name = "고객";
        String password = "encodedPassword";
        CustomerStatus status = CustomerStatus.ACTIVE;
        LocalDateTime now = LocalDateTime.now();
        
        // when
        Customer customer = new Customer(id, facilityGroupId, uuid, email, name, password, status, now, now);
        
        // then
        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isEqualTo(id);
        assertThat(customer.getEmail()).isEqualTo(email);
        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.getStatus()).isEqualTo(status);
    }
    
    @Test
    @DisplayName("MockData를 사용하여 Customer 객체를 생성할 수 있다")
    void shouldCreateCustomerUsingMockData() {
        // given & when
        Customer customer = MockData.createCustomer();
        
        // then
        assertThat(customer).isNotNull();
        assertThat(customer.getEmail()).isEqualTo("customer@wellness.com");
        assertThat(customer.getName()).isEqualTo("Customer User");
        assertThat(customer.getStatus()).isEqualTo(CustomerStatus.ACTIVE);
        assertThat(customer.getCreatedAt()).isNotNull();
        assertThat(customer.getUpdatedAt()).isNotNull();
    }
    
    @Test
    @DisplayName("MockData를 사용하여 커스텀 이메일로 Customer 객체를 생성할 수 있다")
    void shouldCreateCustomerUsingMockDataWithCustomEmail() {
        // given
        String customEmail = "custom@example.com";
        
        // when
        Customer customer = MockData.createCustomerWithEmail(customEmail);
        
        // then
        assertThat(customer).isNotNull();
        assertThat(customer.getEmail()).isEqualTo(customEmail);
        assertThat(customer.getName()).isEqualTo("Customer User");
        assertThat(customer.getStatus()).isEqualTo(CustomerStatus.ACTIVE);
    }
    
    @Test
    @DisplayName("Setter를 사용하여 Customer 필드를 변경할 수 있다")
    void shouldUpdateCustomerFieldsUsingSetter() {
        // given
        Customer customer = MockData.createCustomer();
        String newName = "새 고객";
        CustomerStatus newStatus = CustomerStatus.INACTIVE;
        
        // when
        customer.setName(newName);
        customer.setStatus(newStatus);
        
        // then
        assertThat(customer.getName()).isEqualTo(newName);
        assertThat(customer.getStatus()).isEqualTo(newStatus);
    }
    
    @Test
    @DisplayName("Getter를 사용하여 Customer 필드를 조회할 수 있다")
    void shouldGetCustomerFieldsUsingGetter() {
        // given
        Customer customer = Customer.builder()
                .id(1L)
                .facilityGroupId(1L)
                .uuid("test-uuid-123")
                .email("customer@example.com")
                .name("고객")
                .password("encodedPassword")
                .status(CustomerStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        // when & then
        assertThat(customer.getId()).isEqualTo(1L);
        assertThat(customer.getFacilityGroupId()).isEqualTo(1L);
        assertThat(customer.getUuid()).isEqualTo("test-uuid-123");
        assertThat(customer.getEmail()).isEqualTo("customer@example.com");
        assertThat(customer.getName()).isEqualTo("고객");
        assertThat(customer.getPassword()).isEqualTo("encodedPassword");
        assertThat(customer.getStatus()).isEqualTo(CustomerStatus.ACTIVE);
        assertThat(customer.getCreatedAt()).isNotNull();
        assertThat(customer.getUpdatedAt()).isNotNull();
    }
    
    @Test
    @DisplayName("CustomerStatus enum 값을 사용할 수 있다")
    void shouldUseCustomerStatusEnum() {
        // given & when
        CustomerStatus active = CustomerStatus.ACTIVE;
        CustomerStatus inactive = CustomerStatus.INACTIVE;
        CustomerStatus suspended = CustomerStatus.SUSPENDED;
        
        // then
        assertThat(active).isNotNull();
        assertThat(inactive).isNotNull();
        assertThat(suspended).isNotNull();
        assertThat(CustomerStatus.values()).hasSize(3);
    }
    
    @Test
    @DisplayName("Customer 객체의 모든 필드를 설정할 수 있다")
    void shouldSetAllCustomerFields() {
        // given
        Long id = 1L;
        Long facilityGroupId = 1L;
        String uuid = "test-uuid-123";
        String email = "customer@example.com";
        String name = "고객";
        String password = "encodedPassword";
        CustomerStatus status = CustomerStatus.ACTIVE;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        
        // when
        Customer customer = Customer.builder()
                .id(id)
                .facilityGroupId(facilityGroupId)
                .uuid(uuid)
                .email(email)
                .name(name)
                .password(password)
                .status(status)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
        
        // then
        assertThat(customer.getId()).isEqualTo(id);
        assertThat(customer.getFacilityGroupId()).isEqualTo(facilityGroupId);
        assertThat(customer.getUuid()).isEqualTo(uuid);
        assertThat(customer.getEmail()).isEqualTo(email);
        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.getPassword()).isEqualTo(password);
        assertThat(customer.getStatus()).isEqualTo(status);
        assertThat(customer.getCreatedAt()).isEqualTo(createdAt);
        assertThat(customer.getUpdatedAt()).isEqualTo(updatedAt);
    }
}

