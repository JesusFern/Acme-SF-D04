
package acme.forms;

import acme.client.data.AbstractForm;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	Integer						totalNumberOfProgressLogsCompletenessBelow25;
	Integer						totalNumberOfProgressLogsCompletenessBetween25to50;
	Integer						totalNumberOfProgressLogsCompletenessBetween50to75;
	Integer						totalNumberOfProgressLogsCompletenessAbove75;

	Money						averageContractsBudget;
	Money						deviationContractsBudget;
	Money						minimumContractsBudget;
	Money						maximumContractsBudget;

}
