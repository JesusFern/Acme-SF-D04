
package acme.features.client.dashboard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.ClientDashboard;
import acme.roles.Client;

@Service
public class ClientDashboardShowService extends AbstractService<Client, ClientDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientDashboardRepository cdr;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}
	@Override
	public void load() {
		ClientDashboard dashboard;

		int id;
		id = super.getRequest().getPrincipal().getActiveRoleId();

		Integer totalNumberOfProgressLogsCompletenessBelow25;
		Integer totalNumberOfProgressLogsCompletenessBetween25to50;
		Integer totalNumberOfProgressLogsCompletenessBetween50to75;
		Integer totalNumberOfProgressLogsCompletenessAbove75;

		//DEVIATION
		Money deviationContractsBudgetEUR = new Money();
		deviationContractsBudgetEUR.setAmount(this.cdr.deviationContractsBudget(id, "EUR"));
		deviationContractsBudgetEUR.setCurrency("EUR");

		Money deviationContractsBudgetUSD = new Money();
		deviationContractsBudgetUSD.setAmount(this.cdr.deviationContractsBudget(id, "USD"));
		deviationContractsBudgetUSD.setCurrency("USD");

		Money deviationContractsBudgetGBP = new Money();
		deviationContractsBudgetGBP.setAmount(this.cdr.deviationContractsBudget(id, "GBP"));
		deviationContractsBudgetGBP.setCurrency("GBP");

		//AVERAGE
		Money averageContractsBudgetEUR = new Money();
		averageContractsBudgetEUR.setAmount(this.cdr.averageContractsBudget(id, "EUR"));
		averageContractsBudgetEUR.setCurrency("EUR");

		Money averageContractsBudgetUSD = new Money();
		averageContractsBudgetUSD.setAmount(this.cdr.averageContractsBudget(id, "USD"));
		averageContractsBudgetUSD.setCurrency("USD");

		Money averageContractsBudgetGBP = new Money();
		averageContractsBudgetGBP.setAmount(this.cdr.averageContractsBudget(id, "GBP"));
		averageContractsBudgetGBP.setCurrency("GBP");

		//MINIMUM
		Money minimumContractsBudgetEUR = new Money();
		minimumContractsBudgetEUR.setAmount(this.cdr.minimumContractsBudget(id, "EUR"));
		minimumContractsBudgetEUR.setCurrency("EUR");

		Money minimumContractsBudgetUSD = new Money();
		minimumContractsBudgetUSD.setAmount(this.cdr.minimumContractsBudget(id, "USD"));
		minimumContractsBudgetUSD.setCurrency("USD");

		Money minimumContractsBudgetGBP = new Money();
		minimumContractsBudgetGBP.setAmount(this.cdr.minimumContractsBudget(id, "GBP"));
		minimumContractsBudgetGBP.setCurrency("GBP");

		//MAXIMUM
		Money maximumContractsBudgetEUR = new Money();
		maximumContractsBudgetEUR.setAmount(this.cdr.maximumContractsBudget(id, "EUR"));
		maximumContractsBudgetEUR.setCurrency("EUR");

		Money maximumContractsBudgetUSD = new Money();
		maximumContractsBudgetUSD.setAmount(this.cdr.maximumContractsBudget(id, "USD"));
		maximumContractsBudgetUSD.setCurrency("USD");

		Money maximumContractsBudgetGBP = new Money();
		maximumContractsBudgetGBP.setAmount(this.cdr.maximumContractsBudget(id, "GBP"));
		maximumContractsBudgetGBP.setCurrency("GBP");

		totalNumberOfProgressLogsCompletenessBelow25 = this.cdr.totalNumberOfProgressLogsCompletenessBelow25(id);
		totalNumberOfProgressLogsCompletenessBetween25to50 = this.cdr.totalNumberOfProgressLogsCompletenessBetween25to50(id);
		totalNumberOfProgressLogsCompletenessBetween50to75 = this.cdr.totalNumberOfProgressLogsCompletenessBetween50to75(id);
		totalNumberOfProgressLogsCompletenessAbove75 = this.cdr.totalNumberOfProgressLogsCompletenessAbove75(id);

		dashboard = new ClientDashboard();
		Map<String, Double> maximumMap = new HashMap<>();
		Map<String, Double> minimumMap = new HashMap<>();
		Map<String, Double> averageMap = new HashMap<>();
		Map<String, Double> deviationMap = new HashMap<>();
		dashboard.setAverageContractsBudget(null);
		dashboard.setDeviationContractsBudget(null);
		dashboard.setMaximumContractsBudget(null);
		dashboard.setMinimumContractsBudget(null);
		dashboard.setTotalNumberOfProgressLogsCompletenessAbove75(0);
		dashboard.setTotalNumberOfProgressLogsCompletenessBelow25(0);
		dashboard.setTotalNumberOfProgressLogsCompletenessBetween25to50(0);
		dashboard.setTotalNumberOfProgressLogsCompletenessBetween50to75(0);

		if (minimumContractsBudgetEUR != null || minimumContractsBudgetUSD != null || minimumContractsBudgetGBP != null) {
			dashboard.setTotalNumberOfProgressLogsCompletenessBelow25(totalNumberOfProgressLogsCompletenessBelow25);
			dashboard.setTotalNumberOfProgressLogsCompletenessBetween25to50(totalNumberOfProgressLogsCompletenessBetween25to50);
			dashboard.setTotalNumberOfProgressLogsCompletenessBetween50to75(totalNumberOfProgressLogsCompletenessBetween50to75);
			dashboard.setTotalNumberOfProgressLogsCompletenessAbove75(totalNumberOfProgressLogsCompletenessAbove75);
			averageMap.put(averageContractsBudgetEUR.getCurrency(), averageContractsBudgetEUR.getAmount());
			averageMap.put(averageContractsBudgetUSD.getCurrency(), averageContractsBudgetUSD.getAmount());
			averageMap.put(averageContractsBudgetGBP.getCurrency(), averageContractsBudgetGBP.getAmount());
			dashboard.setAverageContractsBudget(averageMap);
			deviationMap.put(deviationContractsBudgetEUR.getCurrency(), deviationContractsBudgetEUR.getAmount());
			deviationMap.put(deviationContractsBudgetUSD.getCurrency(), deviationContractsBudgetUSD.getAmount());
			deviationMap.put(deviationContractsBudgetGBP.getCurrency(), deviationContractsBudgetGBP.getAmount());
			dashboard.setDeviationContractsBudget(deviationMap);
			minimumMap.put(minimumContractsBudgetEUR.getCurrency(), minimumContractsBudgetEUR.getAmount());
			minimumMap.put(minimumContractsBudgetUSD.getCurrency(), minimumContractsBudgetUSD.getAmount());
			minimumMap.put(minimumContractsBudgetGBP.getCurrency(), minimumContractsBudgetGBP.getAmount());
			dashboard.setMinimumContractsBudget(minimumMap);
			maximumMap.put(maximumContractsBudgetEUR.getCurrency(), maximumContractsBudgetEUR.getAmount());
			maximumMap.put(maximumContractsBudgetUSD.getCurrency(), maximumContractsBudgetUSD.getAmount());
			maximumMap.put(maximumContractsBudgetGBP.getCurrency(), maximumContractsBudgetGBP.getAmount());
			dashboard.setMaximumContractsBudget(maximumMap);

		}
		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ClientDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalNumberOfProgressLogsCompletenessBelow25", //
			"totalNumberOfProgressLogsCompletenessBetween25to50", //
			"totalNumberOfProgressLogsCompletenessBetween50to75", // 
			"totalNumberOfProgressLogsCompletenessAbove75", //
			"averageContractsBudget", //
			"deviationContractsBudget", //
			"minimumContractsBudget", //
			"maximumContractsBudget");

		super.getResponse().addData(dataset);
	}

}
