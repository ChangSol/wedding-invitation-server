# Wedding Invitation API SERVER

## 1. Requirements

- 축하글 API
  - [x] 조회 no-offset API
  - [x] 등록 API
    - [ ] 사용자계정 자동 매칭
  - [ ] 수정 API
    - [ ] 관리자 가능
    - [ ] 일치하는 사용자계정 가능
  - [ ] 삭제 API
    - [ ] 관리자 가능
    - [ ] 일치하는 사용자계정 가능

## 2. Environment

### 1) 개발 환경
- 기본 사용 포트 : 8080
- 코드컨벤션 : chang_sol_code_style.xml 참고
- JAVA 16
- SpringBoot 2.7.14
- JPA (Spring Data JPA)
- SpringDoc를 통한 OpenAPI(3.0) swagger

### 2) 공통 외부 라이브러리
- module 
  - lombok:1.18.2 => 보일러플레이트 처리.
  - mapstruct:1.5.2.Final => Class간 변환을 처리하기 위한 매퍼 클래스. DTO->Entity, Entity->DTO, DTO->DTO 등
  - guava:31.1-jre => Google Core 라이브러리. 주로 collection 초기화에 사용하였음
  - commons-lang3:3.12.0 => Lang 유틸리티로 사용.
  - commons-collections4:4.4 => Colletion 유틸리티로 사용.
  - commons-io:2.7 => Apache IO 라이브러리. DataReader 등 읽기에 사용하였음
  - springdoc-openapi-ui:1.6.14 => API Docs를 위한 라이브러리. Swagger 작성 

### 3) 모듈 구조 및 의존성관리
- core : 코어 모듈
  - dependency
    - spring-boot-starter-data-jpa : jpa restriction, pageutils를 위한 의존성
- infra-rdb : RDB 모듈
  - 의존 관련
    - profiles이 local 인 경우 h2, prd인 경우 mssql로 의존 변경 필요
    - 역전 의존을 위한 domain 모듈 의존
- domain-core: 도메인 코어 모듈
  - dependency
    - spring-boot-starter-data-jpa : 도메인들이 JPA를 사용을 위한 의존성
- domain-member : 도메인 계정 모듈
  - domain-core : 도메인 코어 모듈 의존
- domain-congratulation : 도메인 축하글 모듈
  - domain-core : 도메인 코어 모듈 의존
- module-app : 창솔루션 모바일 청첩장 API 모듈
  - infra-rdb : RDB 모듈 의존
  - domain-member : 도메인 계정 모듈 의존
  - domain-congratulation : 도메인 축하글 모듈 의존
  - core : 코어 모듈 의존

java -jar "-Dserver.port=8080" "-Dspring.profiles.active=prd" ./changsol-wedding-server-0.0.1-SNAPSHOT.jar
