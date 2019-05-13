package pu.pto.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pu.pto.domain.Employee;
import pu.pto.domain.EmployeeQueryInPort;
import pu.pto.domain.PTO;
import pu.pto.domain.PTOCommandInPort;
import pu.pto.domain.PTOQueryInPort;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/PTO")
public class PTOController {

	@NonNull
	private EmployeeQueryInPort employeeQueryInPort;

	@NonNull
	private PTOCommandInPort ptoCommandInPort;

	@NonNull
	private PTOQueryInPort ptoQueryInPort;

	@GetMapping
	public String listPTOs(Model model) {
		List<PTO> allPTOs = ptoQueryInPort.getAllPTOs();
		model.addAttribute("requestList", allPTOs);
		return "listPTOs";
	}

	@GetMapping(params="form")
	public String createForm(Model uiModel) {
		uiModel.addAttribute("pto", PTO.of(null, null, LocalDate.now(), LocalDate.now(), ""));
		return "createPTO";
	}

	@PostMapping
	public String createPTO(PTO pto, BindingResult bindingResult, Model uiModel, @CurrentEmployee Employee employee) {

		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("pto", pto);
			return "createPTO";
		}

		PTO completePTO = pto.toBuilder()//
				.requester(employee)//
				.build();
		ptoCommandInPort.createPTORequest(completePTO);
		return "redirect:/PTO";
	}
	
	@DeleteMapping("/{id}")
	public String deletePTO(@PathVariable("id") String id) {
		PTO pto = ptoQueryInPort.getPTO(id);
		ptoCommandInPort.cancelPTORequest(pto);
		
		return "redirect:/PTO";
		
	}

//	@ModelAttribute(name = "username")
//	public String addCurrentUserToModel(@CurrentUser String user) {
//		return user;
//	}

	@ModelAttribute(name = "username")
	public String addCurrentUserToModel(@CurrentEmployee Employee employee) {
		return employee.getFirstname() + " " + employee.getLastname();
	}

	@InitBinder
	void initBinder(WebDataBinder binder) {
		binder.initDirectFieldAccess();
	}

}
