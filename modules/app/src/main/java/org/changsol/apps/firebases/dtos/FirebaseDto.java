package org.changsol.apps.firebases.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class FirebaseDto {

	@Getter
	@Setter
	@Schema(title = "FirebaseDto ConfigResponse", name = "FirebaseDto ConfigResponse")
	public static class ConfigResponse {

		private String projectId;

		private String apiKey;

		private String authDomain;

		private String storageBucket;

		private String messagingSenderId;

		private String appId;
	}
}

