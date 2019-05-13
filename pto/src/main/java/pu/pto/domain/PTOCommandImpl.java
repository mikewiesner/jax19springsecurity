package pu.pto.domain;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_={@Autowired}) 
@Service
public class PTOCommandImpl implements PTOCommandInPort {
	
	@NonNull
	private PTORepository ptoRepository;
	
	@PreAuthorize("hasAuthority('RIGHT_CREATE_PTO')")
	public PTO createPTORequest(PTO ptoRequest) {
		PTO newPTO = ptoRequest.toBuilder()//
			.id(UUID.randomUUID().toString())//
			.build();
		PTO savedPTO = ptoRepository.save(newPTO);
		return savedPTO;
	}

	//@PreAuthorize("hasAuthority('RIGHT_CANCEL_PTO') and #pto.requester.id == principal.username")
	@PreAuthorize("hasAuthority('RIGHT_CANCEL_PTO') and hasPermission(#pto, 'cancel')")
	public void cancelPTORequest(PTO pto) {
		ptoRepository.delete(pto);
	}

}
