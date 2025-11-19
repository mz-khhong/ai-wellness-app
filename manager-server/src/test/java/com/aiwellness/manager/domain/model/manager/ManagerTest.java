package com.aiwellness.manager.domain.model.manager;

import com.aiwellness.manager.domain.model.enums.ManagerStatus;
import com.aiwellness.manager.fixture.MockData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * com.aiwellness.manager.domain.model.manager
 * <p>
 * ManagerTest
 * <p>
 * Manager 도메인 모델 단위 테스트
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
@DisplayName("Manager 도메인 모델 테스트")
class ManagerTest {
    
    @Test
    @DisplayName("Builder 패턴으로 Manager 객체를 생성할 수 있다")
    void shouldCreateManagerUsingBuilder() {
        // given & when
        Manager manager = Manager.builder()
                .id(1L)
                .facilityGroupId(1L)
                .uuid("test-uuid-123")
                .email("manager@example.com")
                .name("매니저")
                .password("encodedPassword")
                .status(ManagerStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        // then
        assertThat(manager).isNotNull();
        assertThat(manager.getId()).isEqualTo(1L);
        assertThat(manager.getEmail()).isEqualTo("manager@example.com");
        assertThat(manager.getName()).isEqualTo("매니저");
        assertThat(manager.getStatus()).isEqualTo(ManagerStatus.ACTIVE);
    }
    
    @Test
    @DisplayName("NoArgsConstructor로 Manager 객체를 생성할 수 있다")
    void shouldCreateManagerUsingNoArgsConstructor() {
        // given & when
        Manager manager = new Manager();
        
        // then
        assertThat(manager).isNotNull();
    }
    
    @Test
    @DisplayName("AllArgsConstructor로 Manager 객체를 생성할 수 있다")
    void shouldCreateManagerUsingAllArgsConstructor() {
        // given
        Long id = 1L;
        Long facilityGroupId = 1L;
        String uuid = "test-uuid-123";
        String email = "manager@example.com";
        String name = "매니저";
        String password = "encodedPassword";
        ManagerStatus status = ManagerStatus.ACTIVE;
        LocalDateTime now = LocalDateTime.now();
        
        // when
        Manager manager = new Manager(id, facilityGroupId, uuid, email, name, password, status, now, now);
        
        // then
        assertThat(manager).isNotNull();
        assertThat(manager.getId()).isEqualTo(id);
        assertThat(manager.getEmail()).isEqualTo(email);
        assertThat(manager.getName()).isEqualTo(name);
        assertThat(manager.getStatus()).isEqualTo(status);
    }
    
    @Test
    @DisplayName("MockData를 사용하여 Manager 객체를 생성할 수 있다")
    void shouldCreateManagerUsingMockData() {
        // given & when
        Manager manager = MockData.createManager();
        
        // then
        assertThat(manager).isNotNull();
        assertThat(manager.getEmail()).isEqualTo("manager@wellness.com");
        assertThat(manager.getName()).isEqualTo("Manager User");
        assertThat(manager.getStatus()).isEqualTo(ManagerStatus.ACTIVE);
        assertThat(manager.getCreatedAt()).isNotNull();
        assertThat(manager.getUpdatedAt()).isNotNull();
    }
    
    @Test
    @DisplayName("MockData를 사용하여 커스텀 이메일로 Manager 객체를 생성할 수 있다")
    void shouldCreateManagerUsingMockDataWithCustomEmail() {
        // given
        String customEmail = "custom@example.com";
        
        // when
        Manager manager = MockData.createManagerWithEmail(customEmail);
        
        // then
        assertThat(manager).isNotNull();
        assertThat(manager.getEmail()).isEqualTo(customEmail);
        assertThat(manager.getName()).isEqualTo("Manager User");
        assertThat(manager.getStatus()).isEqualTo(ManagerStatus.ACTIVE);
    }
    
    @Test
    @DisplayName("Setter를 사용하여 Manager 필드를 변경할 수 있다")
    void shouldUpdateManagerFieldsUsingSetter() {
        // given
        Manager manager = MockData.createManager();
        String newName = "새 매니저";
        ManagerStatus newStatus = ManagerStatus.INACTIVE;
        
        // when
        manager.setName(newName);
        manager.setStatus(newStatus);
        
        // then
        assertThat(manager.getName()).isEqualTo(newName);
        assertThat(manager.getStatus()).isEqualTo(newStatus);
    }
    
    @Test
    @DisplayName("Getter를 사용하여 Manager 필드를 조회할 수 있다")
    void shouldGetManagerFieldsUsingGetter() {
        // given
        Manager manager = Manager.builder()
                .id(1L)
                .facilityGroupId(1L)
                .uuid("test-uuid-123")
                .email("manager@example.com")
                .name("매니저")
                .password("encodedPassword")
                .status(ManagerStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        // when & then
        assertThat(manager.getId()).isEqualTo(1L);
        assertThat(manager.getFacilityGroupId()).isEqualTo(1L);
        assertThat(manager.getUuid()).isEqualTo("test-uuid-123");
        assertThat(manager.getEmail()).isEqualTo("manager@example.com");
        assertThat(manager.getName()).isEqualTo("매니저");
        assertThat(manager.getPassword()).isEqualTo("encodedPassword");
        assertThat(manager.getStatus()).isEqualTo(ManagerStatus.ACTIVE);
        assertThat(manager.getCreatedAt()).isNotNull();
        assertThat(manager.getUpdatedAt()).isNotNull();
    }
    
    @Test
    @DisplayName("ManagerStatus enum 값을 사용할 수 있다")
    void shouldUseManagerStatusEnum() {
        // given & when
        ManagerStatus active = ManagerStatus.ACTIVE;
        ManagerStatus inactive = ManagerStatus.INACTIVE;
        ManagerStatus suspended = ManagerStatus.SUSPENDED;
        
        // then
        assertThat(active).isNotNull();
        assertThat(inactive).isNotNull();
        assertThat(suspended).isNotNull();
        assertThat(ManagerStatus.values()).hasSize(3);
    }
    
    @Test
    @DisplayName("Manager 객체의 모든 필드를 설정할 수 있다")
    void shouldSetAllManagerFields() {
        // given
        Long id = 1L;
        Long facilityGroupId = 1L;
        String uuid = "test-uuid-123";
        String email = "manager@example.com";
        String name = "매니저";
        String password = "encodedPassword";
        ManagerStatus status = ManagerStatus.ACTIVE;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        
        // when
        Manager manager = Manager.builder()
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
        assertThat(manager.getId()).isEqualTo(id);
        assertThat(manager.getFacilityGroupId()).isEqualTo(facilityGroupId);
        assertThat(manager.getUuid()).isEqualTo(uuid);
        assertThat(manager.getEmail()).isEqualTo(email);
        assertThat(manager.getName()).isEqualTo(name);
        assertThat(manager.getPassword()).isEqualTo(password);
        assertThat(manager.getStatus()).isEqualTo(status);
        assertThat(manager.getCreatedAt()).isEqualTo(createdAt);
        assertThat(manager.getUpdatedAt()).isEqualTo(updatedAt);
    }
}

