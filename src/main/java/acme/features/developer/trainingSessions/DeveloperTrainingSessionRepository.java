
package acme.features.developer.trainingSessions;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingModule.TrainingSession;

@Repository
public interface DeveloperTrainingSessionRepository extends AbstractRepository {

	@Query("select t from TrainingSession t where t.id = :id")
	TrainingSession findOneTrainingSessionById(int id);

	@Query("select t from TrainingSession t where t.trainingModule.id = :id")
	Collection<TrainingSession> findAllTrainingSessionsByTrainingModuleId(int id);

	@Query("select t from TrainingSession t")
	Collection<TrainingSession> findAllTrainingSessions();

	@Query("select t from TrainingModule t")
	Collection<TrainingModule> findAllTrainingModules();

	@Query("select t from TrainingSession t where t.trainingModule.developer.id = :id")
	Collection<TrainingSession> findAllTrainingSessionsByDeveloperId(int id);

	@Query("select t from TrainingModule t where t.id = :id")
	TrainingModule findOneTrainingModuleById(int id);

	@Query("select ts.trainingModule from TrainingSession ts where ts.id = :id")
	TrainingModule findOneTrainingModuleByTrainingSessionId(int id);

	@Query("select t from TrainingSession t where t.code = :code")
	TrainingSession findOneTrainingSessionByCode(String code);

}
