
package acme.features.authenticated.auditRecord;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.auditRecords.AuditRecord;

@Controller
public class AuthenticatedAuditRecordController extends AbstractController<Authenticated, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedAuditRecordListMineService	listMineService;

	@Autowired
	private AuthenticatedAuditRecordShowService		showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listMineService);
		super.addBasicCommand("show", this.showService);
	}
}
