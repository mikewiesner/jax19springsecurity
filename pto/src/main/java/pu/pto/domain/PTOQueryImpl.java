package pu.pto.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_={@Autowired}) 
@Service
public class PTOQueryImpl implements PTOQueryInPort {
	
	@NonNull
	private PTORepository ptoRepository;
	
	@NonNull
	private EmployeeRepository employeeRepository;
	
	@PreAuthorize("hasAuthority('RIGHT_LIST_PTO')")
	public List<PTO> getAllPTOs() {
		Iterable<PTO> findAll = ptoRepository.findAll();
		ArrayList<PTO> arrayList = new ArrayList<PTO>();
		findAll.forEach(arrayList::add);
		return arrayList;
	}
	
	
	public PTO getPTO(String id) {
		return ptoRepository.findById(id).get();
	}
	
	
	@PostConstruct
	public void dataInitializer() {
		Employee max = employeeRepository.findById("max").get();
		Employee mike = employeeRepository.findById("mike").get();
		ptoRepository.deleteAll();
		ptoRepository.save(PTO.of("1", max, LocalDate.now(), LocalDate.now(), "I'm out today!"));
		ptoRepository.save(PTO.of("2", max, LocalDate.of(2008,12, 31), LocalDate.of(2009, 1, 1), null));
		ptoRepository.save(PTO.of("3", mike, LocalDate.of(2008,12, 24), LocalDate.of(2009, 12, 31), "X-Mas"));
		
	}

	

}
