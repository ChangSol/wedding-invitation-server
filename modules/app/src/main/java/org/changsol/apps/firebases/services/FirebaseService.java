package org.changsol.apps.firebases.services;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.changsol.apps.firebases.dtos.FirebaseDto;
import org.changsol.exceptions.NotFoundException;
import org.changsol.firebases.domains.FirebaseAuth;
import org.changsol.firebases.domains.FirebaseAuthRepository;
import org.changsol.firebases.domains.FirebaseConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FirebaseService {

	private final FirebaseAuthRepository firebaseAuthRepository;

	/**
	 * Firebase Get Config
	 */
	public FirebaseDto.ConfigResponse getConfig() {
		FirebaseDto.ConfigResponse configResponse = new FirebaseDto.ConfigResponse();
		firebaseAuthRepository.findTop1ByDateAndCountLessThanEqualOrderByFirebaseConfigAsc(LocalDate.now(), 49)
							  .ifPresentOrElse(firebaseAuth -> {
								  FirebaseConfig firebaseConfig = firebaseAuth.getFirebaseConfig();
								  configResponse.setAppId(firebaseConfig.getAppId());
								  configResponse.setApiKey(firebaseConfig.getApiKey());
								  configResponse.setMessagingSenderId(firebaseConfig.getMessagingSenderId());
								  configResponse.setAuthDomain(firebaseConfig.getAuthDomain());
								  configResponse.setStorageBucket(firebaseConfig.getStorageBucket());
								  configResponse.setProjectId(firebaseConfig.getProjectId());
							  }, () -> {
								  throw new NotFoundException("사용할 수 있는 firebase config가 존재하지 않습니다.");
							  });
		return configResponse;
	}

	/**
	 * Firebase Auth CountUp
	 */
	@Transactional
	public void countUp(String projectId) {
		firebaseAuthRepository.findByDateAndFirebaseConfigId(LocalDate.now(), projectId)
							  .ifPresentOrElse(FirebaseAuth::countUp, () -> {
								  throw new NotFoundException("firebase config가 존재하지 않습니다.");
							  });
	}
}