package pu.employee.domain;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName="of")
@Builder(toBuilder=true)
public class HREmployee {
	
	private HREmployee() {
		
	}
	
	@Id
	private String id;
	private String email;
	private String firstname;
	private String lastname;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate startDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate birthday;

}
