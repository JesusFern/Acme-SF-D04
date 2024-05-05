
package acme.features.manager.projectUserStory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.entities.projects.UserStory;

@Repository
public interface ManagerProjectUserStoryRepository extends AbstractRepository {

	@Query("select pu from ProjectUserStory pu where pu.project.manager.id = :managerId")
	Collection<ProjectUserStory> findManyProjectUserStoriesByManagerId(int managerId);

	@Query("SELECT u FROM UserStory u " + "WHERE u.manager.id = :managerId " + "AND u NOT IN (SELECT pu.userStory FROM ProjectUserStory pu WHERE pu.project.id = :projectId)")
	Collection<UserStory> findManyUserStoriesByManagerIdNotInProjectId(int managerId, int projectId);

	@Query("select u from UserStory u where u.manager.id = :managerId")
	Collection<UserStory> findManyUserStoriesByManagerId(int managerId);

	@Query("select pu from ProjectUserStory pu where pu.project.id = :projectId")
	Collection<ProjectUserStory> findManyProjectUserStoriesByProjectId(int projectId);

	@Query("select pu from ProjectUserStory pu where pu.project.id = :projectId and pu.userStory.id= :userStoryId")
	ProjectUserStory findOneProjectUserStoriesByProjectAndUserStoryId(int projectId, int userStoryId);

	@Query("select pu from ProjectUserStory pu where pu.id = :id")
	ProjectUserStory findOneProjectUserStoryById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select u from UserStory u where u.id = :id")
	UserStory findOneUserStoryById(int id);

	@Query("select p from Project p where p.manager.id = :managerId")
	Collection<Project> findManyProjectsByManagerId(int managerId);

}
