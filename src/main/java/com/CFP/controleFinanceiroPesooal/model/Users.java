package com.CFP.controleFinanceiroPesooal.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Users {

    private UUID id;

    private String cpf;

    private String nome;

    private String email;

    private String password;



    
}
