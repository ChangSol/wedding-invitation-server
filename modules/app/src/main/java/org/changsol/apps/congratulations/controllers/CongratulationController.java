package org.changsol.apps.congratulations.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.changsol.congratulations.services.CongratulationDomainService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "축하글 API") // Swagger API 명 설정
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/congratulation")
public class CongratulationController {

	private final CongratulationDomainService congratulationDomainService;

	// private final CongratulationRepository congratulationRepository;

	@Operation(summary = "축하글 등록")
	@PostMapping
	public void addCongratulation(@RequestBody String contents) {
		congratulationDomainService.addCongratulation(contents);
	}
}
