package org.changsol.congratulations.domains;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.changsol.utils.bases.domains.BaseDomainIdentity;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"congratulation_id", "memberId"}),
	}
)
public class CongratulationLike extends BaseDomainIdentity {

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Congratulation congratulation;

	/**
	 * 다른 애그리거트이므로 간접참조
	 */
	@NotNull
	private Long memberId;

	@Builder
	public CongratulationLike(Congratulation congratulation, Long memberId) {
		this.congratulation = Objects.requireNonNull(congratulation, "congratulation is required");
		this.memberId = Objects.requireNonNull(memberId, "memberId is required");
	}
}
