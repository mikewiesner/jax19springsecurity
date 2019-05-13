package pu.pto.web;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pu.pto.domain.UserRole;
import pu.pto.domain.UserRoleQueryInPort;

@RequiredArgsConstructor(onConstructor_ = { @Autowired })
@Slf4j
public class PTOGrantedAuthoritiesMapper implements GrantedAuthoritiesMapper {

	@NonNull
	private UserRoleQueryInPort userRoleQueryInPort;

	@Override
	public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Set<GrantedAuthority> oidcMappedAuthorities = new HashSet<>();
		Set<GrantedAuthority> ptoMappedAuthorities = new HashSet<>();

		extractIdTokenClaims(authorities, oidcMappedAuthorities);

		mapRolesToRights(this.userRoleQueryInPort, oidcMappedAuthorities, ptoMappedAuthorities);

		ptoMappedAuthorities.addAll(oidcMappedAuthorities);
		return ptoMappedAuthorities;
	}

	private static void mapRolesToRights(UserRoleQueryInPort userRoleQueryInPort,
			Set<GrantedAuthority> oidcMppedAuthorities, Set<GrantedAuthority> ptoMappedAuthorities) {
		oidcMppedAuthorities.forEach(authority -> {
			try {
				String authorityWithoutRolePrefix = authority.getAuthority().substring(5);
				UserRole userRole = userRoleQueryInPort.findUserRoleByName(authorityWithoutRolePrefix);
				ptoMappedAuthorities.addAll(
						AuthorityUtils.commaSeparatedStringToAuthorityList(userRole.toCommaSeperatedRightsList()));
			} catch (NoSuchElementException ex) {
				log.info("Role {} not found", authority.getAuthority());
			}
		});
	}

	private static void extractIdTokenClaims(Collection<? extends GrantedAuthority> authorities,
			Set<GrantedAuthority> oidcMappedAuthorities) {
		authorities.forEach(authority -> {
			if (OidcUserAuthority.class.isInstance(authority)) {
				OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
				OidcIdToken idToken = oidcUserAuthority.getIdToken();

				List<String> rolesAsStringList = idToken.getClaimAsStringList("roles");
				rolesAsStringList.forEach(role -> {
					oidcMappedAuthorities.add(new SimpleGrantedAuthority(role));
				});
			}
		});
	}

}
