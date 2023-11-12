package org.changsol.members.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.changsol.utils.bases.domains.BaseDomainIdentity;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseDomainIdentity {
	public enum Type {
		ADMIN,
		USER
	}

	@NotNull
	@Column(unique = true)
	private String signName;

	@NotNull
	@Column(unique = true)
	private String phone;

	private String name;

	@NotNull
	private String password;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Type type;

	/**
	 * 휴대폰번호 마스킹 GET
	 */
	public String getPhoneMasking() {
		if (StringUtils.isBlank(this.phone)) {
			return null;
		}

		String phoneNumber = this.phone.replaceAll("[^0-9]", ""); // 숫자만 추출

		if (!(phoneNumber.length() == 10 || phoneNumber.length() == 11)) {
			return phoneNumber;
		}

		if (phoneNumber.length() == 10) {
			// 10자리인 경우. 000-***-0000로 지환
			return phoneNumber.substring(0, 3) + "-***-" + phoneNumber.substring(6, 10);
		} else {
			// 11자리인 경우. 000-****-0000로 지환
			return phoneNumber.substring(0, 3) + "-****-" + phoneNumber.substring(7, 11);
		}
	}
}
