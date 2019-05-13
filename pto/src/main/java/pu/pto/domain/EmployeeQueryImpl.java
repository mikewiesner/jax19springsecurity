package pu.pto.domain;

import java.util.List;

import javax.annotation.PostConstruct;

import org.bson.internal.UnsignedLongs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_={@Autowired}) 
@Service
public class EmployeeQueryImpl implements EmployeeQueryInPort {
	
	@NonNull
	private EmployeeRepository employeeRepository;
	
	@NonNull
	private EmployeeSyncOutPort employeeSyncOutPort;
	
	
	public Employee getEmployeeByEmail(String email) {
		List<Employee> employeeList = employeeRepository.findByEmail(email);
		if (employeeList.size() != 1) {
			throw new IllegalStateException("Expected one matching employee, but found "+employeeList.size()+" with the email adress "+email);
		}
		return employeeList.get(0);
	}
	
	public Employee getCurrentActiveEmployee() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new IllegalStateException("No authenticated employee available.");
		}
		Object principal = authentication.getPrincipal();
		
		final PTOUser ptoUser;
		if (principal instanceof PTOUser) {
			ptoUser = (PTOUser) principal;
		}
		else {
			throw new IllegalStateException("Wrong principal type, expected PTOUser, but was " + principal.getClass());
		}
		return ptoUser.getEmployee();

	}
	
	@PostConstruct
	public void dataInitializer() {
		List<Employee> employeeList = employeeSyncOutPort.fetchEmployeesFromEmployeeApp();
		employeeRepository.saveAll(employeeList);
	}

}
