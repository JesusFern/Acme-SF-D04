
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contracts.Contract;
import acme.entities.contracts.ProgressLog;
import acme.entities.projects.Project;
import acme.roles.Client;

@Repository
public interface ClientContractRepository extends AbstractRepository {

	@Query("select c from Contract c where c.id = :id")
	Contract findOneContractById(int id);

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findManyProjectsByAvailability();

	@Query("select p from Project p")
	Collection<Project> findManyProjects();

	@Query("select c from Client c where c.id = :id")
	Client findOneClientById(int id);

	@Query("select c from Contract c where c.code = :code")
	Contract findOneContractByCode(String code);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select p from Project p where p.id not in (select c.project.id from Contract c where c.client.id = :clientId)")
	Collection<Project> findManyAvailableProjectByClientId(int clientId);

	@Query("select pl from ProgressLog pl where pl.contract.id = :contractId")
	Collection<ProgressLog> findManyProgressLogByContractId(int contractId);

	@Query("select c from Contract c where c.client.id = :clientId")
	Collection<Contract> findManyContractsByClientId(int clientId);

	@Query("select c from Contract c where c.project.id = :projectId")
	Collection<Contract> findManyContractsByProjectId(int projectId);

	@Query("select c from Contract c where  c.draftMode = false  and c.project.id = :projectId")
	Collection<Contract> findManyContractsAvailableByProjectId(int projectId);

	@Query("select c from Contract c where c.draftMode = false")
	Collection<Contract> findManyContractByAvailability();

}
