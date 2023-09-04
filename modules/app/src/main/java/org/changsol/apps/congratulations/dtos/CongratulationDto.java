package org.changsol.apps.congratulations.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.changsol.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

public class CongratulationDto {

	@Getter
	@Setter
	@Schema(title = "CongratulationDto Request")
	public static class Request extends PageUtils.Request {
		@Schema(description = "축하글 작성자 휴대폰번호")
		private String createdByPhone;

		@DateTimeFormat(pattern = "yyyy-MM-dd")
		@Schema(description = "작성일시 시작일")
		private LocalDate startDt;

		@DateTimeFormat(pattern = "yyyy-MM-dd")
		@Schema(description = "작성일시 종료일")
		private LocalDate endDt;
	}

	@Getter
	@Builder
	@Schema(title = "CongratulationDto Response")
	public static class Response {
		@Schema(description = "고유번호")
		private Long id;

		@Schema(description = "축하글")
		private String contents;

		@Schema(description = "축하글 작성자 휴대폰번호")
		private String createdByPhone;

		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+9")
		@Schema(description = "작성일시")
		private LocalDateTime createdAt;
	}
}

