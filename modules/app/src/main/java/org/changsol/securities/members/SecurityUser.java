package org.changsol.securities.members;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.changsol.apps.members.dtos.PrincipalDto;
import org.changsol.apps.members.dtos.PrincipalDtoMapper;
import org.changsol.members.domains.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {

	private final PrincipalDto.Response response;

	public SecurityUser(Member member) {
		super(member.getSignName(), member.getPassword(), getAuthorities(member));
		this.response = PrincipalDtoMapper.INSTANCE.toResponse(member);
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(Member member) {
		Set<String> authorities = Sets.newHashSet();
		authorities.add("ROLE_USER");
		if (member.getName().equals("관리자")) {
			authorities.add("ROLE_ADMIN");
		}
		return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	public PrincipalDto.Response getPrincipal() {
		return this.response;
	}
}
