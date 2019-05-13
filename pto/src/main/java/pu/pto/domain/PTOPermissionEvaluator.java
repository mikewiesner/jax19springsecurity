package pu.pto.domain;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

public class PTOPermissionEvaluator implements PermissionEvaluator {
	
	public boolean hasPermission(Authentication auth, Object target, Object perm) {
		PTOUser principal = (PTOUser) auth.getPrincipal();
		
		if (target instanceof PTO) {
			PTO pto = (PTO) target;
			if (perm.equals("cancel")) {
				return principal.getUsername().equals(pto.getRequester().getId());
			}
		}
		throw new UnsupportedOperationException("hasPermission not supported for object <"+target+"> and permission <"+perm+">");
	}

	public boolean hasPermission(Authentication authentication,
			Serializable targetId, String targetType, Object permission) {
		throw new UnsupportedOperationException("Not supported");
	}

}
