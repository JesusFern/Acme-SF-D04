
package acme.features.client.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contracts.Contract;
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

		Collection<Contract> contracts;
		contracts = this.cdr.findManyContractByClientId(id);

		for (Contract contract : contracts) {
			Money quantity = contract.getBudget();
			this.convertToEuros(quantity);
		}
		Integer totalNumberOfProgressLogsCompletenessBelow25;
		Integer totalNumberOfProgressLogsCompletenessBetween25to50;
		Integer totalNumberOfProgressLogsCompletenessBetween50to75;
		Integer totalNumberOfProgressLogsCompletenessAbove75;

		Money averageContractsBudget = new Money();
		averageContractsBudget.setAmount(this.cdr.averageContractsBudget(id));
		averageContractsBudget.setCurrency("EUR");

		Money deviationContractsBudget = new Money();
		deviationContractsBudget.setAmount(this.cdr.deviationContractsBudget(id));
		deviationContractsBudget.setCurrency("EUR");

		Money minimumContractsBudget = new Money();
		minimumContractsBudget.setAmount(this.cdr.minimumContractsBudget(id));
		minimumContractsBudget.setCurrency("EUR");

		Money maximumContractsBudget = new Money();
		maximumContractsBudget.setAmount(this.cdr.maximumContractsBudget(id));
		maximumContractsBudget.setCurrency("EUR");

		totalNumberOfProgressLogsCompletenessBelow25 = this.cdr.totalNumberOfProgressLogsCompletenessBelow25(id);
		totalNumberOfProgressLogsCompletenessBetween25to50 = this.cdr.totalNumberOfProgressLogsCompletenessBetween25to50(id);
		totalNumberOfProgressLogsCompletenessBetween50to75 = this.cdr.totalNumberOfProgressLogsCompletenessBetween50to75(id);
		totalNumberOfProgressLogsCompletenessAbove75 = this.cdr.totalNumberOfProgressLogsCompletenessAbove75(id);

		dashboard = new ClientDashboard();
		dashboard.setTotalNumberOfProgressLogsCompletenessBelow25(totalNumberOfProgressLogsCompletenessBelow25);
		dashboard.setTotalNumberOfProgressLogsCompletenessBetween25to50(totalNumberOfProgressLogsCompletenessBetween25to50);
		dashboard.setTotalNumberOfProgressLogsCompletenessBetween50to75(totalNumberOfProgressLogsCompletenessBetween50to75);
		dashboard.setTotalNumberOfProgressLogsCompletenessAbove75(totalNumberOfProgressLogsCompletenessAbove75);
		dashboard.setAverageContractsBudget(averageContractsBudget);
		dashboard.setDeviationContractsBudget(deviationContractsBudget);
		dashboard.setMinimumContractsBudget(minimumContractsBudget);
		dashboard.setMaximumContractsBudget(maximumContractsBudget);

		super.getBuffer().addData(dashboard);
	}

	private Money convertToEuros(final Money money) {
		Double currentAmount = money.getAmount();
		String currentCurrency = money.getCurrency();

		if (!currentCurrency.equals("EUR")) {
			switch (currentCurrency) {
			case "USD":
				currentAmount *= 0.94;
				break;
			case "GBP":
				currentAmount *= 1.17;
				break;
			case "AUD":
				currentAmount *= 0.60;
				break;
			case "JPY":
				currentAmount *= 0.0061;
				break;
			case "CAD":
				currentAmount *= 0.68;
				break;
			case "MXN":
				currentAmount *= 0.055;
				break;
			case "CNY":
				currentAmount *= 0.13;
				break;
			default:
				return money;
			}
			money.setCurrency("EUR");
			money.setAmount(currentAmount);
		}
		return money;
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
