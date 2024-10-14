
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.projects.Project;
import acme.roles.Client;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "draftMode"), @Index(columnList = "code"), @Index(columnList = "draftMode ,  project_id"), @Index(columnList = "budget_currency ,  draftMode, client_id")

})
public class Contract extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	// Attributes
	@NotBlank
	@Column(unique = true)

	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$", message = "{validation.pattern}  AA{1,3}-111")

	private String				code;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	@NotNull
	private Date				instantiationMoment;

	@Length(max = 75)
	@NotBlank
	private String				providerName;

	@Length(max = 75)
	@NotBlank
	private String				customerName;

	@Length(max = 100)
	@NotBlank
	private String				goals;

	@NotNull
	private Money				budget;

	private boolean				draftMode;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project				project;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Client				client;

}
