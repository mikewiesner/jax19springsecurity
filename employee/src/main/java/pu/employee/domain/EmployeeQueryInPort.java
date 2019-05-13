package pu.employee.domain;

import java.util.List;

public interface EmployeeQueryInPort {
	
	HREmployee getEmployeeByEmail(String email);

	List<HREmployee> getAllEmployees();

}
