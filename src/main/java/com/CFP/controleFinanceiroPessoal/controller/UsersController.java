package com.CFP.controleFinanceiroPessoal.controller;

import com.CFP.controleFinanceiroPessoal.dto.RegisterDTO;
import com.CFP.controleFinanceiroPessoal.model.Users;
import com.CFP.controleFinanceiroPessoal.repository.UsersRepository;



import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController

@RequestMapping("users")
public class UsersController {

    @Autowired
    private UsersRepository userRepository;

    @PostMapping("/register")
    @Transactional
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

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody @Valid RegisterDTO dto) {
        try {
            return userRepository.findById(id)
                    .map(user -> {
                        user.setNome(dto.nome());
                        user.setEmail(dto.email());
                        user.setCpf(dto.cpf());
                        user.setPassword(new BCryptPasswordEncoder().encode(dto.password()));
                        userRepository.save(user);
                        return ResponseEntity.ok("Usuário atualizado com sucesso.");
                    })
                    .orElse(ResponseEntity.status(404).body("Usuário não encontrado."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                return ResponseEntity.ok("Usuário deletado com sucesso.");
            } else {
                return ResponseEntity.status(404).body("Usuário não encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao deletar: " + e.getMessage());
        }
    }

}
