package pu.pto.config.web;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

import pu.pto.domain.Employee;
import pu.pto.domain.EmployeeQueryInPort;
import pu.pto.domain.PTOUser;
import pu.pto.domain.UserRoleQueryInPort;
import pu.pto.web.PTOGrantedAuthoritiesMapper;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private EmployeeQueryInPort employeeQueryInPort;
	
	@Autowired
	private UserRoleQueryInPort userRoleQueryInPort;
	
	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.mvcMatchers("/PTO")//
					.hasRole("Employee")//
				.anyRequest()//
					.authenticated()//
					.and()//
				.csrf()//
					.disable()//
				.oauth2Login()//
					.clientRegistrationRepository(this.clientRegistrationRepository)//
					.authorizedClientService(this.authorizedClientService())//
					.userInfoEndpoint()//
						.userAuthoritiesMapper(authoritiesMapper())//
						.oidcUserService(ptoUserService());
	}
	
	
	@Bean
	public OAuth2AuthorizedClientService authorizedClientService() {
		return new InMemoryOAuth2AuthorizedClientService(this.clientRegistrationRepository);
	}
	
	@Bean
	public OAuth2UserService<OidcUserRequest, OidcUser> ptoUserService() {
		final OidcUserService delegate = new OidcUserService();
		
		return (userRequest) -> {
			OidcUser baseUser = delegate.loadUser(userRequest);
			OidcIdToken idToken = baseUser.getIdToken();
			OidcUserAuthority newAuthority = new OidcUserAuthority("ROLE_TEMP",idToken, baseUser.getUserInfo());
			Set<GrantedAuthority> authorities = new HashSet<>();
			authorities.add(newAuthority);
			Employee employee = employeeQueryInPort.getEmployeeByEmail(baseUser.getEmail());
			return new PTOUser(authorities, userRequest.getIdToken(), baseUser.getUserInfo(), employee);
		};
	}
	
	@Bean
	public PTOGrantedAuthoritiesMapper authoritiesMapper() {
		return new PTOGrantedAuthoritiesMapper(userRoleQueryInPort);
	}
	

}
