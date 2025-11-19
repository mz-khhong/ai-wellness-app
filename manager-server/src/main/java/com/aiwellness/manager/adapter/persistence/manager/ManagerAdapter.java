package com.aiwellness.manager.adapter.persistence.manager;

import com.aiwellness.manager.domain.model.manager.Manager;
import com.aiwellness.manager.domain.port.manager.ManagerRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ManagerAdapter implements ManagerRepositoryPort {

    private final ManagerMapper managerMapper;

    @Override
    public Manager save(Manager manager) {
        log.info("[Adapter/Persistence] ManagerAdapter.save() - Domain Port 구현 호출: id={}", manager.getId());
        LocalDateTime now = LocalDateTime.now();
        if (manager.getId() == null) {
            manager = Manager.builder()
                    .email(manager.getEmail())
                    .name(manager.getName())
                    .password(manager.getPassword())
                    .status(manager.getStatus())
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            managerMapper.save(manager);
            log.info("[Adapter/Persistence] ManagerAdapter.save() - INSERT 완료: id={}", manager.getId());
        } else {
            manager = Manager.builder()
                    .id(manager.getId())
                    .email(manager.getEmail())
                    .name(manager.getName())
                    .password(manager.getPassword())
                    .status(manager.getStatus())
                    .createdAt(manager.getCreatedAt())
                    .updatedAt(now)
                    .build();
            managerMapper.save(manager);
            log.info("[Adapter/Persistence] ManagerAdapter.save() - UPDATE 완료: id={}", manager.getId());
        }
        return manager;
    }

    @Override
    public Optional<Manager> findById(Long id) {
        log.info("[Adapter/Persistence] ManagerAdapter.findById() - Domain Port 구현 호출: id={}", id);
        Manager manager = managerMapper.findById(id);
        log.info("[Adapter/Persistence] ManagerAdapter.findById() - 조회 완료: id={}, found={}", id, manager != null);
        return Optional.ofNullable(manager);
    }

    @Override
    public Optional<Manager> findByEmail(String email) {
        log.info("[Adapter/Persistence] ManagerAdapter.findByEmail() - Domain Port 구현 호출: email={}", email);
        Manager manager = managerMapper.findByEmail(email);
        log.info("[Adapter/Persistence] ManagerAdapter.findByEmail() - 조회 완료: email={}, found={}", email, manager != null);
        return Optional.ofNullable(manager);
    }

    @Override
    public void deleteById(Long id) {
        log.info("[Adapter/Persistence] ManagerAdapter.deleteById() - Domain Port 구현 호출: id={}", id);
        managerMapper.deleteById(id);
        log.info("[Adapter/Persistence] ManagerAdapter.deleteById() - DELETE 완료: id={}", id);
    }
}

