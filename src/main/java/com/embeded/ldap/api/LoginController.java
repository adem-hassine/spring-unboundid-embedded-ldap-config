package com.embeded.ldap.api;

import com.embeded.ldap.JwtUtils;
import com.embeded.ldap.constants.UtilityConstants;
import com.embeded.ldap.dto.AuthenticationRequest;
import com.embeded.ldap.dto.AuthenticationResponse;
import lombok.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {




    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping(UtilityConstants.LOGIN_ENDPOINT)
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword());
         authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return AuthenticationResponse.builder()
                .token(this.jwtUtils.generateToken(authenticationRequest.getUsername())).username(authenticationRequest.getUsername()).build();
    }
}
