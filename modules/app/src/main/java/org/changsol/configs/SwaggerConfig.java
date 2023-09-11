package org.changsol.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Swagger View Config Class
 */
@Component
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion) {
		Info info = new Info()
			.title("Changsol Wedding Invitation API")
			.version(appVersion)
			.description("창솔루션 모바일 청첩장 API 서비스에 오신걸 환영합니다.😁")
			.contact(new Contact().name("changsol-github").url("https://github.com/ChangSol"))
			.license(new License().name("Apache License Version 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"));

		// API 요청헤더에 인증정보 포함
		SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");
		// SecuritySchemes 등록
		Components components = new Components()
			.addSecuritySchemes("bearerAuth",
								new SecurityScheme()
									.name("bearerAuth")
									.type(SecurityScheme.Type.HTTP)
									.scheme("bearer")
									.bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional)

		return new OpenAPI()
			.info(info)
			.addSecurityItem(securityRequirement)
			.components(components);
	}
}
