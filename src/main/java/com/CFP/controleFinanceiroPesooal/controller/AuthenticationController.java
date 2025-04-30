package com.CFP.controleFinanceiroPesooal.controller;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CFP.controleFinanceiroPesooal.dto.AuthDTO;
import com.CFP.controleFinanceiroPesooal.dto.RegisterDTO;
import com.CFP.controleFinanceiroPesooal.model.Users;
import com.CFP.controleFinanceiroPesooal.repository.UsersRepository;
import com.CFP.controleFinanceiroPesooal.service.TokenService;



@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO dto) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(dto.email(),
                dto.password());
        Authentication authenticate = authenticationManager.authenticate(credentials);

        String token = tokenService.generateToken((Users) authenticate.getPrincipal());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO) {

        if (userRepository.findByEmail(registerDTO.nome()) != null) {
            return ResponseEntity.badRequest().build();
        }

        Users user = new Users();
        user.setNome(registerDTO.nome());
        user.setPassword(new BCryptPasswordEncoder().encode(registerDTO.password()));
        user.setEmail(registerDTO.email());
        user.setCpf(registerDTO.cpf());
        

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

}
