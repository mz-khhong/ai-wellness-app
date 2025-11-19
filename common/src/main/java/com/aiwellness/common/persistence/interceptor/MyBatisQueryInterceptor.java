package com.aiwellness.common.persistence.interceptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * com.aiwellness.common.persistence.interceptor
 * <p>
 * MyBatisQueryInterceptor
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
@Intercepts({
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MyBatisQueryInterceptor implements Interceptor {
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs().length > 1 ? invocation.getArgs()[1] : null;
        
        String mapperId = mappedStatement.getId();
        String queryId = mappedStatement.getId().substring(mappedStatement.getId().lastIndexOf(".") + 1);
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        String sql = boundSql.getSql().replaceAll("\\s+", " ").trim();
        
        // SQL에서 QueryId 주석 추출 (/* QueryId */ 형식)
        String extractedQueryId = extractQueryIdFromSql(sql);
        String finalQueryId = extractedQueryId != null ? extractedQueryId : queryId;
        
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = invocation.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            log.info("MyBatis Query Executed - Mapper: {}, Query: {}, QueryId: {}, ExecutionTime: {}ms", 
                    mapperId, queryId, finalQueryId, executionTime);
            
            if (log.isDebugEnabled()) {
                log.debug("MyBatis SQL - {}", sql);
                if (parameter != null) {
                    log.debug("MyBatis Parameters - {}", parameter);
                }
            }
            
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("MyBatis Query Failed - Mapper: {}, Query: {}, QueryId: {}, ExecutionTime: {}ms, Error: {}", 
                    mapperId, queryId, finalQueryId, executionTime, e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * SQL에서 QueryId 주석을 추출합니다.
     * 형식:  QueryId
     * 
     * @param sql SQL 쿼리 문자열
     * @return 추출된 QueryId (없으면 null)
     */
    private String extractQueryIdFromSql(String sql) {
        if (sql == null || sql.trim().isEmpty()) {
            return null;
        }
        
        // /* QueryId */ 형식의 주석에서 QueryId 추출
        Pattern pattern = Pattern.compile("/\\*\\s*([^\\s]+(?:\\.[^\\s]+)*)\\s*\\*/");
        Matcher matcher = pattern.matcher(sql);
        
        if (matcher.find()) {
            return matcher.group(1);
        }
        
        return null;
    }
    
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
    
    @Override
    public void setProperties(Properties properties) {
        // 필요시 설정 프로퍼티 처리
    }
}

