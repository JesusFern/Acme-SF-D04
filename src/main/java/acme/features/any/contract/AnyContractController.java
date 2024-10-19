
package acme.features.any.contract;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.contracts.Contract;

@Controller
public class AnyContractController extends AbstractController<Any, Contract> {

	@Autowired
	private AnyContractListAllService	listAllService;

	@Autowired
	private AnyContractShowService		listShowService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-all", "list", this.listAllService);
		super.addBasicCommand("show", this.listShowService);
	}

}
