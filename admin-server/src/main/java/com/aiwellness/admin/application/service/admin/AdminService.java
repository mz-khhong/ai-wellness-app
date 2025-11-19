package com.aiwellness.admin.application.service.admin;

import com.aiwellness.admin.adapter.web.admin.dto.AdminCreateRequest;
import com.aiwellness.admin.adapter.web.admin.dto.AdminResponse;
import com.aiwellness.admin.adapter.web.admin.dto.AdminUpdateRequest;
import com.aiwellness.admin.domain.model.admin.Admin;
import com.aiwellness.admin.domain.model.enums.AdminStatus;
import com.aiwellness.admin.domain.port.admin.AdminRepositoryPort;
import com.aiwellness.admin.exception.AdminBusinessException;
import com.aiwellness.admin.exception.AdminErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * com.aiwellness.admin.application.service
 * <p>
 * AdminService
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
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED)
public class AdminService {
    private final AdminRepositoryPort adminRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public AdminResponse getAdmin(Long id) {
        log.info("[Application/Service] AdminService.getAdmin() - Use Case 시작: id={}", id);
        Admin admin = adminRepositoryPort.findById(id)
                .orElseThrow(() -> new AdminBusinessException(AdminErrorCode.ADMIN_NOT_FOUND));
        log.info("[Application/Service] AdminService.getAdmin() - Use Case 완료: id={}, email={}", id, admin.getEmail());
        return AdminResponse.from(admin);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public AdminResponse createAdmin(AdminCreateRequest request) {
        log.info("[Application/Service] AdminService.createAdmin() - Use Case 시작: email={}", request.getEmail());
        adminRepositoryPort.findByEmail(request.getEmail())
                .ifPresent(existing -> {
                    log.warn("[Application/Service] AdminService.createAdmin() - 이메일 중복: {}", request.getEmail());
                    throw new AdminBusinessException(AdminErrorCode.ADMIN_EMAIL_DUPLICATE);
                });
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Admin admin = Admin.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(encodedPassword)
                .facilityGroupId(request.getFacilityGroupId())
                .uuid(UUID.randomUUID().toString())
                .status(AdminStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Admin saved = adminRepositoryPort.save(admin);
        log.info("[Application/Service] AdminService.createAdmin() - Use Case 완료: id={}, email={}", saved.getId(), saved.getEmail());
        return AdminResponse.from(saved);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public AdminResponse updateAdmin(Long id, AdminUpdateRequest request) {
        log.info("[Application/Service] AdminService.updateAdmin() - Use Case 시작: id={}", id);
        Admin existing = adminRepositoryPort.findById(id)
                .orElseThrow(() -> new AdminBusinessException(AdminErrorCode.ADMIN_NOT_FOUND));
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
        Admin updated = adminRepositoryPort.save(existing);
        log.info("[Application/Service] AdminService.updateAdmin() - Use Case 완료: id={}, email={}", 
                updated.getId(), updated.getEmail());
        return AdminResponse.from(updated);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteAdmin(Long id) {
        log.info("[Application/Service] AdminService.deleteAdmin() - Use Case 시작: id={}", id);
        Admin existing = adminRepositoryPort.findById(id)
                .orElseThrow(() -> new AdminBusinessException(AdminErrorCode.ADMIN_NOT_FOUND));
        adminRepositoryPort.deleteById(id);
        log.info("[Application/Service] AdminService.deleteAdmin() - Use Case 완료: id={}", id);
    }
}

