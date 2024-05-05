
package acme.features.developer.dashboards;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface DeveloperDashboardRepository extends AbstractRepository {

	@Query("select count(t) from TrainingModule t where t.updateMoment is not null and t.developer.id = :id and t.draftMode = false")
	Integer totalNumberOfTrainingModulesWithUpdatedMoment(int id);

	@Query("select count(t) from TrainingSession t where t.link is not null and t.trainingModule.developer.id = :id")
	Integer totalNumberOfTrainingSessionsWithLink(int id);

	@Query("select avg(t.time) from TrainingModule t where t.developer.id = :id and t.draftMode = false")
	Double averageTimeTrainingModules(int id);

	@Query("select stddev(t.time) from TrainingModule t where t.developer.id = :id and t.draftMode = false")
	Double deviationTimeTrainingModules(int id);

	@Query("select min(t.time) from TrainingModule t where t.developer.id = :id and t.draftMode = false")
	Integer minimumTimeTrainingModules(int id);

	@Query("select max(t.time) from TrainingModule t where t.developer.id = :id and t.draftMode = false")
	Integer maximumTimeTrainingModules(int id);
}
