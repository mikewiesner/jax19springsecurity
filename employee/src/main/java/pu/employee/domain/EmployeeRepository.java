package pu.employee.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<HREmployee, String>  {

	List<HREmployee> findByEmail(String email);
}
