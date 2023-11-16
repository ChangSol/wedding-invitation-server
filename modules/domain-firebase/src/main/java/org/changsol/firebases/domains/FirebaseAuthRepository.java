package org.changsol.firebases.domains;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.changsol.utils.bases.domains.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FirebaseAuthRepository extends BaseRepository<FirebaseAuth, Long> {

	Optional<FirebaseAuth> findTop1ByDateAndCountLessThanEqualOrderByFirebaseConfigAsc(LocalDate date, Integer count);
	Optional<FirebaseAuth> findByDateAndFirebaseConfigId(LocalDate date, String projectId);
}
