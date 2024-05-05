
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						totalNumberOfMustUserStories;
	Integer						totalNumberOfShouldUserStories;
	Integer						totalNumberOfCouldUserStories;
	Integer						totalNumberOfWontUserStories;

	Double						averageEstimatedCostUserStories;
	Double						deviationEstimatedCostUserStories;
	Integer						minimumEstimatedCostUserStories;
	Integer						maximumEstimatedCostUserStories;

	Double						averageProjectsCost;
	Double						deviationProjectsCost;
	Integer						minimumProjectsCost;
	Integer						maximumProjectsCost;

}
