package com.CFP.controleFinanceiroPessoal.dto;

import com.CFP.controleFinanceiroPessoal.model.CategoriaDespesas;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record DespesasDTO(
        @NotBlank(message = "Descrição é obrigatória")
        String descricao,

        @NotNull(message = "Valor é obrigatório")
        @Positive
        BigDecimal valor,

        @NotNull(message = "Data é obrigatório")
        LocalDate data,

        @NotNull(message = "Categoria é obrigatório")
        CategoriaDespesas categoria
) {}
