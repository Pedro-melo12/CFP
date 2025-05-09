package com.CFP.controleFinanceiroPessoal.service;

import com.CFP.controleFinanceiroPessoal.dto.DespesasDTO;
import com.CFP.controleFinanceiroPessoal.model.Despesas;
import com.CFP.controleFinanceiroPessoal.model.Users;
import com.CFP.controleFinanceiroPessoal.repository.DespesasRepository;
import com.CFP.controleFinanceiroPessoal.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DespesasService {

    @Autowired
    private DespesasRepository despesasRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Transactional
    public Despesas cadastrarDespesa(DespesasDTO despesasDTO) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        Users usuario = usersRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Despesas despesa = new Despesas(
                UUID.randomUUID(),
                despesasDTO.descricao(),
                despesasDTO.valor(),
                despesasDTO.data(),
                despesasDTO.categoria(),
                usuario
        );

        return despesasRepository.save(despesa);
    }

    public List<Despesas> obterTodasDespesas() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Users usuario = usersRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return despesasRepository.findByUsuario(usuario);
    }

    public Despesas obterDespesaPorId(UUID id) {
        return despesasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
    }

    @Transactional
    public Despesas editarDespesa(UUID id, DespesasDTO despesasDTO) {
        Despesas despesa = despesasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        despesa.setDescricao(despesasDTO.descricao());
        despesa.setValor(despesasDTO.valor());
        despesa.setData(despesasDTO.data());
        despesa.setCategoria(despesasDTO.categoria());

        return despesasRepository.save(despesa);
    }

    @Transactional
    public void deletarDespesa(UUID id) {
        Despesas despesa = despesasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        despesasRepository.delete(despesa);
    }

}
