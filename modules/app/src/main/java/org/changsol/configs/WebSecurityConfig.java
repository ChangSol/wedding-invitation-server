package org.changsol.configs;

import lombok.RequiredArgsConstructor;
import org.changsol.securities.supports.RestAuthenticationEntryPoint;
import org.changsol.securities.supports.RestAuthenticationFailureHandler;
import org.changsol.securities.supports.RestAuthenticationSuccessHandler;
import org.changsol.securities.supports.RestLogoutSuccessHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String ANT_PATTERN_1 = "/v1/**";

	@Bean
	@ConditionalOnMissingBean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(
			"/docs/index.html",
			"/swagger-ui.html",
			"/swagger-ui/**",
			"/swagger-resources/**",
			"/v2/api-docs");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, ANT_PATTERN_1).permitAll()
			.antMatchers(HttpMethod.POST, ANT_PATTERN_1).permitAll()
			.antMatchers(HttpMethod.GET, ANT_PATTERN_1).permitAll()
			.antMatchers(HttpMethod.PUT, ANT_PATTERN_1).permitAll()
			.antMatchers(HttpMethod.DELETE, ANT_PATTERN_1).permitAll()
			.antMatchers("/oauth/**").permitAll()
			.antMatchers("/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
			.antMatchers("/h2-console/**").permitAll()
			.antMatchers("/error").permitAll()
			.anyRequest().authenticated()
			.and()
			.exceptionHandling()
			.accessDeniedHandler(new OAuth2AccessDeniedHandler())
			.authenticationEntryPoint(new RestAuthenticationEntryPoint())
			.and()
			.formLogin()
			.successHandler(new RestAuthenticationSuccessHandler())
			.failureHandler(new RestAuthenticationFailureHandler())
			.and()
			.logout()
			.logoutSuccessHandler(new RestLogoutSuccessHandler())
			.and()
			.csrf()
			.ignoringAntMatchers("/h2-console/**")
			.disable()
			.cors()
			.and()
			.headers()
			.frameOptions().disable();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.addAllowedMethod("GET");
		configuration.addAllowedMethod("POST");
		configuration.addAllowedMethod("PUT");
		configuration.addAllowedMethod("DELETE");
		configuration.addAllowedMethod("OPTIONS");
		configuration.addAllowedHeader("Authorization");
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
