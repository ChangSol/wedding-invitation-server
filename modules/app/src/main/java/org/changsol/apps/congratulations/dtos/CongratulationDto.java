package org.changsol.apps.congratulations.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.changsol.utils.PageUtils;
import org.springframework.format.annotation.DateTimeFormat;

public class CongratulationDto {

	@Getter
	@Setter
	@Schema(title = "CongratulationDto_Request", name = "CongratulationDto_Request")
	public static class Request extends PageUtils.Request {
		// @Schema(description = "축하글 작성자 휴대폰번호")
		// private String createdByPhone;

		@DateTimeFormat(pattern = "yyyy-MM-dd")
		@Schema(description = "작성일시 시작일")
		private LocalDate startDt;

		@DateTimeFormat(pattern = "yyyy-MM-dd")
		@Schema(description = "작성일시 종료일")
		private LocalDate endDt;
	}

	@Getter
	@Setter
	@Schema(title = "CongratulationDto NoOffsetRequest", name = "CongratulationDto NoOffsetRequest")
	public static class NoOffsetRequest {
		@Schema(title = "SortType")
		public enum SortType {
			NEW, // 최신순
			OLD // 오래된순
		}

		@NotNull(message = "Please enter page limit number")
		@Min(value = 1, message = "페이지 당 갯수는 최소 1개 이상이어야 합니다.")
		@Max(value = 1000, message = "페이지 당 갯수는 최대 1000개 이하이어야 합니다.")
		@Schema(description = "한 페이지 당 갯수", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
		private Integer limit = 10;

		@Schema(description = "마지막 고유번호")
		private Long lastId;

		@NotNull(message = "Please enter sortType")
		@Schema(description = "정렬 타입 (NEW,OLD)")
		private SortType sortType;
	}

	@Getter
	@Builder
	@Schema(title = "CongratulationDto Response", name = "CongratulationDto Response")
	public static class Response {
		@Schema(description = "고유번호")
		private Long id;

		@Schema(description = "이름")
		private String name;

		@Schema(description = "내용")
		private String contents;

		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+9")
		@Schema(description = "작성일시")
		private LocalDateTime createdAt;
	}

	@Getter
	@Builder
	@Schema(title = "CongratulationDto Create", name = "CongratulationDto Create")
	public static class Create {

		@NotBlank(message = "이름을 입력해 주세요.")
		@Schema(description = "이름")
		private String name;

		@NotBlank(message = "내용을 입력해 주세요.")
		@Schema(description = "내용")
		private String contents;

		@NotBlank(message = "비밀번호를 입력해 주세요.")
		@Schema(description = "비밀번호")
		private String password;
	}
}

