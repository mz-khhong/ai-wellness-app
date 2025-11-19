package com.aiwellness.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * TransactionConfig
 * <p>
 * 여러 시스템(Admin, Manager, Customer)에서 하나의 통합 DB를 사용하는 경우를 위한 트랜잭션 설정
 * - 트랜잭션 격리 수준: READ_COMMITTED (기본값, 명시적으로 설정)
 * - 트랜잭션 타임아웃: 30초
 */
@Configuration
@EnableTransactionManagement
public class TransactionConfig {
    
    /**
     * 트랜잭션 매니저 설정
     * 
     * 여러 시스템에서 하나의 통합 DB를 사용하는 경우:
     * - 트랜잭션 타임아웃 설정으로 장시간 트랜잭션 방지
     * - 각 서버별로 독립적인 트랜잭션 관리
     * 
     * 참고: 트랜잭션 격리 수준은 application-postgresql.yml의 
     * hikari.connection-init-sql에서 설정됩니다 (READ_COMMITTED)
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        
        // 트랜잭션 타임아웃 설정 (30초)
        transactionManager.setDefaultTimeout(30);
        
        return transactionManager;
    }
}

