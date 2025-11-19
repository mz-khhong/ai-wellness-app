package com.aiwellness.customer.application.service.profile;

import com.aiwellness.customer.adapter.web.profile.dto.ProfileResponse;
import com.aiwellness.customer.domain.model.profile.Profile;
import com.aiwellness.customer.domain.port.profile.ProfileRepositoryPort;
import com.aiwellness.customer.exception.CustomerBusinessException;
import com.aiwellness.customer.exception.CustomerErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * com.aiwellness.customer.application.service.profile
 * <p>
 * ProfileService
 * <p>
 * 프로필 서비스 (Use Case 구현)
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
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED)
public class ProfileService {
    
    private final ProfileRepositoryPort profileRepositoryPort;
    
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ProfileResponse getProfile(Long id) {
        log.info("[Application/Service] ProfileService.getProfile() - Use Case 시작: id={}", id);
        log.debug("[Application/Service] ProfileService.getProfile() - Domain Port 호출: ProfileRepositoryPort.findById()");
        
        Profile profile = profileRepositoryPort.findById(id)
                .orElseThrow(() -> new CustomerBusinessException(CustomerErrorCode.CUSTOMER_NOT_FOUND));
        
        log.debug("[Application/Service] ProfileService.getProfile() - Domain Port 응답: Profile(id={}, customerId={})", 
                profile.getId(), profile.getCustomerId());
        log.info("[Application/Service] ProfileService.getProfile() - Use Case 완료: id={}", id);
        
        // Domain Model을 Response DTO로 변환
        return ProfileResponse.from(profile);
    }
    
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ProfileResponse getProfileByCustomerId(Long customerId) {
        log.info("[Application/Service] ProfileService.getProfileByCustomerId() - Use Case 시작: customerId={}", customerId);
        log.debug("[Application/Service] ProfileService.getProfileByCustomerId() - Domain Port 호출: ProfileRepositoryPort.findByCustomerId()");
        
        Profile profile = profileRepositoryPort.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerBusinessException(CustomerErrorCode.CUSTOMER_NOT_FOUND));
        
        log.debug("[Application/Service] ProfileService.getProfileByCustomerId() - Domain Port 응답: Profile(id={}, customerId={})", 
                profile.getId(), profile.getCustomerId());
        log.info("[Application/Service] ProfileService.getProfileByCustomerId() - Use Case 완료: customerId={}", customerId);
        
        // Domain Model을 Response DTO로 변환
        return ProfileResponse.from(profile);
    }
}

