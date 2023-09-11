package org.changsol.securities.members;

import lombok.RequiredArgsConstructor;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.changsol.members.domains.Member;
import org.changsol.members.domains.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("UserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		// 로그인 여부
		boolean isLogin = false;
		String username;

		try {
			JSONObject jsonObject = JSONObject.fromObject(arg0);
			if (jsonObject.containsKey("isLogin")) {
				isLogin = (Boolean) jsonObject.get("isLogin");
			}
			username = jsonObject.get("username").toString();
		} catch (JSONException ex) {
			username = arg0;
		}

		Member member;
		if (isLogin) {
			member = memberRepository.findByPhone(username).orElseThrow(() -> new UsernameNotFoundException("계정이 존재하지 않습니다."));
		} else {
			member = memberRepository.findBySignName(username).orElseThrow(() -> new UsernameNotFoundException("계정이 존재하지 않습니다."));
		}
		return new SecurityUser(member);
	}
}
