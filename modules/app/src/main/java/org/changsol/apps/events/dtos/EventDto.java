package org.changsol.apps.events.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

public class EventDto {

	@Getter
	@Builder
	@Schema(title = "EventDto Response", name = "EventDto Response")
	public static class Response {

		@Schema(description = "고유번호")
		private Long id;

		@Schema(description = "이름")
		private String name;

		@Schema(description = "휴대폰번호")
		private String phone;

		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+9")
		@Schema(description = "작성일시")
		private LocalDateTime createdAt;
	}

	@Getter
	@Builder
	@Schema(title = "EventDto Create", name = "EventDto Create")
	public static class Create {

		@NotBlank(message = "이름을 입력해 주세요.")
		@Schema(description = "이름")
		private String name;

		@NotBlank(message = "휴대폰번호를 입력해 주세요.")
		@Schema(description = "휴대폰번호")
		@Pattern(regexp = "^((010\\d{4})|(070\\d{4})|(01[1|6|7|8|9]\\d{3,4}))(\\d{4})$")
		private String phone;
	}
}

