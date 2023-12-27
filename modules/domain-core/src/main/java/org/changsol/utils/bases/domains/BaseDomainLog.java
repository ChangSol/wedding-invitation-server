package org.changsol.utils.bases.domains;

import java.time.LocalDateTime;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseDomainLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	private String remoteAddr;

	private String userAgent;

	// @Column(columnDefinition = "TEXT")
	@Lob
	private String origin;

	// @Column(columnDefinition = "TEXT")
	@Lob
	private String requestUri;

	private String method;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@PrePersist
	public void prePersist() {
		HttpServletRequest request = Optional.ofNullable(RequestContextHolder.getRequestAttributes())
											 .filter(ServletRequestAttributes.class::isInstance)
											 .map(ServletRequestAttributes.class::cast)
											 .map(ServletRequestAttributes::getRequest).orElse(null);
		if (request != null) {
			this.remoteAddr = request.getRemoteAddr();
			this.userAgent = request.getHeader("User-Agent");
			this.origin = request.getHeader("Origin");
			this.requestUri = request.getRequestURI();
			this.method = request.getMethod();
		}
	}
}