package com.aiwellness.manager.domain.model.customer;

import com.aiwellness.manager.domain.model.enums.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * com.aiwellness.manager.domain.model.customer
 * <p>
 * Customer
 * <p>
 * 고객 도메인 모델
 * <p>
 * 데이터베이스 테이블: customers (1:1 매핑)
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 20.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 20.    메가존 시스템            최초 생성
 * </pre>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    
    private Long id;
    private Long facilityGroupId;
    private String uuid;
    private String email;
    private String name;
    private String password;
    private CustomerStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

