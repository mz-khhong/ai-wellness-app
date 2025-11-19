-- AI Wellness App Database Schema
-- 데이터베이스: aiwellnessdb

-- 1. Facility Groups 테이블
CREATE TABLE IF NOT EXISTS facility_groups (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    address TEXT,
    description TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_facility_group_type CHECK (type IN ('APARTMENT', 'OFFICE', 'COMMERCIAL', 'MIXED')),
    CONSTRAINT chk_facility_group_status CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED'))
);

COMMENT ON TABLE facility_groups IS '시설 그룹 정보 테이블';

-- 2. Admins 테이블 (facility_group_id, uuid 포함)
CREATE TABLE IF NOT EXISTS admins (
    id BIGSERIAL PRIMARY KEY,
    facility_group_id BIGINT NOT NULL REFERENCES facility_groups(id) ON DELETE RESTRICT,
    uuid VARCHAR(36) NOT NULL UNIQUE DEFAULT gen_random_uuid()::text,
    email VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_admin_status CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED')),
    CONSTRAINT uk_admins_facility_group_email UNIQUE (facility_group_id, email)
);

COMMENT ON TABLE admins IS '관리자 정보 테이블';
COMMENT ON COLUMN admins.facility_group_id IS '소속 시설 그룹 ID (Foreign Key)';
COMMENT ON COLUMN admins.uuid IS '관리자 UUID (고유 식별자)';

-- 3. Managers 테이블 (facility_group_id, uuid 포함)
CREATE TABLE IF NOT EXISTS managers (
    id BIGSERIAL PRIMARY KEY,
    facility_group_id BIGINT NOT NULL REFERENCES facility_groups(id) ON DELETE RESTRICT,
    uuid VARCHAR(36) NOT NULL UNIQUE DEFAULT gen_random_uuid()::text,
    email VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_manager_status CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED')),
    CONSTRAINT uk_managers_facility_group_email UNIQUE (facility_group_id, email)
);

COMMENT ON TABLE managers IS '매니저 정보 테이블';
COMMENT ON COLUMN managers.facility_group_id IS '소속 시설 그룹 ID (Foreign Key)';
COMMENT ON COLUMN managers.uuid IS '매니저 UUID (고유 식별자)';


-- 4. Customers 테이블 (facility_group_id, uuid 포함)
CREATE TABLE IF NOT EXISTS customers (
    id BIGSERIAL PRIMARY KEY,
    facility_group_id BIGINT NOT NULL REFERENCES facility_groups(id) ON DELETE RESTRICT,
    uuid VARCHAR(36) NOT NULL UNIQUE DEFAULT gen_random_uuid()::text,
    email VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_customer_status CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED')),
    CONSTRAINT uk_customers_facility_group_email UNIQUE (facility_group_id, email)
);

COMMENT ON TABLE customers IS '고객 정보 테이블';
COMMENT ON COLUMN customers.facility_group_id IS '소속 시설 그룹 ID (Foreign Key)';
COMMENT ON COLUMN customers.uuid IS '고객 UUID (고유 식별자)';

-- 5. Categories 테이블
CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    display_order INTEGER,
    is_active BOOLEAN NOT NULL DEFAULT true,
    CONSTRAINT chk_category_display_order CHECK (display_order IS NULL OR display_order >= 0)
);

COMMENT ON TABLE categories IS '카테고리 정보 테이블';

