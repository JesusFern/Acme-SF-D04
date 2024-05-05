
package acme.features.authenticated.codeAudit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditRecords.AuditRecord;
import acme.entities.codeAudits.CodeAudit;

@Repository
public interface AuthenticatedCodeAuditRepository extends AbstractRepository {

	@Query("select ca from CodeAudit ca where ca.id = :id")
	CodeAudit findOneCodeAuditById(int id);

	@Query("select ar from AuditRecord ar where ar.codeAudit.id = :codeAuditId")
	Collection<AuditRecord> findManyAuditRecordByCodeAuditId(int codeAuditId);

	@Query("select ca from CodeAudit ca where ca.draftMode = false")
	Collection<CodeAudit> findManyCodeAuditsByAvailability();

}
