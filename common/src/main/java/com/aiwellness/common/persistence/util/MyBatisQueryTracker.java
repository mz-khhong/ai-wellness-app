package com.aiwellness.common.persistence.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.MDC;

/**
 * com.aiwellness.common.persistence.util
 * <p>
 * MyBatisQueryTracker
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 14.    메가존 시스템            최초 생성
 * </pre>
 */
@Slf4j
public class MyBatisQueryTracker {
    
    private static final String QUERY_CONTEXT_KEY = "query.context";
    private static final String QUERY_PURPOSE_KEY = "query.purpose";
    
    /**
     * 쿼리 실행 컨텍스트 설정
     * @param context 컨텍스트 정보 (예: "AdminService.getAdmin")
     */
    public static void setContext(String context) {
        MDC.put(QUERY_CONTEXT_KEY, context);
    }
    
    /**
     * 쿼리 실행 목적 설정
     * @param purpose 목적 정보 (예: "관리자 조회", "관리자 목록 조회")
     */
    public static void setPurpose(String purpose) {
        MDC.put(QUERY_PURPOSE_KEY, purpose);
    }
    
    /**
     * 쿼리 실행 추적 시작
     * @param mapperId Mapper ID
     * @param queryId Query ID
     * @param context 컨텍스트 정보
     */
    public static void startTracking(String mapperId, String queryId, String context) {
        MDC.put("tracking.mapper.id", mapperId);
        MDC.put("tracking.query.id", queryId);
        MDC.put(QUERY_CONTEXT_KEY, context);
        log.debug("Query tracking started - Mapper: {}, Query: {}, Context: {}", mapperId, queryId, context);
    }
    
    /**
     * 쿼리 실행 추적 종료
     */
    public static void endTracking() {
        MDC.remove("tracking.mapper.id");
        MDC.remove("tracking.query.id");
        MDC.remove(QUERY_CONTEXT_KEY);
        MDC.remove(QUERY_PURPOSE_KEY);
    }
    
    /**
     * SqlSession을 사용한 쿼리 실행 추적
     * @param sqlSession SqlSession
     * @param mapperId Mapper ID
     * @param queryId Query ID
     * @param parameter 파라미터
     * @param <T> 반환 타입
     * @return 쿼리 실행 결과
     */
    public static <T> T executeWithTracking(SqlSession sqlSession, String mapperId, String queryId, Object parameter) {
        String fullQueryId = mapperId + "." + queryId;
        startTracking(mapperId, queryId, Thread.currentThread().getStackTrace()[2].getMethodName());
        
        try {
            long startTime = System.currentTimeMillis();
            T result = sqlSession.selectOne(fullQueryId, parameter);
            long executionTime = System.currentTimeMillis() - startTime;
            
            log.info("Query executed successfully - Mapper: {}, Query: {}, ExecutionTime: {}ms", 
                    mapperId, queryId, executionTime);
            
            return result;
        } catch (Exception e) {
            log.error("Query execution failed - Mapper: {}, Query: {}", mapperId, queryId, e);
            throw e;
        } finally {
            endTracking();
        }
    }
}

