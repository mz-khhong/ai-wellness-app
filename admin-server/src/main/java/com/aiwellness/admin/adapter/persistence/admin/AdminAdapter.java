package com.aiwellness.admin.adapter.persistence.admin;

import com.aiwellness.admin.domain.model.admin.Admin;
import com.aiwellness.admin.domain.port.admin.AdminRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AdminAdapter implements AdminRepositoryPort {

    private final AdminMapper adminMapper;

    @Override
    public Admin save(Admin admin) {
        log.info("[Adapter/Persistence] AdminAdapter.save() - Domain Port 구현 호출: id={}", admin.getId());
        LocalDateTime now = LocalDateTime.now();
        if (admin.getId() == null) {
            admin.setCreatedAt(now);
            admin.setUpdatedAt(now);
            adminMapper.insert(admin);
            log.info("[Adapter/Persistence] AdminAdapter.save() - INSERT 완료: id={}", admin.getId());
        } else {
            admin.setUpdatedAt(now);
            adminMapper.update(admin);
            log.info("[Adapter/Persistence] AdminAdapter.save() - UPDATE 완료: id={}", admin.getId());
        }
        return admin;
    }

    @Override
    public Optional<Admin> findById(Long id) {
        log.info("[Adapter/Persistence] AdminAdapter.findById() - Domain Port 구현 호출: id={}", id);
        Admin admin = adminMapper.findById(id);
        log.info("[Adapter/Persistence] AdminAdapter.findById() - 조회 완료: id={}, found={}", id, admin != null);
        return Optional.ofNullable(admin);
    }

    @Override
    public Optional<Admin> findByEmail(String email) {
        log.info("[Adapter/Persistence] AdminAdapter.findByEmail() - Domain Port 구현 호출: email={}", email);
        Admin admin = adminMapper.findByEmail(email);
        log.info("[Adapter/Persistence] AdminAdapter.findByEmail() - 조회 완료: email={}, found={}", email, admin != null);
        return Optional.ofNullable(admin);
    }

    @Override
    public void deleteById(Long id) {
        log.info("[Adapter/Persistence] AdminAdapter.deleteById() - Domain Port 구현 호출: id={}", id);
        adminMapper.deleteById(id);
        log.info("[Adapter/Persistence] AdminAdapter.deleteById() - DELETE 완료: id={}", id);
    }
}

