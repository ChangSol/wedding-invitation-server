package org.changsol.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String ANT_PATTERN_1 = "/v1/**";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, ANT_PATTERN_1).permitAll()
			.antMatchers(HttpMethod.POST, ANT_PATTERN_1).permitAll()
			.antMatchers(HttpMethod.GET, ANT_PATTERN_1).permitAll()
			.antMatchers(HttpMethod.PUT, ANT_PATTERN_1).permitAll()
			.antMatchers(HttpMethod.DELETE, ANT_PATTERN_1).permitAll()
			.antMatchers("/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
			.antMatchers("/h2-console/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.cors().disable()
			.csrf().disable()
			.formLogin().disable()
			.headers().frameOptions().disable();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
