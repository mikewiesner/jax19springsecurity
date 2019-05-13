package pu.pto.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pu.pto.domain.Employee;
import pu.pto.domain.EmployeeSyncOutPort;

@RequiredArgsConstructor(onConstructor_={@Autowired}) 
@Service
public class EmployeeHttpSyncGateway implements EmployeeSyncOutPort {
	 
	@NonNull
	private RestTemplate restTemplate;
	
	@PostConstruct
	//TODO: Ask Mike how to do that with Kafka ;-)
	// JIRA-XXX created for that
	public List<Employee> fetchEmployeesFromEmployeeApp() {
		Employee[] employeesAsArray = restTemplate.getForObject("http://localhost:8082/sync/employee", Employee[].class);
		ArrayList<Employee> arrayList = new ArrayList<>(Arrays.asList(employeesAsArray));
		return arrayList;
		
	}

	
}
