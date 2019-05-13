package pu.pto.domain;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Getter
@AllArgsConstructor(staticName="of")
@Builder
public class UserRole {
	
	@Id
	private String id;
	
	@Singular
	private List<UserRight> rights;
	
	public String toCommaSeperatedRightsList() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (UserRight userRight : rights) {
			if (first == false) {
				sb.append(",");
			}
			first = false;
			sb.append("RIGHT_").append(userRight.getId());
		}
		return sb.toString();
	}

}
