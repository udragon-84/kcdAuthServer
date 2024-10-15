### kcd 인증/인가 서버

### 기술 스택 및 Version
* Java Version: Jdk17
* BackEnd: Spring Boot 3.3.4
  * spring-boot-starter-actuator: 애플리케이션의 상태를 모니터링하고 메트릭을 수집하기 위해 사용.
  * spring-boot-starter-validation: 입력 값 검증을 위한 라이브러리, 주로 DTO에서 @Valid와 같은 어노테이션을 사용해 유효성 검사를 수행하기 위해 선택.
  * spring-boot-starter-web: REST API 개발을 위해 사용.
  * spring-boot-starter-data-jpa: JPA를 사용한 데이터베이스 연동 및 ORM(Object-Relational Mapping) 기능 제공.
  * spring-boot-starter-data-redis: Redis를 활용한 캐싱, 세션 관리, OTP 저장 등을 위해 사용.
* DataBase 
  * MySQL 8.0: 관계형 데이터베이스로 트랜잭션 처리 및 데이터 무결성 보장을 위해 사용(회원 등록 및 조회).
  * Redis 7.2.5: TTL 기반의 빠른 데이터 만료를 위해 OTP 처리나 세션 관리에 사용.
* Security & Authentication
  * spring-boot-starter-security: 스프링 시큐리티를 통한 인증 및 권한 부여 설정을 위해 사용.
  * spring-boot-starter-oauth2-client: 카카오 OAuth 2.0 로그인 등 외부 인증 제공자와의 연동을 위해 선택.
  * jwt: 로그인 사용자 정보를 기반으로 JWT(JsonWebToken)을 생성하여 세션 및 쿠키에 저장하는 용도로 사용.
  * 양방향 암호화: 성능과 보안의 균형을 고려하여 AES-128 비트 대칭키 암호화를 사용 개인정보 보호와 효율적인 데이터 처리에 적합하다고 판단.
* OpenAPI Documentation
  * springdoc-openapi-starter-webmvc-ui:2.6.0: REST API를 위한 자동화된 OpenAPI 문서 생성을 위해 사용.
* Build Tool
  * Gradle 8.10.2

### kcdAuthServer Infra 설치 및 설정
* mysql8.0 설치: brew install mysql@8.0
  * database 생성: CREATE DATABASE `kcd` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */
  * 테이블 생성: CREATE TABLE `users` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(128) NOT NULL,
    `mobile` varchar(128) DEFAULT NULL,
    `email` varchar(128) DEFAULT NULL,
    `password` varchar(255) NOT NULL,
    `birthday` varchar(64) NOT NULL,
    `rrn7th` varchar(64) NOT NULL,
    `gender` varchar(64) DEFAULT NULL,
    `nationality` varchar(80) DEFAULT NULL,
    `tsp` varchar(50) DEFAULT NULL,
    `provider` varchar(50) DEFAULT NULL,
    `created_at` datetime(6) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `users_idx_createdAt` (`created_at`),
    KEY `users_idx_name` (`name`),
    KEY `users_idx_mobile` (`mobile`),
    KEY `users_idx_email` (`email`),
    KEY `users_idx_rrn7th` (`rrn7th`)
  ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
  * mysql 서비스 시작: brew services start mysql@8.0
  * mysql 서비스 종료: brew services stop mysql@8.0

* Redis7.2.5 설치: brew install redis
  * Redis Acl 설정: https://leehah0908.tistory.com/52
  * redis 서비스 시작: brew services start redis
  * redis 서비스 종료: brew services stop redis

### kcdAuthServer Api 명세
* 서버 구동 후 swagger-ui 를 통해서 테스트 가능합니다.
* swagger-ui: http://localhost:8080/swagger-ui/index.html
* api-docs: http://localhost:8080/api-docs

### 카카오 oAuth2 인증 및 로그인
* 인증 및 로그인 url: http://localhost:8080/login

### 프로젝트 패키지 구성
* com.kcd.api: 
  * opt 발급 및 검증
  * 회원 가입 및 회원 정보 조회
  * 로그인 및 로그아웃
* com.kcd.common
  * 양방향 암호화 처리 AES-128
  * Exception 처리 및 핸들러
  * jwt 토큰 발급 및 검증
  * session 및 쿠키 토큰 값 저장
* com.kcd.config
  * Redis 설정
  * OpenApiConfig 설정
  * Spring Security 및 oAuth2 핸들러 설정
* com.kcd.repository
  * Redis OTP Repository
  * User Repository 및 Entity
* com.kcd.service
  * 로그인 관련 서비스 구현
  * 회원 관련 서비스 구현
  * otp 관련 서비스 구현
  * 카카오 oAuth2 인증 및 핸들러 구현