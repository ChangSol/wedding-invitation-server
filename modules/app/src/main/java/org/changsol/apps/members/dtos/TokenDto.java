package org.changsol.apps.members.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class TokenDto {

	@Getter
	@Setter
	@Schema(title = "TokenDto Request")
	public static class Request {

		@NotBlank(message = "accessToken is required")
		@Schema(description = "액세스토큰")
		private String accessToken;

		@NotBlank(message = "refreshToken is required")
		@Schema(description = "리프레시토큰")
		private String refreshToken;
	}

	@Getter
	@Setter
	@Schema(title = "TokenDto Response")
	public static class Response {
		public Response(String accessToken, Long accessExpiresIn, String refreshToken, Long refreshExpiresIn) {
			this.accessToken = accessToken;
			this.accessExpiresIn = accessExpiresIn;
			this.refreshToken = refreshToken;
			this.refreshExpiresIn = refreshExpiresIn;
		}

		@Schema(description = "액세스토큰")
		private String accessToken;

		@Schema(description = "액세스토큰 만료까지 남은시간(초)")
		private Long accessExpiresIn;

		@Schema(description = "리프레시토큰")
		private String refreshToken;

		@Schema(description = "리프레시토큰 만료까지 남은시간(초)")
		private Long refreshExpiresIn;
	}
}
