package org.changsol.firebases.domains;

import javax.persistence.Entity;
import javax.persistence.Id;
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
public class FirebaseConfig extends BaseDomain {

	@Id
	private String projectId;

	@NotNull
	private String apiKey;

	@NotNull
	private String authDomain;

	@NotNull
	private String storageBucket;

	@NotNull
	private String messagingSenderId;

	@NotNull
	private String appId;
}
