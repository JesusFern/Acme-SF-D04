
package acme.features.authenticated.contract;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.contracts.Contract;

@Controller
public class AuthenticatedContractController extends AbstractController<Authenticated, Contract> {

	@Autowired
	private AuthenticatedContractListAllService	listAllService;

	@Autowired
	private AuthenticatedContractShowService	listShowService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-all", "list", this.listAllService);
		super.addBasicCommand("show", this.listShowService);
	}

}
