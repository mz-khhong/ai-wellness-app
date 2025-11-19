package com.aiwellness.common.persistence.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * com.aiwellness.common.persistence.interceptor
 * <p>
 * QueryIdGenerationInterceptor
 * <p>
 * MyBatis 쿼리에 QueryId 주석을 자동으로 추가하는 Interceptor
 * <p>
 * 실행되는 SQL 쿼리 앞에 ` QueryId` 형식의 주석을 추가하여
 * 데이터베이스 로그에서 어떤 Mapper 메서드에서 실행된 쿼리인지 추적할 수 있도록 합니다.
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 19.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 19.    메가존 시스템            최초 생성
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
@Intercepts({
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class QueryIdGenerationInterceptor implements Interceptor {
    
    private final String defaultQueryIdPrefix;
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            Object[] args = invocation.getArgs();
            MappedStatement mappedStatement = (MappedStatement) args[0];
            args[0] = createNewMappedStatement(mappedStatement);
            log.trace(">>> 쿼리ID 주석 자동 적용: {}", mappedStatement.getId());
        } catch (Exception e) {
            log.warn("쿼리ID 주석 자동 적용에 실패했습니다. 무시하고 계속 진행합니다.", e);
        }
        return invocation.proceed();
    }
    
    private MappedStatement createNewMappedStatement(MappedStatement mappedStatement) {
        String[] keyProperties = mappedStatement.getKeyProperties();
        String[] keyColumns = mappedStatement.getKeyColumns();
        String[] resultSets = mappedStatement.getResultSets();
        return new MappedStatement.Builder(
            mappedStatement.getConfiguration(),
            mappedStatement.getId(),
            new SqlSourceWrapper(mappedStatement, defaultQueryIdPrefix),
            mappedStatement.getSqlCommandType())
            .resource(mappedStatement.getResource())
            .fetchSize(mappedStatement.getFetchSize())
            .timeout(mappedStatement.getTimeout())
            .statementType(mappedStatement.getStatementType())
            .resultSetType(mappedStatement.getResultSetType())
            .cache(mappedStatement.getCache())
            .parameterMap(mappedStatement.getParameterMap())
            .resultMaps(mappedStatement.getResultMaps())
            .flushCacheRequired(mappedStatement.isFlushCacheRequired())
            .useCache(mappedStatement.isUseCache())
            .resultOrdered(mappedStatement.isResultOrdered())
            .keyProperty(keyProperties != null ? String.join(",", keyProperties) : null)
            .keyColumn(keyColumns != null ? String.join(",", keyColumns) : null)
            .databaseId(mappedStatement.getDatabaseId())
            .lang(mappedStatement.getLang())
            .resultSets(resultSets != null ? String.join(",", resultSets) : null)
            .dirtySelect(mappedStatement.isDirtySelect())
            .keyGenerator(mappedStatement.getKeyGenerator())
            .build();
    }
    
    @RequiredArgsConstructor
    static class SqlSourceWrapper implements SqlSource {
        
        private final MappedStatement mappedStatement;
        private final String defaultQueryIdPrefix;
        
        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(parameterObject);
            Configuration config = mappedStatement.getConfiguration();
            QueryIdGenerationBoundSql newBoundSql = new QueryIdGenerationBoundSql(
                config, 
                boundSql, 
                mappedStatement.getId(), 
                defaultQueryIdPrefix
            );
            boundSql.getAdditionalParameters()
                .forEach(newBoundSql::setAdditionalParameter);
            return newBoundSql;
        }
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

