package com.aiwellness.admin.adapter.persistence.mybody;

import com.aiwellness.admin.domain.model.mybody.MyBody;
import com.aiwellness.admin.domain.port.mybody.MyBodyRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * com.aiwellness.admin.adapter.persistence.mybody
 * <p>
 * MyBodyAdapter
 * <p>
 * MY BODY 도메인 저장소 어댑터 (MyBatis 구현)
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 18.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 18.    메가존 시스템            최초 생성
 * </pre>
 */
@Repository
@RequiredArgsConstructor
public class MyBodyAdapter implements MyBodyRepositoryPort {
    
    private final MyBodyMapper myBodyMapper;
    
    @Override
    public MyBody save(MyBody myBody) {
        LocalDateTime now = LocalDateTime.now();
        if (myBody.getId() == null) {
            // 새 엔티티: createdAt, updatedAt 설정 후 INSERT
            myBody.setCreatedAt(now);
            myBody.setUpdatedAt(now);
            myBodyMapper.insert(myBody);
        } else {
            // 기존 엔티티: updatedAt만 갱신 후 UPDATE
            myBody.setUpdatedAt(now);
            myBodyMapper.update(myBody);
        }
        return myBody;
    }
    
    @Override
    public Optional<MyBody> findById(Long id) {
        MyBody myBody = myBodyMapper.findById(id);
        return Optional.ofNullable(myBody);
    }
    
    @Override
    public Optional<MyBody> findByCustomerId(Long customerId) {
        MyBody myBody = myBodyMapper.findByCustomerId(customerId);
        return Optional.ofNullable(myBody);
    }
    
    @Override
    public void deleteById(Long id) {
        myBodyMapper.deleteById(id);
    }
}

