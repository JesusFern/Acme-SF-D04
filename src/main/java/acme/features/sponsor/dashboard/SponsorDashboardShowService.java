
package acme.features.sponsor.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
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

		Collection<Sponsorship> sponsorships;
		sponsorships = this.sdr.findManySponsorshipsBySponsorId(id);

		for (Sponsorship sponsorship : sponsorships) {
			Money amount = sponsorship.getAmount();
			this.convertToEuros(amount);
		}

		Collection<Invoice> invoices;
		invoices = this.sdr.findManyInvoicesBySponsorId(id);

		for (Invoice invoice : invoices) {
			Money quantity = invoice.getQuantity();
			this.convertToEuros(quantity);
		}

		Integer totalNumberOfInvoicesWithTaxLessOrEquals21;
		Integer totalNumberOfSponsorshipsLink;

		Money averageSponsorshipsAmount = new Money();
		averageSponsorshipsAmount.setAmount(this.sdr.averageSponsorshipsAmount(id));
		averageSponsorshipsAmount.setCurrency("EUR");

		Money deviationSponsorshipsAmount = new Money();
		deviationSponsorshipsAmount.setAmount(this.sdr.deviationSponsorshipsAmount(id));
		deviationSponsorshipsAmount.setCurrency("EUR");

		Money minimumSponsorshipsAmount = new Money();
		minimumSponsorshipsAmount.setAmount(this.sdr.minimumSponsorshipsAmount(id));
		minimumSponsorshipsAmount.setCurrency("EUR");

		Money maximumSponsorshipsAmount = new Money();
		maximumSponsorshipsAmount.setAmount(this.sdr.maximumSponsorshipsAmount(id));
		maximumSponsorshipsAmount.setCurrency("EUR");

		Money averageInvoicesQuantity = new Money();
		averageInvoicesQuantity.setAmount(this.sdr.averageInvoicesQuantity(id));
		averageInvoicesQuantity.setCurrency("EUR");

		Money deviationInvoicesQuantity = new Money();
		deviationInvoicesQuantity.setAmount(this.sdr.deviationInvoicesQuantity(id));
		deviationInvoicesQuantity.setCurrency("EUR");

		Money minimumInvoicesQuantity = new Money();
		minimumInvoicesQuantity.setAmount(this.sdr.minimumInvoicesQuantity(id));
		minimumInvoicesQuantity.setCurrency("EUR");

		Money maximumInvoicesQuantity = new Money();
		maximumInvoicesQuantity.setAmount(this.sdr.maximumInvoicesQuantity(id));
		maximumInvoicesQuantity.setCurrency("EUR");

		totalNumberOfInvoicesWithTaxLessOrEquals21 = this.sdr.totalNumberOfInvoicesWithTaxLessOrEquals21(id);
		totalNumberOfSponsorshipsLink = this.sdr.totalNumberOfSponsorshipsLink(id);

		dashboard = new SponsorDashboard();
		dashboard.setTotalNumberOfInvoicesWithTaxLessOrEquals21(totalNumberOfInvoicesWithTaxLessOrEquals21);
		dashboard.setTotalNumberOfSponsorshipsLink(totalNumberOfSponsorshipsLink);
		dashboard.setAverageSponsorshipsAmount(averageSponsorshipsAmount);
		dashboard.setDeviationSponsorshipsAmount(deviationSponsorshipsAmount);
		dashboard.setMinimumSponsorshipsAmount(minimumSponsorshipsAmount);
		dashboard.setMaximumSponsorshipsAmount(maximumSponsorshipsAmount);
		dashboard.setAverageInvoicesQuantity(averageInvoicesQuantity);
		dashboard.setDeviationInvoicesQuantity(deviationInvoicesQuantity);
		dashboard.setMinimumInvoicesQuantity(minimumInvoicesQuantity);
		dashboard.setMaximumInvoicesQuantity(maximumInvoicesQuantity);

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
