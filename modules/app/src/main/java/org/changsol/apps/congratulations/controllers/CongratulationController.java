package org.changsol.apps.congratulations.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.changsol.apps.congratulations.dtos.CongratulationDto;
import org.changsol.apps.congratulations.services.CongratulationService;
import org.changsol.utils.PageUtils;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "축하글") // Swagger API 명 설정
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/congratulation")
public class CongratulationController {

	private final CongratulationService congratulationService;

	@Operation(summary = "축하글 목록 조회 (페이지)")
	@GetMapping
	@Secured("ROLE_USER")
	public ResponseEntity<PageUtils.Response<CongratulationDto.Response>> getCongratulationPage(@ParameterObject @Valid CongratulationDto.Request request) {
		return ResponseEntity.ok(congratulationService.getCongratulationPage(request));
	}

	@Operation(summary = "축하글 목록 조회 (no-offset)")
	@GetMapping("/no-offset")
	public ResponseEntity<List<CongratulationDto.Response>> getCongratulationNoOffset(@ParameterObject @Valid CongratulationDto.NoOffsetRequest request) {
		return ResponseEntity.ok(congratulationService.getCongratulationNoOffset(request));
	}

	@Operation(summary = "축하글 등록")
	@PostMapping
	public void addCongratulation(@RequestBody String contents) {
		congratulationService.addCongratulation(contents);
	}
}
