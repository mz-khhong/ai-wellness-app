package com.aiwellness.admin.application.service;

import com.aiwellness.admin.adapter.web.admin.dto.request.AdminCreateRequest;
import com.aiwellness.admin.adapter.web.admin.dto.response.AdminResponse;
import com.aiwellness.admin.application.service.admin.AdminService;
import com.aiwellness.admin.domain.model.admin.Admin;
import com.aiwellness.admin.domain.port.admin.AdminRepositoryPort;
import com.aiwellness.admin.exception.AdminBusinessException;
import com.aiwellness.admin.exception.AdminErrorCode;
import com.aiwellness.admin.fixture.MockData;
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
 * com.aiwellness.admin.application.service
 * <p>
 * AdminServiceTest
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
@ExtendWith(MockitoExtension.class)
class AdminServiceTest {
    @Mock
    private AdminRepositoryPort adminRepositoryPort;
    
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminService adminService;

    private Admin mockAdmin;

    @BeforeEach
    void setUp() {
        // MockData를 사용하여 테스트용 Admin 생성
        mockAdmin = MockData.createAdmin();
        // ID 및 필수 필드 설정
        mockAdmin = Admin.builder()
                .id(1L)
                .facilityGroupId(1L)
                .uuid("test-uuid-123")
                .email(mockAdmin.getEmail())
                .name(mockAdmin.getName())
                .password(mockAdmin.getPassword())
                .status(mockAdmin.getStatus())
                .createdAt(mockAdmin.getCreatedAt())
                .updatedAt(mockAdmin.getUpdatedAt())
                .build();
    }

    @Test
    void getAdmin_shouldReturnAdmin_whenExists() {
        // given
        when(adminRepositoryPort.findById(1L)).thenReturn(Optional.of(mockAdmin));

        // when
        AdminResponse result = adminService.getAdmin(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("admin@wellness.com");
    }

    @Test
    void getAdmin_shouldThrowException_whenNotFound() {
        // given
        when(adminRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> adminService.getAdmin(1L))
                .isInstanceOf(AdminBusinessException.class)
                .satisfies(exception -> {
                    AdminBusinessException adminException = (AdminBusinessException) exception;
                    assertThat(adminException.getAdminErrorCode()).isEqualTo(AdminErrorCode.ADMIN_NOT_FOUND);
                });
    }

    @Test
    void createAdmin_shouldReturnCreatedAdmin() {
        // given
        AdminCreateRequest request = new AdminCreateRequest();
        request.setEmail("admin@wellness.com");
        request.setName("Admin User");
        request.setPassword("password123");
        request.setFacilityGroupId(1L);
        
        // 이메일 중복 체크: 존재하지 않음
        when(adminRepositoryPort.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        // 비밀번호 암호화 Mock
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        // 저장 Mock
        when(adminRepositoryPort.save(any(Admin.class))).thenReturn(mockAdmin);

        // when
        AdminResponse result = adminService.createAdmin(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("admin@wellness.com");
    }
    
    @Test
    void createAdmin_shouldThrowException_whenEmailDuplicate() {
        // given
        AdminCreateRequest request = new AdminCreateRequest();
        request.setEmail("admin@wellness.com");
        request.setName("Admin User");
        request.setPassword("password123");
        request.setFacilityGroupId(1L);
        
        // 이메일 중복: 이미 존재함
        when(adminRepositoryPort.findByEmail(request.getEmail())).thenReturn(Optional.of(mockAdmin));

        // when & then
        assertThatThrownBy(() -> adminService.createAdmin(request))
                .isInstanceOf(AdminBusinessException.class)
                .satisfies(exception -> {
                    AdminBusinessException adminException = (AdminBusinessException) exception;
                    assertThat(adminException.getAdminErrorCode()).isEqualTo(AdminErrorCode.ADMIN_EMAIL_DUPLICATE);
                });
    }
    
    @Test
    void createAdmin_shouldUseMockDataWithEmail() {
        // given
        String customEmail = "custom@wellness.com";
        Admin customAdmin = MockData.createAdminWithEmail(customEmail);
        customAdmin = Admin.builder()
                .id(2L)
                .facilityGroupId(1L)
                .uuid("custom-uuid-456")
                .email(customAdmin.getEmail())
                .name(customAdmin.getName())
                .password(customAdmin.getPassword())
                .status(customAdmin.getStatus())
                .createdAt(customAdmin.getCreatedAt())
                .updatedAt(customAdmin.getUpdatedAt())
                .build();
        
        AdminCreateRequest request = new AdminCreateRequest();
        request.setEmail(customEmail);
        request.setName("Custom Admin");
        request.setPassword("password123");
        request.setFacilityGroupId(1L);
        
        when(adminRepositoryPort.findByEmail(customEmail)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(adminRepositoryPort.save(any(Admin.class))).thenReturn(customAdmin);
        
        // when
        AdminResponse result = adminService.createAdmin(request);
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(customEmail);
    }
}

