
package acme.features.any.contract;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contracts.Contract;
import acme.entities.projects.Project;

@Repository
public interface AnyContractRepository extends AbstractRepository {

	@Query("select c from Contract c where c.draftMode = false")
	Collection<Contract> findManyContractByAvailability();

	@Query("select c from Contract c where c.id = :id")
	Contract findOneContractById(int id);

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findManyProjectsByAvailability();

}
