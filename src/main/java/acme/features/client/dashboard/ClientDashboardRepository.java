
package acme.features.client.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contracts.Contract;

@Repository
public interface ClientDashboardRepository extends AbstractRepository {

	@Query("select count(p) from ProgressLog p where p.percentageCompleteness <=25.00 and p.contract.client.id = :id and p.contract.draftMode = false")
	Integer totalNumberOfProgressLogsCompletenessBelow25(int id);

	@Query("select count(p) from ProgressLog p where p.percentageCompleteness >=25.00 and p.percentageCompleteness <=50.00  and p.contract.client.id = :id and p.contract.draftMode = false")
	Integer totalNumberOfProgressLogsCompletenessBetween25to50(int id);

	@Query("select count(p) from ProgressLog p where p.percentageCompleteness >=50.00 and p.percentageCompleteness <=75.00  and p.contract.client.id = :id and p.contract.draftMode = false")
	Integer totalNumberOfProgressLogsCompletenessBetween50to75(int id);

	@Query("select count(p) from ProgressLog p where p.percentageCompleteness >=75.00  and p.contract.client.id = :id and p.contract.draftMode = false")
	Integer totalNumberOfProgressLogsCompletenessAbove75(int id);

	@Query("select avg(c.budget.amount) from Contract c where c.client.id = :id and c.draftMode = false")
	Double averageContractsBudget(int id);

	@Query("select stddev(c.budget.amount) from Contract c where c.client.id = :id and c.draftMode = false")
	Double deviationContractsBudget(int id);

	@Query("select min(c.budget.amount) from Contract c where c.client.id = :id and c.draftMode = false")
	Double minimumContractsBudget(int id);

	@Query("select max(c.budget.amount) from Contract c where c.client.id = :id and c.draftMode = false")
	Double maximumContractsBudget(int id);

	@Query("select c from Contract c where c.client.id = :id")
	Collection<Contract> findManyContractByClientId(int id);

}
