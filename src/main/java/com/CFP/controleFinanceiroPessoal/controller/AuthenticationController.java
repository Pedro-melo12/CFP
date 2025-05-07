package com.CFP.controleFinanceiroPessoal.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CFP.controleFinanceiroPessoal.dto.AuthDTO;
import com.CFP.controleFinanceiroPessoal.model.Users;
import com.CFP.controleFinanceiroPessoal.service.TokenService;

@RestController

@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid AuthDTO dto) {
        try {
            UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(dto.email(),
                    dto.password());

            Authentication authenticate = authenticationManager.authenticate(credentials);

            String token = tokenService.generateToken((Users) authenticate.getPrincipal());

            return ResponseEntity.ok("Usuário logado com sucesso. Token: " + token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a requisição.");
        }
    }

}
