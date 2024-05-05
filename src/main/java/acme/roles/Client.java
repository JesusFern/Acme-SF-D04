
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Client extends AbstractRole {
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	@NotBlank
	@Unique
	@Pattern(regexp = "^CLI-[0-9]{4}$", message = "{validation.pattern}  CLI-1111")

	private String				identification;

	@NotBlank
	@Size(max = 75)
	private String				companyName;

	@NotNull
	private Types				type;

	@NotBlank
	@Email
	private String				email;

	@URL
	@Length(max = 255)
	private String				optionalLink;

}
