
package acme.features.manager.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Priority;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select count(u) from UserStory u where u.priority = :p and u.manager.id =:id")
	Integer totalNumberOfUserStoriesByPriority(int id, Priority p);

	@Query("select avg(u.estimatedCost) from UserStory u where u.manager.id =:id")
	Double averageEstimatedCostUserStories(int id);

	@Query("select stddev(u.estimatedCost) from UserStory u where u.manager.id =:id")
	Double deviationEstimatedCostUserStories(int id);

	@Query("select min(u.estimatedCost) from UserStory u where u.manager.id =:id")
	Integer minimumEstimatedCostUserStories(int id);

	@Query("select max(u.estimatedCost) from UserStory u where u.manager.id =:id")
	Integer maximumEstimatedCostUserStories(int id);

	@Query("select avg(p.cost) from Project p where p.manager.id =:id")
	Double averageProjectsCost(int id);

	@Query("select stddev(p.cost) from Project p where p.manager.id =:id")
	Double deviationProjectsCost(int id);

	@Query("select min(p.cost) from Project p where p.manager.id =:id")
	Integer minimumProjectsCost(int id);

	@Query("select max(p.cost) from Project p where p.manager.id =:id")
	Integer maximumProjectsCost(int id);

}
