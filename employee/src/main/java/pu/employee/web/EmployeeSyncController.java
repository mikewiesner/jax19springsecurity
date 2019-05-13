package pu.employee.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pu.employee.domain.EmployeeQueryInPort;
import pu.employee.domain.HREmployee;

@RestController
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
public class EmployeeSyncController {
	
	@NonNull
	private EmployeeQueryInPort employeeQueryInPort;
	
	
	@RequestMapping(value="/sync/employee", produces="application/json")
	public List<EmployeeDTO> allEmployees() {
		List<HREmployee> allEmployees = employeeQueryInPort.getAllEmployees();
		ArrayList<EmployeeDTO> returnList = new ArrayList<EmployeeDTO>();
		allEmployees.forEach(employee -> {
			returnList.add(EmployeeDTO.of(employee.getId(), employee.getEmail(), employee.getFirstname(), employee.getLastname()));
		});

		return returnList;
	}
	
	@Getter
	@AllArgsConstructor(staticName="of")
	private static class EmployeeDTO {
		private String id;
		private String email;
		private String firstname;
		private String lastname; 
	}

}
