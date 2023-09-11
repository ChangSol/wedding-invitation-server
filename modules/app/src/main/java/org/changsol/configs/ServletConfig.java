package org.changsol.configs;

import java.io.IOException;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.changsol.filters.RequestWrapper;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ServletConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("*");
	}

	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter() {
			@Override
			protected void doFilterInternal(
				@NonNull HttpServletRequest request,
				@NonNull HttpServletResponse response,
				@NonNull FilterChain chain
			) throws ServletException, IOException {
				chain.doFilter(new RequestWrapper(request), response);
			}
		};
	}

	@Bean
	public ErrorAttributes errorAttributes() {
		return new DefaultErrorAttributes() {
			@Override
			public Map<String, Object> getErrorAttributes(WebRequest webRequest,
														  ErrorAttributeOptions options) {
				Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
				errorAttributes.remove("exception");
				errorAttributes.remove("trace");
				return errorAttributes;
			}
		};
	}
}
