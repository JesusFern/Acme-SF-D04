
package acme.features.authenticated.codeAudit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.codeAudits.CodeAudit;

@Controller
public class AuthenticatedCodeAuditController extends AbstractController<Authenticated, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedCodeAuditListAllService	listAllService;

	@Autowired
	private AuthenticatedCodeAuditShowService		showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addCustomCommand("list-all", "list", this.listAllService);
	}
}
