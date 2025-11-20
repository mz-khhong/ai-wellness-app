package com.aiwellness.manager.application.service;

import com.aiwellness.manager.adapter.web.manager.dto.request.ManagerCreateRequest;
import com.aiwellness.manager.adapter.web.manager.dto.response.ManagerResponse;
import com.aiwellness.manager.application.service.manager.ManagerService;
import com.aiwellness.manager.domain.model.manager.Manager;
import com.aiwellness.manager.domain.port.manager.ManagerRepositoryPort;
import com.aiwellness.manager.exception.ManagerBusinessException;
import com.aiwellness.manager.exception.ManagerErrorCode;
import com.aiwellness.manager.fixture.MockData;
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
 * com.aiwellness.manager.application.service
 * <p>
 * ManagerServiceTest
 * <p>
 * ManagerService 단위 테스트
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
class ManagerServiceTest {
    
    @Mock
    private ManagerRepositoryPort managerRepositoryPort;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private ManagerService managerService;
    
    private Manager mockManager;
    
    @BeforeEach
    void setUp() {
        // MockData를 사용하여 테스트용 Manager 생성
        mockManager = MockData.createManager();
        // ID 설정
        mockManager = Manager.builder()
                .id(1L)
                .facilityGroupId(1L)
                .uuid("test-uuid-123")
                .email(mockManager.getEmail())
                .name(mockManager.getName())
                .password(mockManager.getPassword())
                .status(mockManager.getStatus())
                .createdAt(mockManager.getCreatedAt())
                .updatedAt(mockManager.getUpdatedAt())
                .build();
    }
    
    @Test
    void getManager_shouldReturnManager_whenExists() {
        // given
        when(managerRepositoryPort.findById(1L)).thenReturn(Optional.of(mockManager));
        
        // when
        ManagerResponse result = managerService.getManager(1L);
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("manager@wellness.com");
        assertThat(result.getName()).isEqualTo("Manager User");
    }
    
    @Test
    void getManager_shouldThrowException_whenNotFound() {
        // given
        when(managerRepositoryPort.findById(1L)).thenReturn(Optional.empty());
        
        // when & then
        assertThatThrownBy(() -> managerService.getManager(1L))
                .isInstanceOf(ManagerBusinessException.class)
                .satisfies(exception -> {
                    ManagerBusinessException managerException = (ManagerBusinessException) exception;
                    assertThat(managerException.getManagerErrorCode()).isEqualTo(ManagerErrorCode.MANAGER_NOT_FOUND);
                });
    }
    
    @Test
    void createManager_shouldReturnCreatedManager() {
        // given
        ManagerCreateRequest request = new ManagerCreateRequest();
        request.setEmail("newmanager@wellness.com");
        request.setName("New Manager");
        request.setPassword("password123");
        request.setFacilityGroupId(1L);
        
        // 이메일 중복 체크: 존재하지 않음
        when(managerRepositoryPort.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        // 비밀번호 암호화 Mock
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        // 저장 Mock
        when(managerRepositoryPort.save(any(Manager.class))).thenReturn(mockManager);
        
        // when
        ManagerResponse result = managerService.createManager(request);
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("manager@wellness.com");
    }
    
    @Test
    void createManager_shouldThrowException_whenEmailDuplicate() {
        // given
        ManagerCreateRequest request = new ManagerCreateRequest();
        request.setEmail("manager@wellness.com");
        request.setName("Manager User");
        request.setPassword("password123");
        request.setFacilityGroupId(1L);
        
        // 이메일 중복: 이미 존재함
        when(managerRepositoryPort.findByEmail(request.getEmail())).thenReturn(Optional.of(mockManager));
        
        // when & then
        assertThatThrownBy(() -> managerService.createManager(request))
                .isInstanceOf(ManagerBusinessException.class)
                .satisfies(exception -> {
                    ManagerBusinessException managerException = (ManagerBusinessException) exception;
                    assertThat(managerException.getManagerErrorCode()).isEqualTo(ManagerErrorCode.MANAGER_EMAIL_DUPLICATE);
                });
    }
    
    @Test
    void createManager_shouldUseMockDataWithEmail() {
        // given
        String customEmail = "custom@wellness.com";
        Manager customManager = MockData.createManagerWithEmail(customEmail);
        customManager = Manager.builder()
                .id(2L)
                .facilityGroupId(1L)
                .uuid("custom-uuid-456")
                .email(customManager.getEmail())
                .name(customManager.getName())
                .password(customManager.getPassword())
                .status(customManager.getStatus())
                .createdAt(customManager.getCreatedAt())
                .updatedAt(customManager.getUpdatedAt())
                .build();
        
        ManagerCreateRequest request = new ManagerCreateRequest();
        request.setEmail(customEmail);
        request.setName("Custom Manager");
        request.setPassword("password123");
        request.setFacilityGroupId(1L);
        
        when(managerRepositoryPort.findByEmail(customEmail)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(managerRepositoryPort.save(any(Manager.class))).thenReturn(customManager);
        
        // when
        ManagerResponse result = managerService.createManager(request);
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(customEmail);
    }
}

