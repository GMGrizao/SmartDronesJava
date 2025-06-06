package br.com.fiap.smartdrones.controller;

import br.com.fiap.smartdrones.dto.AuthDTO;
import br.com.fiap.smartdrones.model.Token;
import br.com.fiap.smartdrones.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody @Valid AuthDTO authDTO) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authDTO.getUsername(),
                authDTO.getPassword()
        );

        Authentication auth = authenticationManager.authenticate(authToken);
        
        UserDetails userDetails = (UserDetails) auth.getPrincipal(); 
        
        String jwt = tokenService.generateToken(userDetails); 

        return ResponseEntity.ok(new Token(jwt, "Bearer", userDetails.getUsername()));
    }
}