package com.CFP.controleFinanceiroPesooal.controller;

import com.CFP.controleFinanceiroPesooal.dto.RegisterDTO;
import com.CFP.controleFinanceiroPesooal.model.Users;
import com.CFP.controleFinanceiroPesooal.repository.UsersRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class UsersController {

    @Autowired
    private UsersRepository userRepository;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO registerDTO) {
        try {
            if (userRepository.findByEmail(registerDTO.email()).isPresent()) {
                return ResponseEntity.badRequest().body("Email já está em uso.");
            }

            Users user = new Users();
            user.setNome(registerDTO.nome());
            user.setPassword(new BCryptPasswordEncoder().encode(registerDTO.password()));
            user.setEmail(registerDTO.email());
            user.setCpf(registerDTO.cpf());

            userRepository.save(user);

            return ResponseEntity.ok("Usuário registrado com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro interno: " + e.getMessage());
        }
    }

}
