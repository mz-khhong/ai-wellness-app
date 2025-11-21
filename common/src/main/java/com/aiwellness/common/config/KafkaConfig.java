package com.aiwellness.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * com.aiwellness.common.config
 * <p>
 * KafkaConfig
 * <p>
 * Kafka Producer 설정
 * <p>
 * Kafka 메시지 발행을 위한 ProducerFactory와 KafkaTemplate을 설정합니다.
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
@Slf4j
@Configuration
public class KafkaConfig {
    
    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;
    
    @Value("${spring.kafka.producer.acks:all}")
    private String acks;
    
    @Value("${spring.kafka.producer.retries:3}")
    private Integer retries;
    
    @Value("${spring.kafka.producer.properties.max.in.flight.requests.per.connection:5}")
    private Integer maxInFlightRequestsPerConnection;
    
    @Value("${spring.kafka.producer.properties.enable.idempotence:true}")
    private Boolean enableIdempotence;
    
    @Value("${spring.kafka.producer.properties.request.timeout.ms:30000}")
    private Integer requestTimeoutMs;
    
    @Value("${spring.kafka.producer.properties.delivery.timeout.ms:120000}")
    private Integer deliveryTimeoutMs;
    
    @Value("${spring.kafka.producer.properties.metadata.max.age.ms:300000}")
    private Long metadataMaxAgeMs;
    
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, acks);
        configProps.put(ProducerConfig.RETRIES_CONFIG, retries);
        configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, maxInFlightRequestsPerConnection);
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, enableIdempotence);
        
        // 연결 타임아웃 설정
        if (requestTimeoutMs != null) {
            configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeoutMs);
        }
        if (deliveryTimeoutMs != null) {
            configProps.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeoutMs);
        }
        if (metadataMaxAgeMs != null) {
            configProps.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, metadataMaxAgeMs);
        }
        
        log.info("[KafkaConfig] Kafka Producer configured. Bootstrap servers: {}", bootstrapServers);
        
        return new DefaultKafkaProducerFactory<>(configProps);
    }
    
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}

