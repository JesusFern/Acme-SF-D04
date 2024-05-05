
package acme.entities.sponsorships;

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
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.projects.Project;
import acme.roles.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsorship extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$", message = "{validation.pattern}  AUE-000")
	@Column(unique = true)
	private String				code;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	@NotNull
	private Date				moment;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				startSponsor;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				endSponsor;

	private boolean				draftMode;

	@NotNull
	private Money				amount;

	@NotNull
	private Type				type;

	@Email
	@Length(max = 255)
	private String				email;

	@URL
	@Length(max = 255)
	private String				link;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project				project;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Sponsor				sponsor;
}
