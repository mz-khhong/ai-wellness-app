package com.aiwellness.common.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.aiwellness.common.config.FilterPathConfig;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * com.aiwellness.common.logging
 * <p>
 * TraceIdFilter
 * <p>
 * HTTP 요청에 대한 traceId를 생성하고 MDC에 설정하여 로그 추적을 가능하게 합니다.
 * <p>
 * <b>동작 방식:</b>
 * <ul>
 *   <li>HTTP 요청 헤더에서 X-Trace-Id 또는 X-Request-Id를 확인</li>
 *   <li>헤더에 traceId가 없으면 UUID로 생성</li>
 *   <li>MDC에 traceId를 설정하여 모든 로그에 포함</li>
 *   <li>응답 헤더에 traceId를 포함하여 클라이언트가 추적 가능하도록 함</li>
 * </ul>
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
/**
 * TraceIdFilter는 SecurityConfig에서 addFilterBefore()를 통해
 * 가장 먼저 실행되도록 명시적으로 순서가 지정됩니다.
 * @Order 어노테이션은 필터 체인에서의 순서를 보장하지 않으므로 사용하지 않습니다.
 */
@Slf4j
@Component
public class TraceIdFilter extends OncePerRequestFilter {
    
    private final FilterPathConfig filterPathConfig;
    
    public TraceIdFilter(FilterPathConfig filterPathConfig) {
        this.filterPathConfig = filterPathConfig;
    }
    
    private static final String TRACE_ID_HEADER = "X-Trace-Id";
    private static final String REQUEST_ID_HEADER = "X-Request-Id";
    private static final String SPAN_ID_HEADER = "X-Span-Id";
    private static final String MDC_TRACE_ID_KEY = "traceId";
    private static final String MDC_SPAN_ID_KEY = "spanId";
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        try {
            // HTTP 요청 헤더에서 traceId 확인
            String traceId = extractTraceId(request);
            
            // HTTP 요청 헤더에서 spanId 확인 (없으면 생성)
            String spanId = extractSpanId(request);
            
            // MDC에 traceId와 spanId 설정 (모든 로그에 자동 포함됨)
            MDC.put(MDC_TRACE_ID_KEY, traceId);
            MDC.put(MDC_SPAN_ID_KEY, spanId);
            
            // 응답 헤더에 traceId와 spanId 포함 (클라이언트가 추적 가능하도록)
            response.setHeader(TRACE_ID_HEADER, traceId);
            response.setHeader(SPAN_ID_HEADER, spanId);
            
            filterChain.doFilter(request, response);
        } finally {
            // 요청 처리 완료 후 MDC 정리 (메모리 누수 방지)
            MDC.clear();
        }
    }
    
    /**
     * HTTP 요청 헤더에서 traceId를 추출하거나 생성합니다.
     * <p>
     * 우선순위:
     * <ol>
     *   <li>X-Trace-Id 헤더</li>
     *   <li>X-Request-Id 헤더</li>
     *   <li>없으면 UUID로 생성</li>
     * </ol>
     *
     * @param request HTTP 요청
     * @return traceId
     */
    private String extractTraceId(HttpServletRequest request) {
        // X-Trace-Id 헤더 확인
        String traceId = request.getHeader(TRACE_ID_HEADER);
        if (traceId != null && !traceId.trim().isEmpty()) {
            return traceId.trim();
        }
        
        // X-Request-Id 헤더 확인
        traceId = request.getHeader(REQUEST_ID_HEADER);
        if (traceId != null && !traceId.trim().isEmpty()) {
            return traceId.trim();
        }
        
        // 헤더에 없으면 UUID로 생성
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * HTTP 요청 헤더에서 spanId를 추출하거나 생성합니다.
     * <p>
     * 우선순위:
     * <ol>
     *   <li>X-Span-Id 헤더</li>
     *   <li>없으면 UUID로 생성 (8자리 짧은 형식)</li>
     * </ol>
     *
     * @param request HTTP 요청
     * @return spanId
     */
    private String extractSpanId(HttpServletRequest request) {
        // X-Span-Id 헤더 확인
        String spanId = request.getHeader(SPAN_ID_HEADER);
        if (spanId != null && !spanId.trim().isEmpty()) {
            return spanId.trim();
        }
        
        // 헤더에 없으면 UUID로 생성 (8자리 짧은 형식)
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 설정 파일에서 관리하는 제외 경로 확인
        String path = request.getRequestURI();
        return filterPathConfig.isExcluded(path);
    }
}

