package com.aiwellness.manager.domain.port.manager;

import com.aiwellness.manager.domain.model.manager.Manager;

import java.util.Optional;

public interface ManagerRepositoryPort {
    Manager save(Manager manager);
    Optional<Manager> findById(Long id);
    Optional<Manager> findByEmail(String email);
    void deleteById(Long id);
}

