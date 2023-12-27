package org.changsol.apps.events.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.changsol.apps.congratulations.dtos.CongratulationDto;
import org.changsol.apps.congratulations.services.CongratulationService;
import org.changsol.apps.events.dtos.EventDto;
import org.changsol.apps.events.services.EventService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "축하글") // Swagger API 명 설정
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/event")
public class EventController {

	// private final EventService eventService;
	//
	// @Operation(summary = "이벤트 추첨")
	// @Secured("ROLE_ADMIN")
	// @GetMapping("/raffle")
	// public ResponseEntity<List<EventDto.Response>> raffle() {
	// 	return ResponseEntity.ok(eventService.raffle());
	// }
	//
	// @Operation(summary = "이벤트 참여")
	// @PostMapping
	// public void create(@RequestBody @Valid EventDto.Create create) {
	// 	eventService.create(create);
	// }
}
