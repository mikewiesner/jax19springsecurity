package pu.pto.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pu.pto.domain.Employee;
import pu.pto.domain.EmployeeRepository;
import pu.pto.domain.EmployeeSyncOutPort;

@Controller
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
public class IndexController {
	
	@NonNull
	private EmployeeSyncOutPort syncOutPort;
	
	@NonNull
	private EmployeeRepository employeeRepository;

	@RequestMapping("/")
	public String redirectToStart() {
		return "redirect:/PTO";
	}
	
	@RequestMapping(value="/", params="sync")
	public String syncEmployees() {
		//TODO: We really need to ask Mike how to do that with Kafka
		List<Employee> employees = syncOutPort.fetchEmployeesFromEmployeeApp();
		employeeRepository.saveAll(employees);
		return "redirect:/PTO";
	}
	
}
