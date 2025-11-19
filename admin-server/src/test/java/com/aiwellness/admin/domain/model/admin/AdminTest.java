package com.aiwellness.admin.domain.model.admin;

import com.aiwellness.admin.domain.model.enums.AdminStatus;
import com.aiwellness.admin.fixture.MockData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * com.aiwellness.admin.domain.model.admin
 * <p>
 * AdminTest
 * <p>
 * Admin 도메인 모델 단위 테스트
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
@DisplayName("Admin 도메인 모델 테스트")
class AdminTest {
    
    @Test
    @DisplayName("Builder 패턴으로 Admin 객체를 생성할 수 있다")
    void shouldCreateAdminUsingBuilder() {
        // given & when
        Admin admin = Admin.builder()
                .id(1L)
                .facilityGroupId(1L)
                .uuid("test-uuid-123")
                .email("admin@example.com")
                .name("관리자")
                .password("encodedPassword")
                .status(AdminStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        // then
        assertThat(admin).isNotNull();
        assertThat(admin.getId()).isEqualTo(1L);
        assertThat(admin.getEmail()).isEqualTo("admin@example.com");
        assertThat(admin.getName()).isEqualTo("관리자");
        assertThat(admin.getStatus()).isEqualTo(AdminStatus.ACTIVE);
    }
    
    @Test
    @DisplayName("NoArgsConstructor로 Admin 객체를 생성할 수 있다")
    void shouldCreateAdminUsingNoArgsConstructor() {
        // given & when
        Admin admin = new Admin();
        
        // then
        assertThat(admin).isNotNull();
    }
    
    @Test
    @DisplayName("AllArgsConstructor로 Admin 객체를 생성할 수 있다")
    void shouldCreateAdminUsingAllArgsConstructor() {
        // given
        Long id = 1L;
        Long facilityGroupId = 1L;
        String uuid = "test-uuid-123";
        String email = "admin@example.com";
        String name = "관리자";
        String password = "encodedPassword";
        AdminStatus status = AdminStatus.ACTIVE;
        LocalDateTime now = LocalDateTime.now();
        
        // when
        Admin admin = new Admin(id, facilityGroupId, uuid, email, name, password, status, now, now);
        
        // then
        assertThat(admin).isNotNull();
        assertThat(admin.getId()).isEqualTo(id);
        assertThat(admin.getEmail()).isEqualTo(email);
        assertThat(admin.getName()).isEqualTo(name);
        assertThat(admin.getStatus()).isEqualTo(status);
    }
    
    @Test
    @DisplayName("MockData를 사용하여 Admin 객체를 생성할 수 있다")
    void shouldCreateAdminUsingMockData() {
        // given & when
        Admin admin = MockData.createAdmin();
        
        // then
        assertThat(admin).isNotNull();
        assertThat(admin.getEmail()).isEqualTo("admin@wellness.com");
        assertThat(admin.getName()).isEqualTo("Admin User");
        assertThat(admin.getStatus()).isEqualTo(AdminStatus.ACTIVE);
        assertThat(admin.getCreatedAt()).isNotNull();
        assertThat(admin.getUpdatedAt()).isNotNull();
    }
    
    @Test
    @DisplayName("MockData를 사용하여 커스텀 이메일로 Admin 객체를 생성할 수 있다")
    void shouldCreateAdminUsingMockDataWithCustomEmail() {
        // given
        String customEmail = "custom@example.com";
        
        // when
        Admin admin = MockData.createAdminWithEmail(customEmail);
        
        // then
        assertThat(admin).isNotNull();
        assertThat(admin.getEmail()).isEqualTo(customEmail);
        assertThat(admin.getName()).isEqualTo("Admin User");
        assertThat(admin.getStatus()).isEqualTo(AdminStatus.ACTIVE);
    }
    
    @Test
    @DisplayName("Setter를 사용하여 Admin 필드를 변경할 수 있다")
    void shouldUpdateAdminFieldsUsingSetter() {
        // given
        Admin admin = MockData.createAdmin();
        String newName = "새 관리자";
        AdminStatus newStatus = AdminStatus.INACTIVE;
        
        // when
        admin.setName(newName);
        admin.setStatus(newStatus);
        
        // then
        assertThat(admin.getName()).isEqualTo(newName);
        assertThat(admin.getStatus()).isEqualTo(newStatus);
    }
    
    @Test
    @DisplayName("Getter를 사용하여 Admin 필드를 조회할 수 있다")
    void shouldGetAdminFieldsUsingGetter() {
        // given
        Admin admin = Admin.builder()
                .id(1L)
                .facilityGroupId(1L)
                .uuid("test-uuid-123")
                .email("admin@example.com")
                .name("관리자")
                .password("encodedPassword")
                .status(AdminStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        // when & then
        assertThat(admin.getId()).isEqualTo(1L);
        assertThat(admin.getFacilityGroupId()).isEqualTo(1L);
        assertThat(admin.getUuid()).isEqualTo("test-uuid-123");
        assertThat(admin.getEmail()).isEqualTo("admin@example.com");
        assertThat(admin.getName()).isEqualTo("관리자");
        assertThat(admin.getPassword()).isEqualTo("encodedPassword");
        assertThat(admin.getStatus()).isEqualTo(AdminStatus.ACTIVE);
        assertThat(admin.getCreatedAt()).isNotNull();
        assertThat(admin.getUpdatedAt()).isNotNull();
    }
    
    @Test
    @DisplayName("AdminStatus enum 값을 사용할 수 있다")
    void shouldUseAdminStatusEnum() {
        // given & when
        AdminStatus active = AdminStatus.ACTIVE;
        AdminStatus inactive = AdminStatus.INACTIVE;
        AdminStatus suspended = AdminStatus.SUSPENDED;
        
        // then
        assertThat(active).isNotNull();
        assertThat(inactive).isNotNull();
        assertThat(suspended).isNotNull();
        assertThat(AdminStatus.values()).hasSize(3);
    }
    
    @Test
    @DisplayName("Admin 객체의 모든 필드를 설정할 수 있다")
    void shouldSetAllAdminFields() {
        // given
        Long id = 1L;
        Long facilityGroupId = 1L;
        String uuid = "test-uuid-123";
        String email = "admin@example.com";
        String name = "관리자";
        String password = "encodedPassword";
        AdminStatus status = AdminStatus.ACTIVE;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        
        // when
        Admin admin = Admin.builder()
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
        assertThat(admin.getId()).isEqualTo(id);
        assertThat(admin.getFacilityGroupId()).isEqualTo(facilityGroupId);
        assertThat(admin.getUuid()).isEqualTo(uuid);
        assertThat(admin.getEmail()).isEqualTo(email);
        assertThat(admin.getName()).isEqualTo(name);
        assertThat(admin.getPassword()).isEqualTo(password);
        assertThat(admin.getStatus()).isEqualTo(status);
        assertThat(admin.getCreatedAt()).isEqualTo(createdAt);
        assertThat(admin.getUpdatedAt()).isEqualTo(updatedAt);
    }
}

