
package acme.entities.codeAudits;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.auditRecords.AuditRecord;
import acme.entities.auditRecords.Mark;
import acme.entities.projects.Project;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "draftMode"), @Index(columnList = "code"), @Index(columnList = "type, draftMode, auditor_id")
})
public class CodeAudit extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$", message = "{validation.pattern}  {1,3}XXX-000 ")
	private String				code;

	@NotNull
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date				execution;

	@NotNull
	private Type				type;

	private boolean				draftMode;

	@NotBlank
	@Length(max = 100)
	private String				correctiveActions;

	@URL
	@Length(min = 7, max = 255)
	private String				link;


	// Derived attributes ----------------------------------------------------------
	@Transient
	public Mark getMark(final Collection<AuditRecord> records) {
		List<Mark> ORDER = Arrays.asList(Mark.A_PLUS, Mark.A, Mark.B, Mark.C, Mark.F, Mark.F_MINUS);
		Map<Mark, Integer> frequencyMap = new HashMap<>();
		for (AuditRecord arecords : records) {
			Mark mark = arecords.getMark();
			frequencyMap.put(mark, frequencyMap.getOrDefault(mark, 0) + 1);
		}

		Mark mode = null;
		int maxFrequency = 0;

		for (Map.Entry<Mark, Integer> entry : frequencyMap.entrySet())
			if (entry.getValue() > maxFrequency) {
				maxFrequency = entry.getValue();
				mode = entry.getKey();
			} else if (entry.getValue() == maxFrequency)
				if (ORDER.indexOf(entry.getKey()) > ORDER.indexOf(mode))
					mode = entry.getKey();

		return mode;
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project	project;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Auditor	auditor;
}
