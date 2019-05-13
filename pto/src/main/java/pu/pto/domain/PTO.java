package pu.pto.domain;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor(staticName="of")
@Builder(toBuilder=true)
@ToString
public class PTO {
	
	private PTO() {
	}

	
	@Id
	private String id;
	private Employee requester;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate fromDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate toDate;
	private String comment;

}
