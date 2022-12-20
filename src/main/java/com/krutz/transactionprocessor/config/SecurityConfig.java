package com.krutz.transactionprocessor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig {

	@Value("${yourapp.http.auth-token-header-name}")
	private String principalRequestHeader;

	@Autowired
	private MerchantAuthenticationManager merchantAuthenticationManager;

	@Bean
	public MerchantAuthenticationManager getMerchantAuthenticationManager(){
		return merchantAuthenticationManager;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		APIKeyAuthFilter
				filter = new APIKeyAuthFilter(principalRequestHeader);
		filter.setAuthenticationManager(getMerchantAuthenticationManager());
		http.antMatcher("/transaction/**").
				csrf().disable().
				sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
				and().addFilter(filter).authorizeRequests().anyRequest().authenticated();

		return http.build();
	}
}