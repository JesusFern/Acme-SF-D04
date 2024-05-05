
package acme.features.client.contract;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.contracts.Contract;
import acme.roles.Client;

@Controller
public class ClientContractController extends AbstractController<Client, Contract> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private ClientContractListMineService	listMineService;

	@Autowired
	private ClientContractListAllService	listAllService;

	@Autowired
	private ClientContractShowService		listShowService;

	@Autowired
	private ClientContractCreateService		createService;

	@Autowired
	private ClientContractUpdateService		updateService;

	@Autowired
	private ClientContractDeleteService		deleteService;

	@Autowired
	private ClientContractPublishService	publishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);
		super.addCustomCommand("list-all", "list", this.listAllService);
		super.addBasicCommand("show", this.listShowService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
	}

}
