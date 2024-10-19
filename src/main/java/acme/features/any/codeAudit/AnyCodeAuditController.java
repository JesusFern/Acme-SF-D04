
package acme.features.any.codeAudit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.codeAudits.CodeAudit;

@Controller
public class AnyCodeAuditController extends AbstractController<Any, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyCodeAuditListAllService	listAllService;

	@Autowired
	private AnyCodeAuditShowService		showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addCustomCommand("list-all", "list", this.listAllService);
	}
}
