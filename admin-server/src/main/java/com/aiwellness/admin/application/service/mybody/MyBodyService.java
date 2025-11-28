package com.aiwellness.admin.application.service.mybody;

import com.aiwellness.admin.adapter.web.mybody.dto.response.MyBodyResponse;
import com.aiwellness.admin.domain.model.mybody.MyBody;
import com.aiwellness.admin.domain.port.mybody.MyBodyRepositoryPort;
import com.aiwellness.admin.exception.AdminBusinessException;
import com.aiwellness.admin.domain.code.admin.AdminCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * com.aiwellness.admin.application.service.mybody
 * <p>
 * MyBodyService
 * <p>
 * MY BODY 서비스 (Use Case 구현)
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
public class MyBodyService {
    
    private final MyBodyRepositoryPort myBodyRepositoryPort;
    
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public MyBodyResponse getMyBody(Long id) {
        log.info("[Application/Service] MyBodyService.getMyBody() - Use Case 시작: id={}", id);
        log.debug("[Application/Service] MyBodyService.getMyBody() - Domain Port 호출: MyBodyRepositoryPort.findById()");
        
        MyBody myBody = myBodyRepositoryPort.findById(id)
                .orElseThrow(() -> new AdminBusinessException(AdminCode.ADMIN_NOT_FOUND));
        
        log.debug("[Application/Service] MyBodyService.getMyBody() - Domain Port 응답: MyBody(id={}, customerId={})", 
                myBody.getId(), myBody.getCustomerId());
        log.info("[Application/Service] MyBodyService.getMyBody() - Use Case 완료: id={}", id);
        
        // Domain Model을 Response DTO로 변환
        return MyBodyResponse.from(myBody);
    }
    
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public MyBodyResponse getMyBodyByCustomerId(Long customerId) {
        log.info("[Application/Service] MyBodyService.getMyBodyByCustomerId() - Use Case 시작: customerId={}", customerId);
        log.debug("[Application/Service] MyBodyService.getMyBodyByCustomerId() - Domain Port 호출: MyBodyRepositoryPort.findByCustomerId()");
        
        MyBody myBody = myBodyRepositoryPort.findByCustomerId(customerId)
                .orElseThrow(() -> new AdminBusinessException(AdminCode.ADMIN_NOT_FOUND));
        
        log.debug("[Application/Service] MyBodyService.getMyBodyByCustomerId() - Domain Port 응답: MyBody(id={}, customerId={})", 
                myBody.getId(), myBody.getCustomerId());
        log.info("[Application/Service] MyBodyService.getMyBodyByCustomerId() - Use Case 완료: customerId={}", customerId);
        
        // Domain Model을 Response DTO로 변환
        return MyBodyResponse.from(myBody);
    }
}

