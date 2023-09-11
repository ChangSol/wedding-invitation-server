package org.changsol.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

//	private final PermissionEvaluatorImpl permissionEvaluator;
//
//	@Override
//	protected MethodSecurityExpressionHandler createExpressionHandler() {
//		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
//		expressionHandler.setPermissionEvaluator(permissionEvaluator);
//		return expressionHandler;
//	}
}
