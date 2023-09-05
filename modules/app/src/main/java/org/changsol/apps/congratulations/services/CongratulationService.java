package org.changsol.apps.congratulations.services;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.changsol.apps.congratulations.dtos.CongratulationDto;
import org.changsol.congratulations.domains.Congratulation;
import org.changsol.congratulations.domains.CongratulationRepository;
import org.changsol.members.domains.Member;
import org.changsol.members.domains.MemberRepository;
import org.changsol.utils.PageUtils;
import org.changsol.utils.jpas.restrictions.JpaRestriction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CongratulationService {

	private final CongratulationRepository congratulationRepository;
	private final MemberRepository memberRepository;

	/**
	 * 축하글 목록 조회 (페이지)
	 */
	public PageUtils.Response<CongratulationDto.Response> getCongratulationPage(CongratulationDto.Request request) {
		// region sort
		List<Sort.Order> sorts = Lists.newArrayList();

		if (StringUtils.isNotBlank(request.getSortColumn()) && request.getSortType() != null) {
			switch (request.getSortType()) {
				case ASC -> sorts.add(Sort.Order.asc(request.getSortColumn()));
				case DESC -> sorts.add(Sort.Order.desc(request.getSortColumn()));
			}
		}

		if (CollectionUtils.isEmpty(sorts) || sorts.stream().noneMatch(x -> x.getProperty().equals("id"))) {
			sorts.add(Sort.Order.desc("id"));
		}
		Sort sort = Sort.by(sorts);
		// endregion

		// region where
		JpaRestriction r = new JpaRestriction();

		if (StringUtils.isNotBlank(request.getCreatedByPhone())) {
			memberRepository.findByPhone(request.getCreatedByPhone())
							.ifPresent(member -> r.equals("memberId", member.getId()));
		}

		if (request.getStartDt() != null) {
			r.greaterThanEquals("createdAt", request.getStartDt().atTime(0, 0, 0));
		}

		if (request.getEndDt() != null) {
			r.lessThanNotEquals("createdAt", request.getEndDt().atTime(0, 0, 0));
		}
		// endregion

		// region select
		PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit(), sort);
		Page<Congratulation> congratulationPage = congratulationRepository.findAll(r.toSpecification(), pageRequest);

		List<Long> memberIds = congratulationPage.map(Congratulation::getMemberId).toList();
		List<Member> members = CollectionUtils.isEmpty(memberIds) ? Lists.newArrayList() : memberRepository.findAllByIdIn(memberIds);
		List<CongratulationDto.Response> responses = Lists.newArrayList();
		for (Congratulation congratulation : congratulationPage) {
			Member member = members.stream()
								   .filter(x -> x.getId().equals(congratulation.getId()))
								   .findAny()
								   .orElse(null);
			String createdByPhone = member == null ? null : member.getPhoneMasking();
			responses.add(CongratulationDto.Response.builder()
													.id(congratulation.getId())
													.contents(congratulation.getContents())
													.createdAt(congratulation.getCreatedAt())
													.createdByPhone(createdByPhone)
													.build());
		}
		return PageUtils.valueOf(congratulationPage, responses);
		// endregion
	}

	/**
	 * 축하글 목록 조회 (no-offset)
	 */
	public List<CongratulationDto.Response> getCongratulationNoOffset(CongratulationDto.NoOffsetRequest request) {
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

		List<Long> memberIds = congratulationPage.map(Congratulation::getMemberId).toList();
		List<Member> members = CollectionUtils.isEmpty(memberIds) ? Lists.newArrayList() : memberRepository.findAllByIdIn(memberIds);
		List<CongratulationDto.Response> responses = Lists.newArrayList();
		for (Congratulation congratulation : congratulationPage) {
			Member member = members.stream()
								   .filter(x -> x.getId().equals(congratulation.getId()))
								   .findAny()
								   .orElse(null);
			String createdByPhone = member == null ? null : member.getPhoneMasking();
			responses.add(CongratulationDto.Response.builder()
													.id(congratulation.getId())
													.contents(congratulation.getContents())
													.createdAt(congratulation.getCreatedAt())
													.createdByPhone(createdByPhone)
													.build());
		}
		return responses;
		// endregion
	}

	/**
	 * 축하글 등록
	 */
	@Transactional
	public void addCongratulation(String contents) {
		congratulationRepository.save(Congratulation.builder()
													.contents(contents)
													.build());
	}
}
