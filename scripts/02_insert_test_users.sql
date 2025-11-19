-- 테스트 사용자 데이터 삽입
-- 데이터베이스: aiwellnessdb
-- 주의: facility_groups 테이블이 먼저 생성되어 있어야 합니다.

-- 1. 기본 Facility Group 생성 (없는 경우)
INSERT INTO facility_groups (name, type, status)
VALUES ('기본 시설 그룹', 'MIXED', 'ACTIVE')
ON CONFLICT DO NOTHING;

-- Facility Group ID 조회를 위한 변수 (PostgreSQL에서는 DO 블록 사용)
DO $$
DECLARE
    default_facility_group_id BIGINT;
BEGIN
    -- 기본 시설 그룹 ID 조회
    SELECT id INTO default_facility_group_id 
    FROM facility_groups 
    WHERE name = '기본 시설 그룹' 
    LIMIT 1;

    -- 기본 시설 그룹이 없으면 생성
    IF default_facility_group_id IS NULL THEN
        INSERT INTO facility_groups (name, type, status)
        VALUES ('기본 시설 그룹', 'MIXED', 'ACTIVE')
        RETURNING id INTO default_facility_group_id;
    END IF;

    -- 2. Admin 테스트 사용자
    INSERT INTO admins (facility_group_id, email, name, password, status)
    VALUES 
        (default_facility_group_id, 'admin@example.com', '테스트 관리자', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ACTIVE'),
        (default_facility_group_id, 'admin@test.com', '테스트 관리자2', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ACTIVE')
    ON CONFLICT (facility_group_id, email) DO NOTHING;

    -- 3. Manager 테스트 사용자
    INSERT INTO managers (facility_group_id, email, name, password, status)
    VALUES 
        (default_facility_group_id, 'manager@test.com', '테스트 매니저', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ACTIVE')
    ON CONFLICT (facility_group_id, email) DO NOTHING;

    -- 4. Customer 테스트 사용자
    INSERT INTO customers (facility_group_id, email, name, password, status)
    VALUES 
        (default_facility_group_id, 'customer@test.com', '테스트 고객', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ACTIVE')
    ON CONFLICT (facility_group_id, email) DO NOTHING;

    -- 5. Categories 테스트 데이터
    INSERT INTO categories (name, description, display_order, is_active)
    VALUES 
        ('헬스케어', '건강 관리 관련 카테고리', 1, true),
        ('피트니스', '운동 및 피트니스 관련 카테고리', 2, true),
        ('웰니스', '웰빙 및 웰니스 관련 카테고리', 3, true)
    ON CONFLICT (name) DO NOTHING;
END $$;

-- 비밀번호: password123
-- BCrypt 해시: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy

