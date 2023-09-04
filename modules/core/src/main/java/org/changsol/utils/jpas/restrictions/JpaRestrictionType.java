package org.changsol.utils.jpas.restrictions;

/**
 * Predicate WHERE 절 AND, OR TYPE
 */
enum JpaRestrictionType {
	AND("AND"),
	OR("OR");

	public final String title;

	JpaRestrictionType(String title) {
		this.title = title;
	}
}
