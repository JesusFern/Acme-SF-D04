
package acme.features.any.trainingModule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.trainingModule.TrainingModule;

@Repository
public interface AnyTrainingModuleRepository extends AbstractRepository {

	@Query("select t from TrainingModule t where t.id = :id")
	TrainingModule findOneTrainingModuleById(int id);

	@Query("select t from TrainingModule t where t.draftMode = false")
	Collection<TrainingModule> findAllTrainingModulesWithoutDraftMode();

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findManyProjectsByAvailability();
}
