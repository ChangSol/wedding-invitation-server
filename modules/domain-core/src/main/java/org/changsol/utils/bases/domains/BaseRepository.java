package org.changsol.utils.bases.domains;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<DOMAIN, ID_TYPE> extends JpaRepository<DOMAIN, ID_TYPE>, JpaSpecificationExecutor<DOMAIN> {

}
