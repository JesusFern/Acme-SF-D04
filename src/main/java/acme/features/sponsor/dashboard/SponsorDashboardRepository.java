
package acme.features.sponsor.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("select count(i) from Invoice i where i.tax <= 21.00 and i.sponsorship.sponsor.id = :id and i.sponsorship.draftMode = false")
	Integer totalNumberOfInvoicesWithTaxLessOrEquals21(int id);

	@Query("select count(s) from Sponsorship s where s.link is not null and s.sponsor.id = :id and s.draftMode = false")
	Integer totalNumberOfSponsorshipsLink(int id);

	@Query("select avg(s.amount.amount) from Sponsorship s where s.sponsor.id = :id and s.draftMode = false")
	Double averageSponsorshipsAmount(int id);

	@Query("select stddev(s.amount.amount) from Sponsorship s where s.sponsor.id = :id and s.draftMode = false")
	Double deviationSponsorshipsAmount(int id);

	@Query("select min(s.amount.amount) from Sponsorship s where s.sponsor.id = :id and s.draftMode = false")
	Double minimumSponsorshipsAmount(int id);

	@Query("select max(s.amount.amount) from Sponsorship s where s.sponsor.id = :id and s.draftMode = false")
	Double maximumSponsorshipsAmount(int id);

	@Query("select s.amount.currency from Sponsorship s where s.sponsor.id = :id and s.draftMode = false")
	String findCurrentCurrencyAmount(int id);

	@Query("select s from Sponsorship s where s.sponsor.id = :id and s.draftMode = false")
	Collection<Sponsorship> findManySponsorshipsBySponsorId(int id);

	@Query("select i from Invoice i where i.sponsorship.sponsor.id = :id and i.sponsorship.draftMode = false")
	Collection<Invoice> findManyInvoicesBySponsorId(int id);

	@Query("select i.quantity.currency from Invoice i where i.sponsorship.sponsor.id = :id and i.sponsorship.draftMode = false")
	String findCurrentCurrencyQuantity(int id);

	@Query("select avg(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :id and i.sponsorship.draftMode = false")
	Double averageInvoicesQuantity(int id);

	@Query("select stddev(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :id and i.sponsorship.draftMode = false")
	Double deviationInvoicesQuantity(int id);

	@Query("select min(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :id and i.sponsorship.draftMode = false")
	Double minimumInvoicesQuantity(int id);

	@Query("select max(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :id and i.sponsorship.draftMode = false")
	Double maximumInvoicesQuantity(int id);
}
