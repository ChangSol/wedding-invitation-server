package org.changsol.congratulations.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.changsol.utils.bases.domains.BaseDomainLog;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class EventRegistration extends BaseDomainLog {

	@Column(length = 10)
	@NotNull
	private String name;

	@Column(length = 11, unique = true)
	@NotNull
	private String phone;
}
