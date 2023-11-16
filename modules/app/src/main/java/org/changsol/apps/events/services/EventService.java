package org.changsol.apps.events.services;

import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.changsol.apps.events.dtos.EventDto;
import org.changsol.congratulations.domains.EventRegistration;
import org.changsol.congratulations.domains.EventRegistrationRepository;
import org.changsol.exceptions.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {

	private final EventRegistrationRepository eventRegistrationRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 이벤트 추첨
	 */
	public List<EventDto.Response> raffle() {
		List<EventDto.Response> responses = Lists.newArrayList();
		for (EventRegistration eventRegistration : eventRegistrationRepository.findAllRaffle(5)) {

			responses.add(EventDto.Response.builder()
										   .id(eventRegistration.getId())
										   .name(eventRegistration.getName())
										   .phone(eventRegistration.getPhone())
										   .createdAt(eventRegistration.getCreatedAt())
										   .build());
		}
		return responses;
		// endregion
	}

	/**
	 * 이벤트 참여
	 */
	@Transactional
	public void create(EventDto.Create create) {
		if (eventRegistrationRepository.existsByPhone(create.getPhone())) {
			throw new BadRequestException("이미 참여된 휴대폰번호입니다.");
		}
		eventRegistrationRepository.save(EventRegistration.builder()
														  .name(create.getName())
														  .phone(create.getPhone())
														  .build());
	}
}
