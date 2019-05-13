package pu.employee.domain;

import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_={@Autowired}) 
@Service
public class EmployeeCommandImpl implements EmployeeCommandInPort {

	@NonNull
	private EmployeeRepository employeeRepository;
	
	
	public HREmployee newEmployee(HREmployee employee) {
		HREmployee newEmployee = employee.toBuilder()//
			.id(employee.getFirstname().toLowerCase())//TODO: We really need to fix that!
			.build();
		HREmployee savedEmployee = employeeRepository.save(newEmployee);
		return savedEmployee;
	}

	@PostConstruct
	public void dataInitializer() {
		employeeRepository.deleteAll();
		HREmployee max = HREmployee.of("max", "no-reply@example.com", "Max", "Mustermann",LocalDate.now(), LocalDate.of(2000, 01, 01));
		employeeRepository.save(max);
		HREmployee mike = HREmployee.of("mike", "mike@example.com", "Michael", "Wiesner", LocalDate.of(2007, 04, 01), LocalDate.of(2015,10,21));
		employeeRepository.save(mike);
		HREmployee christian = HREmployee.of("christian", "christian@example.com", "Christian", "Harms", LocalDate.of(2006, 04, 01), LocalDate.of(2015,12,24));
		employeeRepository.save(christian);

	}
	
	
}
