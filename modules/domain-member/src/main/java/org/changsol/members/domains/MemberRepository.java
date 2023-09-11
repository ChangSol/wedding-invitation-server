package org.changsol.members.domains;

import java.util.List;
import java.util.Optional;
import org.changsol.utils.bases.domains.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends BaseRepository<Member, Long> {
	Optional<Member> findByPhone(String phone);
	Optional<Member> findBySignName(String signName);
	List<Member> findAllByIdIn(List<Long> ids);
	boolean existsByPhone(String phone);
}
