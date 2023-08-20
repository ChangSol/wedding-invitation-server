package org.changsol.congratulations.domains;

import com.google.common.collect.Sets;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Congratulation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	@NotNull
	private String contents;

	/**
	 * 다른 애그리거트이므로 간접참조
	 */
	private Long memberId;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CongratulationLike> congratulationLikes = Sets.newHashSet();

	@Builder
	public Congratulation(String contents) {
		this.contents = Objects.requireNonNull(contents, "contents is required");
	}

	public void likeUp(Long memberId) {
		congratulationLikes.add(CongratulationLike.builder()
												  .congratulation(this)
												  .memberId(memberId)
												  .build());
	}

	public void likeDown(CongratulationLike congratulationLike) {
		congratulationLikes.remove(congratulationLike);
	}
}
