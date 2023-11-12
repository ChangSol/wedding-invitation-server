package org.changsol.apps.members.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.changsol.apps.members.dtos.MemberDto;
import org.changsol.apps.members.dtos.TokenDto;
import org.changsol.apps.members.services.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "계정") // Swagger API 명 설정
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/member")
public class MemberController {

	private final MemberService memberService;

	@Operation(summary = "로그인")
	@PostMapping("/login")
	public ResponseEntity<TokenDto.Response> login(@RequestBody @Valid MemberDto.LoginRequest request) {
		return ResponseEntity.ok(memberService.login(request));
	}

	@Operation(summary = "로그아웃")
	@PostMapping("/logout")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public void logout() {
		memberService.logout();
	}
}
