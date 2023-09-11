package org.changsol.apps.members.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class MemberDto {

	@Getter
	@Setter
	@Schema(title = "MemberDto LoginRequest")
	public static class LoginRequest {

		@NotBlank(message = "phone is required")
		@Schema(description = "휴대폰번호", requiredMode = Schema.RequiredMode.REQUIRED)
		private String phone;

		@NotBlank(message = "password is required")
		@Schema(description = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
		private String password;
	}
}
