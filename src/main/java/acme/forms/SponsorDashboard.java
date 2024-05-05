
package acme.forms;

import acme.client.data.AbstractForm;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						totalNumberOfInvoicesWithTaxLessOrEquals21;
	Integer						totalNumberOfSponsorshipsLink;

	Money						averageSponsorshipsAmount;
	Money						deviationSponsorshipsAmount;
	Money						minimumSponsorshipsAmount;
	Money						maximumSponsorshipsAmount;

	Money						averageInvoicesQuantity;
	Money						deviationInvoicesQuantity;
	Money						minimumInvoicesQuantity;
	Money						maximumInvoicesQuantity;

}
