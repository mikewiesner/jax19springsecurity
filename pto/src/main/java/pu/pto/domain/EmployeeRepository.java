package pu.pto.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, String>  {

	List<Employee> findByEmail(String email);
}
