
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	int							totalNumberOfProgressLogsCompletenessBelow25;
	int							totalNumberOfProgressLogsCompletenessBetween25to50;
	int							totalNumberOfProgressLogsCompletenessBetween50to75;
	int							totalNumberOfProgressLogsCompletenessAbove75;

	Map<String, Double>			averageContractsBudget;
	Map<String, Double>			deviationContractsBudget;
	Map<String, Double>			minimumContractsBudget;
	Map<String, Double>			maximumContractsBudget;

}
