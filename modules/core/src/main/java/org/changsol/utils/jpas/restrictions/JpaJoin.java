package org.changsol.utils.jpas.restrictions;

import javax.persistence.criteria.JoinType;

/**
 * JPA Restriction Join Record Class
 */
record JpaJoin(String columnName, JoinType joinType) {

}
