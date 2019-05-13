package pu.employee.config.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;

import lombok.SneakyThrows;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/sync/**")//
				.permitAll()//
			.antMatchers("/resource/**")
				.authenticated()
			.anyRequest()//
				//.authenticated() //until roles are setup in Keycloak
				.hasRole("HR")
				.and()//
				.oauth2Login()//
					.clientRegistrationRepository(this.clientRegistrationRepository)//
					.authorizedClientService(this.authorizedClientService())//
					.userInfoEndpoint()//
						.userAuthoritiesMapper(authoritiesMapper());
//						.oidcUserService(oidcUserService()); // Auslesen der Rollen aus dem Access Token
	}


	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	@Bean
	public OAuth2AuthorizedClientService authorizedClientService() {
		return new InMemoryOAuth2AuthorizedClientService(this.clientRegistrationRepository);
	}


	@Bean
	public GrantedAuthoritiesMapper authoritiesMapper() {
		return (authorities) -> {
			Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

			authorities.forEach(authority -> {
				if (OidcUserAuthority.class.isInstance(authority)) {
					OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
					OidcIdToken idToken = oidcUserAuthority.getIdToken();

					List<String> rolesAsStringList = idToken.getClaimAsStringList("roles");
					rolesAsStringList.forEach( role -> {
						mappedAuthorities.add(new SimpleGrantedAuthority(role));
					});
				}
			});

			return mappedAuthorities;
		};

	}



	// Ab hier: Auslesen der Rollen aus dem Access Token
//	private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
//		return new PuOIDCUserService();
//	}
//
//	private static class PuOIDCUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
//
//		final OidcUserService delegate = new OidcUserService();
//
//
//		@Override
//		@SneakyThrows
//		public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
//			OidcUser oidcUser = delegate.loadUser(userRequest);
//
//			Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
//
//			extractAccessTokenClaims(userRequest, mappedAuthorities);
//
//			String userNameAttributeName = userRequest.getClientRegistration()
//					.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
//			oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo(), userNameAttributeName);
//
//			return oidcUser;
//		}
//
//
//		private static void extractAccessTokenClaims(OidcUserRequest userRequest, Set<GrantedAuthority> mappedAuthorities)
//				throws ParseException {
//			JWT accessToken = JWTParser.parse(userRequest.getAccessToken().getTokenValue());
//			List<String> rolesAsStringList = accessToken.getJWTClaimsSet().getStringListClaim("roles");
//			rolesAsStringList.forEach( role -> {
//				mappedAuthorities.add(new SimpleGrantedAuthority(role));
//			});
//		}
//
//
//	}


}