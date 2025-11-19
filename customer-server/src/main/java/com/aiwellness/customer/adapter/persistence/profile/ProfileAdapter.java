package com.aiwellness.customer.adapter.persistence.profile;

import com.aiwellness.customer.domain.model.profile.Profile;
import com.aiwellness.customer.domain.port.profile.ProfileRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * com.aiwellness.customer.adapter.persistence.profile
 * <p>
 * ProfileAdapter
 * <p>
 * 프로필 도메인 저장소 어댑터 (MyBatis 구현)
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
@Repository
@RequiredArgsConstructor
public class ProfileAdapter implements ProfileRepositoryPort {
    
    private final ProfileMapper profileMapper;
    
    @Override
    public Profile save(Profile profile) {
        LocalDateTime now = LocalDateTime.now();
        if (profile.getId() == null) {
            // 새 엔티티: createdAt, updatedAt 설정 후 INSERT
            profile.setCreatedAt(now);
            profile.setUpdatedAt(now);
            profileMapper.insert(profile);
        } else {
            // 기존 엔티티: updatedAt만 갱신 후 UPDATE
            profile.setUpdatedAt(now);
            profileMapper.update(profile);
        }
        return profile;
    }
    
    @Override
    public Optional<Profile> findById(Long id) {
        Profile profile = profileMapper.findById(id);
        return Optional.ofNullable(profile);
    }
    
    @Override
    public Optional<Profile> findByCustomerId(Long customerId) {
        Profile profile = profileMapper.findByCustomerId(customerId);
        return Optional.ofNullable(profile);
    }
    
    @Override
    public void deleteById(Long id) {
        profileMapper.deleteById(id);
    }
}

