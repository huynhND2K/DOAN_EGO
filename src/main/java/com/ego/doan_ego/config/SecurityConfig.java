package com.ego.doan_ego.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

@Configuration
public class SecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        return new ProviderManager(Collections.singletonList(authenticationProvider(userDetailsService)));
    }

    @Bean
    public CustomAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        return new CustomAuthenticationProvider(userDetailsService);
    }

    public static class CustomAuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {
        private final UserDetailsService userDetailsService;

        public CustomAuthenticationProvider(UserDetailsService userDetailsService) {
            this.userDetailsService = userDetailsService;
        }

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (userDetails == null || !password.equals(userDetails.getPassword())) {
                throw new UsernameNotFoundException("Invalid username or password");
            }

            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        }

        @Override
        public boolean supports(Class<?> authentication) {
            return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
        }
    }
}
