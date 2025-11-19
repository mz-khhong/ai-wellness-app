# AI Wellness App

헥사고날 아키텍처를 적용한 Spring Boot 기반 멀티 모듈 프로젝트입니다.

## 프로젝트 구조

```
ai-wellness-app/
├── common/              # 공통 모듈 (인증, 검증, Utils, QueryDSL, MyBatis)
├── admin-server/        # 관리자 서버 (포트: 8080)
├── manager-server/      # 매니저 서버 (포트: 8081)
├── customer-server/     # 고객 서버 (포트: 8082)
└── kafka-module/        # Kafka 모듈
```

## 기술 스택

- **Java**: 21
- **Spring Boot**: 3.2.0
- **Build Tool**: Gradle 8.10
- **Database**: PostgreSQL (통합 DB)
- **Architecture**: Hexagonal Architecture (Ports & Adapters)
- **API Documentation**: Swagger/OpenAPI 3 (SpringDoc 2.3.0)
- **Query Builder**: MyBatis (QueryDSL 제거됨)
- **ORM**: MyBatis 3.0.3
- **Message Queue**: Apache Kafka
- **Code Coverage**: JaCoCo

## 빠른 시작

### 1. PostgreSQL 설치 및 데이터베이스 생성

```bash
# PostgreSQL 설치
brew install postgresql@15
brew services start postgresql@15

# 데이터베이스 생성 (스크립트 사용)
./scripts/create_database.sh
```

### 2. 프로젝트 빌드

```bash
./gradlew clean build
```

### 3. 서버 실행

```bash
# Admin Server (터미널 1)
./gradlew :admin-server:bootRun

# Manager Server (터미널 2)
./gradlew :manager-server:bootRun

# Customer Server (터미널 3)
./gradlew :customer-server:bootRun
```

### 4. Swagger UI 접근

- **Admin Server**: http://localhost:8080/swagger-ui.html
- **Manager Server**: http://localhost:8081/swagger-ui.html
- **Customer Server**: http://localhost:8082/swagger-ui.html

## 주요 기능

- **통합 데이터베이스**: 3개의 서버가 하나의 PostgreSQL 데이터베이스를 공유
- **트랜잭션 격리 수준**: 모든 서비스에서 `READ_COMMITTED` 격리 수준 보장
- **API 로깅**: 모든 API 호출에 대해 요청/응답 로그 자동 기록
- **다국어 지원**: 한국어(ko), 영어(en) 지원
- **JWT 인증**: 토큰 기반 인증 및 Role 기반 접근 제어
- **QueryDSL**: N+1 문제 대응
- **MyBatis**: JDBC 세밀한 제어

## 개발 가이드

자세한 개발 가이드는 [developer-guide](./developer-guide/) 폴더를 참조하세요.

### 주요 가이드 문서

- **[01_QUICK_START.md](./developer-guide/01_QUICK_START.md)**: 빠른 시작 가이드
- **[02_POSTGRESQL_SETUP.md](./developer-guide/02_POSTGRESQL_SETUP.md)**: PostgreSQL 설치 및 설정
- **[03_ARCHITECTURE.md](./developer-guide/03_ARCHITECTURE.md)**: 아키텍처 구조 설명
- **[04_USAGE_GUIDE.md](./developer-guide/04_USAGE_GUIDE.md)**: 사용 가이드
- **[05_SWAGGER_GUIDE.md](./developer-guide/05_SWAGGER_GUIDE.md)**: Swagger UI 사용 가이드
- **[06_MULTI_DOMAIN_GUIDE.md](./developer-guide/06_MULTI_DOMAIN_GUIDE.md)**: 멀티 도메인 작업 가이드
- **[07_DOMAIN_MODEL_GUIDE.md](./developer-guide/07_DOMAIN_MODEL_GUIDE.md)**: Domain Model 구조 가이드
- **[10_I18N_GUIDE.md](./developer-guide/10_I18N_GUIDE.md)**: 다국어 처리 가이드
- **[12_EVENT_DRIVEN_GUIDE.md](./developer-guide/12_EVENT_DRIVEN_GUIDE.md)**: Event-Driven Architecture 가이드
- **[13_HEXAGONAL_ARCHITECTURE_GUIDE.md](./developer-guide/13_HEXAGONAL_ARCHITECTURE_GUIDE.md)**: 헥사고날 아키텍처 실전 가이드
- **[14_RBAC_GUIDE.md](./developer-guide/14_RBAC_GUIDE.md)**: Role-Based Access Control 가이드
- **[15_APPLICATION_RUN_GUIDE.md](./developer-guide/15_APPLICATION_RUN_GUIDE.md)**: 애플리케이션 실행 가이드

자세한 목록은 [developer-guide/README.md](./developer-guide/README.md)를 참조하세요.

## API 엔드포인트

### Auth (공통)
- `POST /api/v1/auth/login` - 로그인 (JWT 토큰 발급)

### Admin Server (포트: 8080)
- `GET /api/v1/admins/{id}` - 관리자 조회
- `POST /api/v1/admins` - 관리자 생성
- `PUT /api/v1/admins/{id}` - 관리자 수정
- `DELETE /api/v1/admins/{id}` - 관리자 삭제

### Manager Server (포트: 8081)
- `GET /api/v1/managers/{id}` - 매니저 조회
- `POST /api/v1/managers` - 매니저 생성
- `PUT /api/v1/managers/{id}` - 매니저 수정
- `DELETE /api/v1/managers/{id}` - 매니저 삭제

### Customer Server (포트: 8082)
- `GET /api/v1/customers/{id}` - 고객 조회
- `POST /api/v1/customers` - 고객 생성
- `PUT /api/v1/customers/{id}` - 고객 수정
- `DELETE /api/v1/customers/{id}` - 고객 삭제

## 데이터베이스 접속

```bash
psql -U aiuser -d aiwellnessdb -h localhost
# 비밀번호: mzaiuser@@
```

또는 GUI 도구 사용:
- **pgAdmin**: `brew install --cask pgadmin4`
- **DBeaver**: `brew install --cask dbeaver-community`

## 테스트

```bash
# 전체 테스트 실행
./gradlew test

# 특정 서버 테스트
./gradlew :admin-server:test
./gradlew :manager-server:test
./gradlew :customer-server:test
```

## CI/CD

GitHub Actions를 통한 CI/CD 파이프라인이 구성되어 있습니다:
- 빌드 및 테스트
- 코드 품질 검사
- Docker 이미지 빌드
- 배포 (main 브랜치)

## 라이선스

이 프로젝트는 내부 사용을 위한 것입니다.
