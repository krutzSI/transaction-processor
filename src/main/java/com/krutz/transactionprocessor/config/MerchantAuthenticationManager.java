package com.krutz.transactionprocessor.config;

import com.krutz.transactionprocessor.dao.model.MerchantDetailsDO;
import com.krutz.transactionprocessor.service.MerchantService;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MerchantAuthenticationManager implements AuthenticationManager {

	@Autowired
	private MerchantService merchantService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UUID principal = UUID.fromString((String) authentication.getPrincipal());
		MerchantDetailsDO merchant = merchantService.getMerchantByApiKey(principal);
		if (Objects.isNull(merchant) || !merchant.getApiKey().equals(principal)) {
			throw new BadCredentialsException("The API key was not found or not the expected value.");
		}
		authentication.setAuthenticated(true);
		return authentication;
	}

}
