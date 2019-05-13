package pu.pto.domain;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import lombok.Getter;

@Getter
public class PTOUser extends DefaultOidcUser {
	
	private String username;
	private static final long serialVersionUID = -5242452500819547285L;
	private Employee employee;
	

	
	public PTOUser(Set<GrantedAuthority> authorities, OidcIdToken idToken, OidcUserInfo userInfo, Employee employee) {
		super(authorities, idToken, userInfo);
		this.username = userInfo.getPreferredUsername();
		this.employee = employee;
	}



	
	

}
