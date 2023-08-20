package org.changsol.congratulations.domains;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CongratulationRepository extends JpaRepository<Congratulation, Long> {

}
