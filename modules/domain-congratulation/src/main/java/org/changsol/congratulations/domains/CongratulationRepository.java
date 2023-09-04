package org.changsol.congratulations.domains;

import org.changsol.utils.bases.domains.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CongratulationRepository extends BaseRepository<Congratulation, Long> {

}
