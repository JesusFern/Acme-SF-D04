
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Integer				totalNumberAuditors;
	private Integer				totalNumberManagers;
	private Integer				totalNumberDevelopers;
	private Integer				totalNumberSponsors;
	private Integer				totalNumberClients;

	private Double				ratioNoticesWithEmailAndLink;
	private Double				ratioCriticalObjectives;
	private Double				ratioNonCriticalObjectives;
	private Double				averageRisksValues;
	private Double				minimumRisksValues;
	private Double				maximumRisksValues;
	private Double				standardDeviationRisksValues;

	private Integer				numberClaimsLast10Weeks;

}
