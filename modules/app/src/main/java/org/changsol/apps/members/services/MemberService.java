package org.changsol.apps.members.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import net.sf.json.JSONObject;
import org.changsol.apps.members.dtos.MemberDto;
import org.changsol.apps.members.dtos.TokenDto;
import org.changsol.apps.properties.AppProperty;
import org.changsol.configs.WebClientUtils;
import org.changsol.exceptions.NotFoundException;
import org.changsol.members.domains.Member;
import org.changsol.members.domains.MemberRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final HttpServletRequest httpServletRequest;
	private final AppProperty appProperty;
	private final WebClient webClient;
	private final TokenStore tokenStore;

	/**
	 * 로그인
	 */
	public TokenDto.Response login(MemberDto.LoginRequest request) {
		// 예외처리
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Origin", httpServletRequest.getHeader("Origin"));
		headers.add("User-Agent", httpServletRequest.getHeader("User-Agent"));
		headers.add("X-Forwarded-For", httpServletRequest.getRemoteAddr());

		Map<String, Object> bodyMap = new HashMap<>();
		bodyMap.put("isLogin", true);
		bodyMap.put("grant_type", "password");
		bodyMap.put("username", request.getPhone());
		bodyMap.put("password", request.getPassword());
		bodyMap.put("client_id", appProperty.getOauth().getClientId());
		bodyMap.put("client_secret", appProperty.getOauth().getClientSecret());

		JSONObject jsonObject = WebClientUtils.retrievePostForMono(webClient,
																   appProperty.getOauthUrl(),
																   headers,
																   null,
																   bodyMap,
																   JSONObject.class).block();
		if (jsonObject == null) {
			throw new NotFoundException("token not found");
		}

		return new TokenDto.Response(String.valueOf(jsonObject.get("access_token")),
									 Long.valueOf(String.valueOf(jsonObject.get("expires_in"))),
									 String.valueOf(jsonObject.get("refresh_token")),
									 Long.parseLong(String.valueOf(jsonObject.get("expires_in"))) + 1000L);
	}

	public void logout() {
		// String accessToken = httpServletRequest.getHeader("authorization").substring(7);
		//
		// OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken);
		// if (oAuth2AccessToken == null) {
		// 	throw new InvalidTokenException("Invalid access token: " + accessToken);
		// }
		// OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(oAuth2AccessToken);
		// final String[] USER_NAME_SPLIT = oAuth2Authentication.getUserAuthentication().getName().split("\\|");
		// AccountType accountType = AccountType.valueOf(USER_NAME_SPLIT[0]);
		// String uid = USER_NAME_SPLIT[1];
		// String username = USER_NAME_SPLIT[2];
		//
		// redisService.refreshTokenDelete(redisService.getRedisKeyType(accountType), username, uid);
	}

	/**
	 * 관리자 계정 생성
	 */
	@Transactional
	public void addAdminMember() {
		if (!memberRepository.existsByPhone("01012341234")) {
			memberRepository.save(Member.builder()
										.signName(UUID.randomUUID().toString())
										.name("관리자")
										.password(passwordEncoder.encode("adminPassword!2")) // CHECK PWD
										.phone("01012341234")
										.build());
		}
	}

}
