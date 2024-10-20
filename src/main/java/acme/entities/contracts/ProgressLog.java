
package acme.entities.contracts;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "draftMode, contract_id"), @Index(columnList = "recordId"), @Index(columnList = "percentageCompleteness")
})
public class ProgressLog extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	// Attributes
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^PG-[A-Z]{1,2}-[0-9]{4}$", message = "{validation.pattern}  PG-A{1,2}-1111")

	private String				recordId;

	@Digits(integer = 3, fraction = 2)
	@Max(100)
	@Min(0)
	private double				percentageCompleteness;

	@Length(max = 100)
	@NotBlank
	private String				comment;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	@NotNull
	private Date				registrationMoment;

	private boolean				draftMode;

	@NotBlank
	@Length(max = 75)
	private String				responsiblePerson;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Contract			contract;

}
