
package acme.entities.trainingModule;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TrainingSession extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Pattern(regexp = "^TS-[A-Z]{1,3}-[0-9]{3}$", message = "{validation.pattern} TS-ABC-000")
	@Column(unique = true)
	@NotBlank
	private String				code;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				startPeriod;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				endPeriod;

	@Length(max = 75)
	@NotBlank
	private String				location;

	@Length(max = 75)
	@NotBlank
	private String				instructor;

	@Email
	@NotBlank
	@Length(max = 255)
	private String				email;

	@URL
	@Length(max = 255)
	private String				link;

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	private TrainingModule		trainingModule;
}
