package org.changsol.apps.congratulations.services;

import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.changsol.apps.congratulations.dtos.CongratulationDto;
import org.changsol.congratulations.domains.Congratulation;
import org.changsol.congratulations.domains.CongratulationRepository;
import org.changsol.exceptions.BadRequestException;
import org.changsol.exceptions.NotFoundException;
import org.changsol.members.domains.Member;
import org.changsol.members.domains.MemberRepository;
import org.changsol.utils.PageUtils;
import org.changsol.utils.jpas.restrictions.JpaRestriction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CongratulationService {

	private final CongratulationRepository congratulationRepository;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 축하글 목록 조회 (no-offset)
	 */
	public List<CongratulationDto.Response> getNoOffset(CongratulationDto.NoOffsetRequest request) {
		// region sort
		Sort sort = null;
		switch (request.getSortType()) {
			case NEW -> sort = Sort.by(Sort.Direction.DESC, "id");
			case OLD -> sort = Sort.by(Sort.Direction.ASC, "id");
		}
		// endregion

		// region where
		JpaRestriction r = new JpaRestriction();

		if (request.getLastId() != null) {
			switch (request.getSortType()) {
				case NEW -> r.lessThanNotEquals("id", request.getLastId());
				case OLD -> r.greaterThanNotEquals("id", request.getLastId());
			}
		}
		// endregion

		// region select
		PageRequest pageRequest = PageRequest.of(0, request.getLimit(), sort);
		Page<Congratulation> congratulationPage = congratulationRepository.findAll(r.toSpecification(), pageRequest);

		List<CongratulationDto.Response> responses = Lists.newArrayList();
		for (Congratulation congratulation : congratulationPage) {

			responses.add(CongratulationDto.Response.builder()
													.id(congratulation.getId())
													.name(congratulation.getName())
													.contents(congratulation.getContents())
													.createdAt(congratulation.getCreatedAt())
													.build());
		}
		return responses;
		// endregion
	}

	/**
	 * 축하글 등록
	 */
	@Transactional
	public void create(@RequestBody @Valid CongratulationDto.Create create) {
		congratulationRepository.save(Congratulation.builder()
													.name(create.getName())
													.contents(create.getContents())
													.password(passwordEncoder.encode(create.getPassword()))
													.build());
	}

	/**
	 * 축하글 삭제
	 */
	@Transactional
	public void delete(Long id, String password) {
		Congratulation congratulation = congratulationRepository.findById(id).orElseThrow(() -> new NotFoundException("축하글이 존재하지 않습니다."));
		if (!congratulation.passwordCheck(passwordEncoder, password)) {
			throw new BadRequestException("비밀번호가 맞지 않습니다.");
		}
		congratulationRepository.delete(congratulation);
	}

	/**
	 * 축하글 삭제
	 */
	@Transactional
	public void delete(Long id) {
		Congratulation congratulation = congratulationRepository.findById(id).orElseThrow(() -> new NotFoundException("축하글이 존재하지 않습니다."));
		congratulationRepository.delete(congratulation);
	}
}
