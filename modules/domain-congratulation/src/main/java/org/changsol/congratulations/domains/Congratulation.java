package org.changsol.congratulations.domains;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.changsol.utils.bases.domains.BaseDomainLog;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Congratulation extends BaseDomainLog {

	@Column(length = 2000)
	@NotNull
	private String contents;

	@Column(length = 10)
	@NotNull
	private String name;

	@NotNull
	private String password;

	@ColumnDefault("0")
	@NotNull
	private Integer likeCount;

	@Builder
	public Congratulation(String contents, String name, String password) {
		this.contents = Objects.requireNonNull(contents, "contents is required");
		this.name = Objects.requireNonNull(name, "name is required");
		this.password = Objects.requireNonNull(password, "password is required");
		this.likeCount = 0;
	}

	public void likeUp() {
		this.likeCount++;
	}

	public boolean passwordCheck(PasswordEncoder passwordEncoder, String password) {
		return passwordEncoder.matches(password, this.password);
	}
}
