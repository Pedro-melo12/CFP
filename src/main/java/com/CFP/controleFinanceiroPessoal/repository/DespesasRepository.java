package com.CFP.controleFinanceiroPessoal.repository;

import com.CFP.controleFinanceiroPessoal.model.Despesas;
import com.CFP.controleFinanceiroPessoal.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DespesasRepository extends JpaRepository<Despesas, UUID> {

    List<Despesas> findByUsuario(Users usuario);

}
