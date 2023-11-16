package org.changsol.firebases.domains;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.changsol.utils.bases.domains.BaseDomain;
import org.changsol.utils.bases.domains.BaseDomainIdentity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {"date, firebaseConfig"})
})
public class FirebaseAuth extends BaseDomain {

	@NotNull
	@Column(name = "dates")
	private LocalDate date;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private FirebaseConfig firebaseConfig;

	@NotNull
	private Integer count;

	public void countUp() {
		this.count++;
	}
}
