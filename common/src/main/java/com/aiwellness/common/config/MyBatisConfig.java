package com.aiwellness.common.config;

import com.aiwellness.common.persistence.interceptor.MyBatisQueryInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * com.aiwellness.common.config
 * <p>
 * MyBatisConfig
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
@Configuration
@MapperScan(basePackages = "com.aiwellness.**.adapter.persistence")
public class MyBatisConfig {
    
    @Bean
    public MyBatisQueryInterceptor myBatisQueryInterceptor() {
        return new MyBatisQueryInterceptor();
    }
    
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, MyBatisQueryInterceptor queryInterceptor) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml")
        );
        sessionFactory.setTypeAliasesPackage("com.aiwellness.**.domain.model");
        
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setDefaultFetchSize(100);
        configuration.setDefaultStatementTimeout(30);
        
        // Interceptor 등록
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(queryInterceptor);
        sessionFactory.setPlugins(interceptors.toArray(new Interceptor[0]));
        
        sessionFactory.setConfiguration(configuration);
        
        return sessionFactory.getObject();
    }
}

