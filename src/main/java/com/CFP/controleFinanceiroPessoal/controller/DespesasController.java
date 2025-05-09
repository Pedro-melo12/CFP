package com.CFP.controleFinanceiroPessoal.controller;

import com.CFP.controleFinanceiroPessoal.dto.DespesasDTO;
import com.CFP.controleFinanceiroPessoal.model.Despesas;
import com.CFP.controleFinanceiroPessoal.service.DespesasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/despesas")
public class DespesasController {

    @Autowired
    private DespesasService despesasService;

    @PostMapping
    public ResponseEntity<Object> cadastrarDespesa(@RequestBody DespesasDTO despesasDTO) {
        try {
            Despesas despesaCadastrada = despesasService.cadastrarDespesa(despesasDTO);
            return new ResponseEntity<>(despesaCadastrada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao cadastrar despesa: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Object> obterDespesas() {
        try {
            var despesas = despesasService.obterTodasDespesas();
            return new ResponseEntity<>(despesas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao listar despesas: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obterDespesaPorId(@PathVariable UUID id) {
        try {
            Despesas despesa = despesasService.obterDespesaPorId(id);
            return new ResponseEntity<>(despesa, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Despesa não encontrada: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editarDespesa(@PathVariable UUID id, @RequestBody DespesasDTO despesasDTO) {
        try {
            Despesas despesaAtualizada = despesasService.editarDespesa(id, despesasDTO);
            return new ResponseEntity<>(despesaAtualizada, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao editar despesa: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarDespesa(@PathVariable UUID id) {
        try {
            despesasService.deletarDespesa(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao deletar despesa: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
