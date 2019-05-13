package pu.employee.web;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pu.employee.domain.EmployeeCommandInPort;
import pu.employee.domain.EmployeeQueryInPort;
import pu.employee.domain.HREmployee;

@Controller
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/employee")
public class EmployeeController {
	
	@NonNull
	private EmployeeQueryInPort employeeQueryInPort;
	
	@NonNull
	private EmployeeCommandInPort employeeCommandInPort;
	
	
	@GetMapping
	public String list(Model model) {
		List<HREmployee> allEmployees = employeeQueryInPort.getAllEmployees();
		model.addAttribute("employeeList", allEmployees);
		return "listEmployees";
	}
	
	@GetMapping(params = "form")
	public String createForm(Model uiModel) {
		uiModel.addAttribute("employee", HREmployee.of(null, null, null, null, null, null));
		return "createEmployee";
	}
	
	@PostMapping
	public String create(HREmployee employee, BindingResult bindingResult, Model uiModel) {

		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("employee", employee);
			return "createEmployee";
		}
		
		employeeCommandInPort.newEmployee(employee);
	
		return "redirect:/employee";
	}
 

	
	@InitBinder
	void initBinder(WebDataBinder binder) {
		binder.initDirectFieldAccess();
	}
	
//	@ModelAttribute(name = "username")
//	public String addCurrentUserToModel() {
//		return "Not available";
//	}
	
	
	
	
	
	
	
	
	
	
	

@ModelAttribute(name = "username")
public String addCurrentUserToModel(Principal principal) {
	return principal.getName();
}

	
	
	
	
	
	
	
	
////Variante 2
//@ModelAttribute(name = "username")
//public String addCurrentUserToModel(@CurrentUser String username) {
//	return username;
//}
}


