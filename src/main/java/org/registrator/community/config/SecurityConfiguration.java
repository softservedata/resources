package org.registrator.community.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		System.out.println(userDetailsService);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.formLogin().loginPage("/login").permitAll()

				.failureUrl("/login?error").usernameParameter("j_username").passwordParameter("j_password").and()
				.logout().logoutUrl("/logout").permitAll().logoutSuccessUrl("/login?logout").and().exceptionHandling()
				.accessDeniedPage("/login").and()

				.authorizeRequests()

				.antMatchers("/registrator/resource/showAllResources")
				.access("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_USER')")
				.antMatchers("/registrator/resource/searchOnMap")
				.access("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_USER')")
				.antMatchers("/registrator/resourcetypes/show-res-types").access("hasRole('ROLE_REGISTRATOR')")
				.antMatchers("/inquiry/add/listInquiryUserInput")
				.access("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_USER')")
				.antMatchers("/inquiry/add/listInqUserOut")
				.access("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_USER')")
				.antMatchers("/registrator/resource/addresource")
				.access("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_USER')")
				.antMatchers("/administrator/users/search")
				.access("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_ADMIN')")
				.antMatchers("/administrator/users/get-all-users").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/administrator/users/get-all-inactive-users").access("hasRole('ROLE_ADMIN')");

	}

}