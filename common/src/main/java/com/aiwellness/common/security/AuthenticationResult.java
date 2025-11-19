package com.aiwellness.common.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * com.aiwellness.common.security
 * <p>
 * AuthenticationResult
 * <p>
 * 인증 결과를 담는 클래스
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResult {
    private Long userId;
    private String userUuid;        // 사용자 UUID (고유 식별자)
    private String email;
    private String name;
    private Long facilityGroupId;   // 소속 시설 그룹 ID
    private List<String> roles;
}

