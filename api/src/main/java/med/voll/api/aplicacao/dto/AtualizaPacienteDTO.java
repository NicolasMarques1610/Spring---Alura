package med.voll.api.aplicacao.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record AtualizaPacienteDTO(
        @NotNull
        Long id,
        String nome,
        String telefone,
        @Valid
        EnderecoDTO endereco) {
}
