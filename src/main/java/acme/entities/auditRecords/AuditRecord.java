
package acme.entities.auditRecords;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.codeAudits.CodeAudit;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "id"), @Index(columnList = "code")
})
public class AuditRecord extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^AU-[0-9]{4}-[0-9]{3}$", message = "{validation.pattern}  AU-0000-000")
	private String				code;

	@NotNull
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date				periodStart;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				periodEnd;

	@NotNull
	private Mark				mark;

	@URL
	@Length(min = 7, max = 255)
	private String				link;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private CodeAudit			codeAudit;

}
