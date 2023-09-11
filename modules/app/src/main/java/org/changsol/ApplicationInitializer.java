package org.changsol;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.changsol.apps.members.services.MemberService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApplicationInitializer implements ApplicationRunner {
	private final MemberService memberService;
	@Override
	public void run(ApplicationArguments args) {
		final String START_COMPLETE = """
			
			================================================
			================================================
			================================================
			  ____ _                       ____        _
			 / ___| |__   __ _ _ __   __ _/ ___|  ___ | |
			| |   | '_ \\ / _` | '_ \\ / _` \\___ \\ / _ \\| |
			| |___| | | | (_| | | | | (_| |___) | (_) | |
			 \\____|_| |_|\\__,_|_| |_|\\__, |____/ \\___/|_|
			                         |___/
						
			================================================
			========== ModuleApp startup complete ==========
			================================================
			""";
		log.info(START_COMPLETE);

		memberService.addAdminMember();
	}

}
