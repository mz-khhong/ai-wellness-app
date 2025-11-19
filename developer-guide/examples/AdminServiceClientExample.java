package com.aiwellness.example;

/**
 * Feign Client 사용 예시
 * 
 * 이 파일은 예시용이며, 실제 구현 시 각 서버의 적절한 위치에 생성해야 합니다.
 */

// ============================================
// 1. Domain Port 정의
// ============================================
// 위치: {server}/domain/port/external/AdminServicePort.java

/*
package com.aiwellness.{server}.domain.port.external;

import com.aiwellness.admin.adapter.web.admin.dto.AdminResponse;

public interface AdminServicePort {
    AdminResponse getAdmin(Long id);
    AdminResponse createAdmin(AdminCreateRequest request);
}
*/

// ============================================
// 2. Feign Client 구현
// ============================================
// 위치: {server}/adapter/infrastructure/client/admin/AdminServiceClient.java

/*
package com.aiwellness.{server}.adapter.infrastructure.client.admin;

import com.aiwellness.admin.adapter.web.admin.dto.AdminCreateRequest;
import com.aiwellness.admin.adapter.web.admin.dto.AdminResponse;
import com.aiwellness.{server}.domain.port.external.AdminServicePort;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "admin-service",
    url = "${services.admin.url}",
    path = "/api"
)
public interface AdminServiceClient extends AdminServicePort {
    
    @GetMapping("/admins/{id}")
    @Override
    AdminResponse getAdmin(@PathVariable Long id);
    
    @PostMapping("/admins")
    @Override
    AdminResponse createAdmin(@RequestBody AdminCreateRequest request);
}
*/

// ============================================
// 3. Adapter 구현 (외부 호출 로그 포함)
// ============================================
// 위치: {server}/adapter/infrastructure/client/admin/AdminServiceAdapter.java

/*
package com.aiwellness.{server}.adapter.infrastructure.client.admin;

import com.aiwellness.admin.adapter.web.admin.dto.AdminCreateRequest;
import com.aiwellness.admin.adapter.web.admin.dto.AdminResponse;
import com.aiwellness.{server}.domain.port.external.AdminServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AdminServiceAdapter implements AdminServicePort {
    
    private final AdminServiceClient adminServiceClient;
    
    @Override
    public AdminResponse getAdmin(Long id) {
        log.info("[Adapter/Infrastructure/Client] AdminServiceAdapter.getAdmin() - 외부 서비스 호출 시작: service=admin-service, method=getAdmin, id={}", id);
        try {
            AdminResponse response = adminServiceClient.getAdmin(id);
            log.info("[Adapter/Infrastructure/Client] AdminServiceAdapter.getAdmin() - 외부 서비스 호출 완료: service=admin-service, method=getAdmin, id={}, success=true", id);
            return response;
        } catch (Exception e) {
            log.error("[Adapter/Infrastructure/Client] AdminServiceAdapter.getAdmin() - 외부 서비스 호출 실패: service=admin-service, method=getAdmin, id={}, error={}", id, e.getMessage());
            throw e;
        }
    }
    
    @Override
    public AdminResponse createAdmin(AdminCreateRequest request) {
        log.info("[Adapter/Infrastructure/Client] AdminServiceAdapter.createAdmin() - 외부 서비스 호출 시작: service=admin-service, method=createAdmin, email={}", request.getEmail());
        try {
            AdminResponse response = adminServiceClient.createAdmin(request);
            log.info("[Adapter/Infrastructure/Client] AdminServiceAdapter.createAdmin() - 외부 서비스 호출 완료: service=admin-service, method=createAdmin, id={}, email={}, success=true", 
                    response.getId(), request.getEmail());
            return response;
        } catch (Exception e) {
            log.error("[Adapter/Infrastructure/Client] AdminServiceAdapter.createAdmin() - 외부 서비스 호출 실패: service=admin-service, method=createAdmin, email={}, error={}", 
                    request.getEmail(), e.getMessage());
            throw e;
        }
    }
}
*/

// ============================================
// 4. Service에서 사용
// ============================================
// 위치: {server}/application/service/{domain}/{Domain}Service.java

/*
package com.aiwellness.{server}.application.service.order;

import com.aiwellness.{server}.domain.port.external.AdminServicePort;
import com.aiwellness.admin.adapter.web.admin.dto.AdminResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final AdminServicePort adminServicePort;
    
    public Order createOrder(OrderRequest request) {
        // Admin 서버에서 관리자 정보 조회
        AdminResponse admin = adminServicePort.getAdmin(request.getAdminId());
        
        // 주문 생성 로직
        // ...
    }
}
*/

