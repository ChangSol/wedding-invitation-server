package org.changsol.congratulations.domains;

import java.util.List;
import org.changsol.utils.bases.domains.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRegistrationRepository extends BaseRepository<EventRegistration, Long> {

	@Query(value = "SELECT * FROM congratulation c ORDER BY random() LIMIT :limit", nativeQuery = true)
	List<EventRegistration> findAllRaffle(Integer limit);

	boolean existsByPhone(String phone);
}
