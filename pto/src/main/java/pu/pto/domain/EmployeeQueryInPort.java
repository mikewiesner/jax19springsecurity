package pu.pto.domain;

public interface EmployeeQueryInPort {
	
	Employee getEmployeeByEmail(String email);

	Employee getCurrentActiveEmployee();

}
