
package acme.features.sponsor.dashboard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorDashboardRepository sdr;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		SponsorDashboard dashboard;

		int id;
		id = super.getRequest().getPrincipal().getActiveRoleId();

		Integer totalNumberOfInvoicesWithTaxLessOrEquals21;
		Integer totalNumberOfSponsorshipsLink;

		Money averageSponsorshipsAmountEUR = new Money();
		averageSponsorshipsAmountEUR.setAmount(this.sdr.averageSponsorshipsAmount(id, "EUR"));
		averageSponsorshipsAmountEUR.setCurrency("EUR");

		Money averageSponsorshipsAmountUSD = new Money();
		averageSponsorshipsAmountUSD.setAmount(this.sdr.averageSponsorshipsAmount(id, "USD"));
		averageSponsorshipsAmountUSD.setCurrency("USD");

		Money averageSponsorshipsAmountGBP = new Money();
		averageSponsorshipsAmountGBP.setAmount(this.sdr.averageSponsorshipsAmount(id, "GBP"));
		averageSponsorshipsAmountGBP.setCurrency("GBP");

		Money deviationSponsorshipsAmountEUR = new Money();
		deviationSponsorshipsAmountEUR.setAmount(this.sdr.deviationSponsorshipsAmount(id, "EUR"));
		deviationSponsorshipsAmountEUR.setCurrency("EUR");

		Money deviationSponsorshipsAmountUSD = new Money();
		deviationSponsorshipsAmountUSD.setAmount(this.sdr.deviationSponsorshipsAmount(id, "USD"));
		deviationSponsorshipsAmountUSD.setCurrency("USD");

		Money deviationSponsorshipsAmountGBP = new Money();
		deviationSponsorshipsAmountGBP.setAmount(this.sdr.deviationSponsorshipsAmount(id, "GBP"));
		deviationSponsorshipsAmountGBP.setCurrency("GBP");

		Money minimumSponsorshipsAmountEUR = new Money();
		minimumSponsorshipsAmountEUR.setAmount(this.sdr.minimumSponsorshipsAmount(id, "EUR"));
		minimumSponsorshipsAmountEUR.setCurrency("EUR");

		Money minimumSponsorshipsAmountUSD = new Money();
		minimumSponsorshipsAmountUSD.setAmount(this.sdr.minimumSponsorshipsAmount(id, "USD"));
		minimumSponsorshipsAmountUSD.setCurrency("USD");

		Money minimumSponsorshipsAmountGBP = new Money();
		minimumSponsorshipsAmountGBP.setAmount(this.sdr.minimumSponsorshipsAmount(id, "GBP"));
		minimumSponsorshipsAmountGBP.setCurrency("GBP");

		Money maximumSponsorshipsAmountEUR = new Money();
		maximumSponsorshipsAmountEUR.setAmount(this.sdr.maximumSponsorshipsAmount(id, "EUR"));
		maximumSponsorshipsAmountEUR.setCurrency("EUR");

		Money maximumSponsorshipsAmountUSD = new Money();
		maximumSponsorshipsAmountUSD.setAmount(this.sdr.maximumSponsorshipsAmount(id, "USD"));
		maximumSponsorshipsAmountUSD.setCurrency("USD");

		Money maximumSponsorshipsAmountGBP = new Money();
		maximumSponsorshipsAmountGBP.setAmount(this.sdr.maximumSponsorshipsAmount(id, "GBP"));
		maximumSponsorshipsAmountGBP.setCurrency("GBP");

		Money averageInvoicesQuantityEUR = new Money();
		averageInvoicesQuantityEUR.setAmount(this.sdr.averageInvoicesQuantity(id, "EUR"));
		averageInvoicesQuantityEUR.setCurrency("EUR");

		Money averageInvoicesQuantityUSD = new Money();
		averageInvoicesQuantityUSD.setAmount(this.sdr.averageInvoicesQuantity(id, "USD"));
		averageInvoicesQuantityUSD.setCurrency("USD");

		Money averageInvoicesQuantityGBP = new Money();
		averageInvoicesQuantityGBP.setAmount(this.sdr.averageInvoicesQuantity(id, "GBP"));
		averageInvoicesQuantityGBP.setCurrency("GBP");

		Money deviationInvoicesQuantityEUR = new Money();
		deviationInvoicesQuantityEUR.setAmount(this.sdr.deviationInvoicesQuantity(id, "EUR"));
		deviationInvoicesQuantityEUR.setCurrency("EUR");

		Money deviationInvoicesQuantityUSD = new Money();
		deviationInvoicesQuantityUSD.setAmount(this.sdr.deviationInvoicesQuantity(id, "USD"));
		deviationInvoicesQuantityUSD.setCurrency("USD");

		Money deviationInvoicesQuantityGBP = new Money();
		deviationInvoicesQuantityGBP.setAmount(this.sdr.deviationInvoicesQuantity(id, "GBP"));
		deviationInvoicesQuantityGBP.setCurrency("GBP");

		Money minimumInvoicesQuantityEUR = new Money();
		minimumInvoicesQuantityEUR.setAmount(this.sdr.minimumInvoicesQuantity(id, "EUR"));
		minimumInvoicesQuantityEUR.setCurrency("EUR");

		Money minimumInvoicesQuantityUSD = new Money();
		minimumInvoicesQuantityUSD.setAmount(this.sdr.minimumInvoicesQuantity(id, "USD"));
		minimumInvoicesQuantityUSD.setCurrency("USD");

		Money minimumInvoicesQuantityGBP = new Money();
		minimumInvoicesQuantityGBP.setAmount(this.sdr.minimumInvoicesQuantity(id, "GBP"));
		minimumInvoicesQuantityGBP.setCurrency("GBP");

		Money maximumInvoicesQuantityEUR = new Money();
		maximumInvoicesQuantityEUR.setAmount(this.sdr.maximumInvoicesQuantity(id, "EUR"));
		maximumInvoicesQuantityEUR.setCurrency("EUR");

		Money maximumInvoicesQuantityUSD = new Money();
		maximumInvoicesQuantityUSD.setAmount(this.sdr.maximumInvoicesQuantity(id, "USD"));
		maximumInvoicesQuantityUSD.setCurrency("USD");

		Money maximumInvoicesQuantityGBP = new Money();
		maximumInvoicesQuantityGBP.setAmount(this.sdr.maximumInvoicesQuantity(id, "GBP"));
		maximumInvoicesQuantityGBP.setCurrency("GBP");

		totalNumberOfInvoicesWithTaxLessOrEquals21 = this.sdr.totalNumberOfInvoicesWithTaxLessOrEquals21(id);
		totalNumberOfSponsorshipsLink = this.sdr.totalNumberOfSponsorshipsLink(id);

		dashboard = new SponsorDashboard();
		dashboard.setTotalNumberOfInvoicesWithTaxLessOrEquals21(0);
		dashboard.setTotalNumberOfSponsorshipsLink(0);
		Map<String, Double> maximumSponsorshipMap = new HashMap<>();
		Map<String, Double> minimumSponsorshipMap = new HashMap<>();
		Map<String, Double> averageSponsorshipMap = new HashMap<>();
		Map<String, Double> deviationSponsorshipMap = new HashMap<>();
		Map<String, Double> maximumInvoiceMap = new HashMap<>();
		Map<String, Double> minimumInvoiceMap = new HashMap<>();
		Map<String, Double> averageInvoiceMap = new HashMap<>();
		Map<String, Double> deviationInvoiceMap = new HashMap<>();

		if (averageSponsorshipsAmountEUR.getAmount() != null || averageSponsorshipsAmountUSD.getAmount() != null || averageSponsorshipsAmountGBP.getAmount() != null) {
			dashboard.setTotalNumberOfInvoicesWithTaxLessOrEquals21(totalNumberOfInvoicesWithTaxLessOrEquals21);
			dashboard.setTotalNumberOfSponsorshipsLink(totalNumberOfSponsorshipsLink);

			averageSponsorshipMap.put(averageSponsorshipsAmountEUR.getCurrency(), averageSponsorshipsAmountEUR.getAmount());
			averageSponsorshipMap.put(averageSponsorshipsAmountUSD.getCurrency(), averageSponsorshipsAmountUSD.getAmount());
			averageSponsorshipMap.put(averageSponsorshipsAmountGBP.getCurrency(), averageSponsorshipsAmountGBP.getAmount());
			dashboard.setAverageSponsorshipsAmount(averageSponsorshipMap);

			deviationSponsorshipMap.put(deviationSponsorshipsAmountEUR.getCurrency(), deviationSponsorshipsAmountEUR.getAmount());
			deviationSponsorshipMap.put(deviationSponsorshipsAmountUSD.getCurrency(), deviationSponsorshipsAmountUSD.getAmount());
			deviationSponsorshipMap.put(deviationSponsorshipsAmountGBP.getCurrency(), deviationSponsorshipsAmountGBP.getAmount());
			dashboard.setDeviationSponsorshipsAmount(deviationSponsorshipMap);

			minimumSponsorshipMap.put(minimumSponsorshipsAmountEUR.getCurrency(), minimumSponsorshipsAmountEUR.getAmount());
			minimumSponsorshipMap.put(minimumSponsorshipsAmountUSD.getCurrency(), minimumSponsorshipsAmountUSD.getAmount());
			minimumSponsorshipMap.put(minimumSponsorshipsAmountGBP.getCurrency(), minimumSponsorshipsAmountGBP.getAmount());
			dashboard.setMinimumSponsorshipsAmount(minimumSponsorshipMap);

			maximumSponsorshipMap.put(maximumSponsorshipsAmountEUR.getCurrency(), maximumSponsorshipsAmountEUR.getAmount());
			maximumSponsorshipMap.put(maximumSponsorshipsAmountEUR.getCurrency(), maximumSponsorshipsAmountEUR.getAmount());
			maximumSponsorshipMap.put(maximumSponsorshipsAmountEUR.getCurrency(), maximumSponsorshipsAmountEUR.getAmount());
			dashboard.setMaximumSponsorshipsAmount(maximumSponsorshipMap);

			averageInvoiceMap.put(averageInvoicesQuantityEUR.getCurrency(), averageInvoicesQuantityEUR.getAmount());
			averageInvoiceMap.put(averageInvoicesQuantityUSD.getCurrency(), averageInvoicesQuantityUSD.getAmount());
			averageInvoiceMap.put(averageInvoicesQuantityGBP.getCurrency(), averageInvoicesQuantityGBP.getAmount());
			dashboard.setAverageInvoicesQuantity(averageInvoiceMap);

			deviationInvoiceMap.put(deviationInvoicesQuantityEUR.getCurrency(), deviationInvoicesQuantityEUR.getAmount());
			deviationInvoiceMap.put(deviationInvoicesQuantityUSD.getCurrency(), deviationInvoicesQuantityUSD.getAmount());
			deviationInvoiceMap.put(deviationInvoicesQuantityGBP.getCurrency(), deviationInvoicesQuantityGBP.getAmount());
			dashboard.setDeviationInvoicesQuantity(deviationInvoiceMap);

			minimumInvoiceMap.put(minimumInvoicesQuantityEUR.getCurrency(), minimumInvoicesQuantityEUR.getAmount());
			minimumInvoiceMap.put(minimumInvoicesQuantityUSD.getCurrency(), minimumInvoicesQuantityUSD.getAmount());
			minimumInvoiceMap.put(minimumInvoicesQuantityGBP.getCurrency(), minimumInvoicesQuantityGBP.getAmount());
			dashboard.setMinimumInvoicesQuantity(minimumInvoiceMap);

			maximumInvoiceMap.put(maximumInvoicesQuantityEUR.getCurrency(), maximumInvoicesQuantityEUR.getAmount());
			maximumInvoiceMap.put(maximumInvoicesQuantityEUR.getCurrency(), maximumInvoicesQuantityEUR.getAmount());
			maximumInvoiceMap.put(maximumInvoicesQuantityEUR.getCurrency(), maximumInvoicesQuantityEUR.getAmount());
			dashboard.setMaximumInvoicesQuantity(maximumInvoiceMap);
		}
		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalNumberOfInvoicesWithTaxLessOrEquals21", //
			"totalNumberOfSponsorshipsLink", //
			"averageSponsorshipsAmount", // 
			"deviationSponsorshipsAmount", //
			"minimumSponsorshipsAmount", //
			"maximumSponsorshipsAmount", //
			"averageInvoicesQuantity", // 
			"deviationInvoicesQuantity", //
			"minimumInvoicesQuantity", //
			"maximumInvoicesQuantity");

		super.getResponse().addData(dataset);
	}
}
