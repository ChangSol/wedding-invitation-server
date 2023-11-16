package org.changsol.apps.firebases.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.changsol.apps.congratulations.dtos.CongratulationDto;
import org.changsol.apps.congratulations.services.CongratulationService;
import org.changsol.apps.firebases.dtos.FirebaseDto;
import org.changsol.apps.firebases.services.FirebaseService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "firebase") // Swagger API 명 설정
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/firebase")
public class FirebaseController {

	private final FirebaseService firebaseService;

	@Operation(summary = "SMS 사용 Config 조회")
	@GetMapping("/config/sms")
	public ResponseEntity<FirebaseDto.ConfigResponse> getConfig() {
		return ResponseEntity.ok(firebaseService.getConfig());
	}

	@Operation(summary = "SMS 사용 Count Up")
	@PutMapping("/sms/count")
	public void getConfig(@RequestParam String projectId) {
		firebaseService.countUp(projectId);
	}
}
