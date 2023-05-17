package med.voll.api.aplicacao.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtualizaMedicoDTO(
        @NotNull
        Long id,
        String nome,
        String telefone,
        @Valid
        EnderecoDTO endereco) {}
