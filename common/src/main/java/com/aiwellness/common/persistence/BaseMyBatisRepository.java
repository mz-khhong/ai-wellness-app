package com.aiwellness.common.persistence;

import com.aiwellness.common.persistence.util.MyBatisQueryTracker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * com.aiwellness.common.persistence
 * <p>
 * BaseMyBatisRepository
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
@Repository
@RequiredArgsConstructor
public abstract class BaseMyBatisRepository<T, ID> implements MyBatisRepository<T, ID> {
    
    protected final SqlSession sqlSession;
    
    /**
     * 매퍼 네임스페이스를 반환합니다.
     * 각 리포지토리에서 구현해야 합니다.
     */
    protected abstract String getNamespace();
    
    @Override
    public T findById(ID id) {
        return sqlSession.selectOne(getNamespace() + ".findById", id);
    }
    
    @Override
    public List<T> findAll() {
        return sqlSession.selectList(getNamespace() + ".findAll");
    }
    
    @Override
    public List<T> findByCondition(Map<String, Object> condition) {
        return sqlSession.selectList(getNamespace() + ".findByCondition", condition);
    }
    
    @Override
    public int save(T entity) {
        return sqlSession.insert(getNamespace() + ".save", entity);
    }
    
    @Override
    public int update(T entity) {
        return sqlSession.update(getNamespace() + ".update", entity);
    }
    
    @Override
    public int deleteById(ID id) {
        return sqlSession.delete(getNamespace() + ".deleteById", id);
    }
    
    @Override
    public int deleteByCondition(Map<String, Object> condition) {
        return sqlSession.delete(getNamespace() + ".deleteByCondition", condition);
    }
    
    @Override
    public long countByCondition(Map<String, Object> condition) {
        Long count = sqlSession.selectOne(getNamespace() + ".countByCondition", condition);
        return count != null ? count : 0L;
    }
    
    @Override
    public List<T> findWithPaging(Map<String, Object> condition, int offset, int limit) {
        Map<String, Object> params = Map.of(
                "condition", condition != null ? condition : Map.of(),
                "offset", offset,
                "limit", limit
        );
        return sqlSession.selectList(getNamespace() + ".findWithPaging", params);
    }
    
    /**
     * 커스텀 쿼리 실행 (쿼리 ID 추적 포함)
     */
    protected <R> R selectOne(String statement, Object parameter) {
        String fullStatement = getNamespace() + "." + statement;
        MyBatisQueryTracker.startTracking(getNamespace(), statement, 
                Thread.currentThread().getStackTrace()[2].getMethodName());
        try {
            return sqlSession.selectOne(fullStatement, parameter);
        } finally {
            MyBatisQueryTracker.endTracking();
        }
    }
    
    protected <R> List<R> selectList(String statement, Object parameter) {
        String fullStatement = getNamespace() + "." + statement;
        MyBatisQueryTracker.startTracking(getNamespace(), statement, 
                Thread.currentThread().getStackTrace()[2].getMethodName());
        try {
            return sqlSession.selectList(fullStatement, parameter);
        } finally {
            MyBatisQueryTracker.endTracking();
        }
    }
    
    protected int insert(String statement, Object parameter) {
        String fullStatement = getNamespace() + "." + statement;
        MyBatisQueryTracker.startTracking(getNamespace(), statement, 
                Thread.currentThread().getStackTrace()[2].getMethodName());
        try {
            return sqlSession.insert(fullStatement, parameter);
        } finally {
            MyBatisQueryTracker.endTracking();
        }
    }
    
    protected int update(String statement, Object parameter) {
        String fullStatement = getNamespace() + "." + statement;
        MyBatisQueryTracker.startTracking(getNamespace(), statement, 
                Thread.currentThread().getStackTrace()[2].getMethodName());
        try {
            return sqlSession.update(fullStatement, parameter);
        } finally {
            MyBatisQueryTracker.endTracking();
        }
    }
    
    protected int delete(String statement, Object parameter) {
        String fullStatement = getNamespace() + "." + statement;
        MyBatisQueryTracker.startTracking(getNamespace(), statement, 
                Thread.currentThread().getStackTrace()[2].getMethodName());
        try {
            return sqlSession.delete(fullStatement, parameter);
        } finally {
            MyBatisQueryTracker.endTracking();
        }
    }
}

