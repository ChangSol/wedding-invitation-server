package org.changsol.configs;

import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.changsol.members.domains.Member;
import org.changsol.members.domains.MemberRepository;
import org.changsol.securities.members.SecurityUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class JpaAuditConfig {

	private final MemberRepository memberRepository;

	@Bean
	public AuditorAwareSimpleImpl auditorProviderSimple() {
		return new AuditorAwareSimpleImpl();
	}

	public class AuditorAwareSimpleImpl implements AuditorAware<Long> {

		@Override
		public @NonNull Optional<Long> getCurrentAuditor() {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (null == authentication || !authentication.isAuthenticated()) {
				return Optional.empty();
			}

			if (authentication.getPrincipal() instanceof SecurityUser) {
				Member member = memberRepository.findById(((SecurityUser) authentication.getPrincipal()).getPrincipal().getId()).orElse(null);
				return member == null ? Optional.empty() : Optional.ofNullable(member.getId());
			} else {
				return Optional.empty();
			}
		}
	}
}
