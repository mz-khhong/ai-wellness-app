package com.aiwellness.manager.application.service.manager;

import com.aiwellness.manager.adapter.web.manager.dto.request.ManagerCreateRequest;
import com.aiwellness.manager.adapter.web.manager.dto.request.ManagerUpdateRequest;
import com.aiwellness.manager.adapter.web.manager.dto.response.ManagerResponse;
import com.aiwellness.manager.domain.model.manager.Manager;
import com.aiwellness.manager.domain.model.enums.ManagerStatus;
import com.aiwellness.manager.domain.port.manager.ManagerRepositoryPort;
import com.aiwellness.manager.exception.ManagerBusinessException;
import com.aiwellness.manager.exception.ManagerErrorCode;
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
public class ManagerService {
    private final ManagerRepositoryPort managerRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ManagerResponse getManager(Long id) {
        log.info("[Application/Service] ManagerService.getManager() - Use Case 시작: id={}", id);
        Manager manager = managerRepositoryPort.findById(id)
                .orElseThrow(() -> new ManagerBusinessException(ManagerErrorCode.MANAGER_NOT_FOUND));
        log.info("[Application/Service] ManagerService.getManager() - Use Case 완료: id={}, email={}", id, manager.getEmail());
        return ManagerResponse.from(manager);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ManagerResponse createManager(ManagerCreateRequest request) {
        log.info("[Application/Service] ManagerService.createManager() - Use Case 시작: email={}", request.getEmail());
        managerRepositoryPort.findByEmail(request.getEmail())
                .ifPresent(existing -> {
                    log.warn("[Application/Service] ManagerService.createManager() - 이메일 중복: {}", request.getEmail());
                    throw new ManagerBusinessException(ManagerErrorCode.MANAGER_EMAIL_DUPLICATE);
                });
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Manager manager = Manager.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(encodedPassword)
                .facilityGroupId(request.getFacilityGroupId())
                .uuid(UUID.randomUUID().toString())
                .status(ManagerStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Manager saved = managerRepositoryPort.save(manager);
        log.info("[Application/Service] ManagerService.createManager() - Use Case 완료: id={}, email={}", 
                saved.getId(), saved.getEmail());
        return ManagerResponse.from(saved);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ManagerResponse updateManager(Long id, ManagerUpdateRequest request) {
        log.info("[Application/Service] ManagerService.updateManager() - Use Case 시작: id={}", id);
        Manager existing = managerRepositoryPort.findById(id)
                .orElseThrow(() -> new ManagerBusinessException(ManagerErrorCode.MANAGER_NOT_FOUND));
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
        Manager updated = managerRepositoryPort.save(existing);
        log.info("[Application/Service] ManagerService.updateManager() - Use Case 완료: id={}, email={}", 
                updated.getId(), updated.getEmail());
        return ManagerResponse.from(updated);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteManager(Long id) {
        log.info("[Application/Service] ManagerService.deleteManager() - Use Case 시작: id={}", id);
        Manager existing = managerRepositoryPort.findById(id)
                .orElseThrow(() -> new ManagerBusinessException(ManagerErrorCode.MANAGER_NOT_FOUND));
        managerRepositoryPort.deleteById(id);
        log.info("[Application/Service] ManagerService.deleteManager() - Use Case 완료: id={}", id);
    }
}

