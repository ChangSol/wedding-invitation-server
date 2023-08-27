package org.changsol.congratulations.services;

import lombok.RequiredArgsConstructor;
import org.changsol.congratulations.domains.Congratulation;
import org.changsol.congratulations.domains.CongratulationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CongratulationDomainService {

	private final CongratulationRepository congratulationRepository;

	@Transactional
	public void addCongratulation(String contents) {
		congratulationRepository.save(Congratulation.builder()
													.contents(contents)
													.build());

	}
}
