package pu.pto.domain;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_={@Autowired}) 
@Service
public class UserRoleQuery implements UserRoleQueryInPort {
	
	@NonNull
	private UserRoleRepository userRoleRepository;
	
	public UserRole findUserRoleByName(String roleName) {
		return userRoleRepository.findById(roleName).get();
	}
	
	@PostConstruct
	public void dataInitializer() {
		UserRole userRole = UserRole.builder()//
			.id("Employee")//
			.right(UserRight.of("LIST_PTO"))//
			.right(UserRight.of("CREATE_PTO"))//
			.right(UserRight.of("CANCEL_PTO"))//
			.build();
		userRoleRepository.save(userRole);
	}

}
