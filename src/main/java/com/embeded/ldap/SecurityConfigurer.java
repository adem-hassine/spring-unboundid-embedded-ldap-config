package com.embeded.ldap;

import com.embeded.ldap.constants.UtilityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Configuration
@RequiredArgsConstructor
public class SecurityConfigurer {
    private final JwtUtils jwtUtils;
    @Value("${ldap.url}")
    public String ldapUrl;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(UtilityConstants.LOGIN_ENDPOINT,"error").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new OncePerRequestFilter() {
                    @Override
                    protected boolean shouldNotFilter(HttpServletRequest request) {
                            return request.getServletPath().startsWith(UtilityConstants.LOGIN_ENDPOINT);
                    }

                    @Override
                    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                        String token =request.getHeader(UtilityConstants.AUTHORIZATION_HEADER).substring(7);

                        if (jwtUtils.isValid(token).equals(Boolean.FALSE)){
                            throw new RuntimeException("Untrusted JWT");
                        }

                        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(jwtUtils.extractUsername(token),null,new ArrayList<>()));
                        filterChain.doFilter(request,response);
                    }
                }, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public DefaultSpringSecurityContextSource contextSource() {
        return new DefaultSpringSecurityContextSource(ldapUrl);
    }

    @SneakyThrows
    @Bean
    public AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource) {
        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory
                (contextSource);
        factory.setUserSearchFilter("(sAMAccountName={0})");
        return factory.createAuthenticationManager();
    }


}
