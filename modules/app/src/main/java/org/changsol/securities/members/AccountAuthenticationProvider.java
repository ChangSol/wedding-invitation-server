package org.changsol.securities.members;

import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import net.sf.json.JSONObject;
import org.changsol.exceptions.BadRequestException;
import org.changsol.exceptions.ConflictException;
import org.changsol.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountAuthenticationProvider implements AuthenticationProvider {

	@Qualifier("UserDetailsService")
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) {
		try {

			HashMap<String, Object> details = (HashMap<String, Object>) authentication.getDetails();

			String username = authentication.getName();
			String password = (String) authentication.getCredentials();
			boolean isLogin = true;

			JSONObject jsonObject = new JSONObject();

			if (details.containsKey("isLogin")) {
				isLogin = Boolean.parseBoolean(details.get("isLogin").toString());
			}

			jsonObject.put("username", username);
			jsonObject.put("isLogin", isLogin);

			SecurityUser user = (SecurityUser) userDetailsService.loadUserByUsername(jsonObject.toString());

			// 패스워드 확인
			if (isLogin && !passwordEncoder.matches(password, user.getPassword())) {
				throw new BadRequestException("비밀번호가 맞지 않습니다.");
			}

			return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
		} catch (BadRequestException | ForbiddenException | ConflictException ex) {
			throw new UsernameNotFoundException(ex.getMessage());
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
