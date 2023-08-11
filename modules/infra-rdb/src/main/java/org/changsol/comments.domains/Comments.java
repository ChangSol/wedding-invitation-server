package org.changsol.comments.domains;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;

@Entity
@SecondaryTable(
	name = "comments_detail",
	pkJoinColumns = @PrimaryKeyJoinColumn(name = "comments_id", referencedColumnName = "id")
)
public class Comments {
	@Id
	private Long id;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "test", column = @Column(table = "comments_detail"))
	})
	private CommentsDetail commentsDetail;
}
