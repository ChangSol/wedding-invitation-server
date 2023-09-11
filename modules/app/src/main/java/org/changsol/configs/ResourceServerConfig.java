package org.changsol.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String ANT_PATTERN_1 = "/v1/**";

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.headers()
			.frameOptions()
			.disable()
			.and()
			.antMatcher(ANT_PATTERN_1)
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, ANT_PATTERN_1).permitAll()
			.antMatchers(HttpMethod.POST, ANT_PATTERN_1).permitAll()
			.antMatchers(HttpMethod.PUT, ANT_PATTERN_1).permitAll()
			.antMatchers(HttpMethod.DELETE, ANT_PATTERN_1).permitAll()
			.anyRequest().authenticated()
			.and()
			.exceptionHandling()
			.accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
}
