package pu.pto.domain;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(staticName="of")
@Builder
@Getter
public class UserRight {
	
	@Id
	private String id;


}
