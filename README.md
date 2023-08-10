# Wedding Invitation API SERVER

## 1. Requirements

- 사진 API
  - [ ] TOP 사진 조회, 수정 API
    - [ ] 수정 API는 관리자만 가능
  - [ ] 사진 조회, 수정 API
    - [ ] 수정 API는 관리자만 가능
- 축하글 API
  - [ ] 축하글 조회 no-offset, 수정 API
    - [ ] 수정 API는 관리자만 가능

## 2. Environment

### 1) 개발 환경
- 기본 사용 포트 : 8080
- 코드컨벤션 : chang_sol_code_style.xml 참고
- JAVA 16
- SpringBoot 2.7.14
- JPA (Spring Data JPA)
- SpringDoc를 통한 OpenAPI(3.0) swagger 

```yaml
#SrpingDoc 설정 API 문서화
springdoc:
  version: 0.0.1
  api-docs:
    path: /api-docs
  #Media Type 기본 값을 application/json
  default-consumes-media-type: application/json;charset=UTF-8
  default-produces-media-type: application/json;charset=UTF-8
  cache:
    disabled: true
  swagger-ui:
    #api 및 태그 정렬 기준을 알파벳 오름차순
    operations-sorter: alpha
    tags-sorter: alpha
    path: /swagger-ui
    #swagger-ui default url인 petstore html 문서 비활성화 여부
    disable-swagger-default-url: true
    display-request-duration: true  # try it out 을 했을 때 request duration 을 추가로 찍어줌
  #OpenAPI 3 로 문서화할 api path 리스트
  paths-to-match:
    - /v1/**
```

```java

@Component
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion) {
		Info info = new Info()
			.title("Changsol Blog Search API")
			.version(appVersion)
			.description("창솔루션 모바일 청첩장 API 서비스에 오신걸 환영합니다.😁")
			.contact(new Contact().name("changsol-github").url("https://github.com/ChangSol"))
			.license(new License().name("Apache License Version 2.0")
								  .url("http://www.apache.org/licenses/LICENSE-2.0"));

		return new OpenAPI()
			.components(new Components())
			.info(info);
	}
}
```

### 2) 외부 라이브러리
- module 
  - lombok:1.18.2 => 보일러플레이트 처리.
  - mapstruct:1.5.2.Final => Class간 변환을 처리하기 위한 매퍼 클래스. DTO->Entity, Entity->DTO, DTO->DTO 등
  - guava:31.1-jre => Google Core 라이브러리. 주로 collection 초기화에 사용하였음
  - commons-lang3:3.12.0 => Lang 유틸리티로 사용.
  - commons-collections4:4.4 => Colletion 유틸리티로 사용.
  - commons-io:2.7 => Apache IO 라이브러리. DataReader 등 읽기에 사용하였음
- module-app
  - springdoc-openapi-ui:1.6.14 => API Docs를 위한 라이브러리. Swagger 작성 

### 3) 모듈 구조

- module-domain : 도메인 모듈
- module-infra : DB관련 모듈
- module-app : 창솔루션 모바일 청첩장 API 모듈 

