package pu.pto.domain;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName="of")
@Builder
public class Employee {

	private Employee() {

	}

	@Id
	private String id;
	private String email;
	private String firstname;
	private String lastname;

}
