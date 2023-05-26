package med.voll.api.domain.dto.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.dto.endereco.EnderecoDTO;

public record AtualizaMedicoDTO(
        @NotNull
        Long id,
        String nome,
        String telefone,
        @Valid
        EnderecoDTO endereco) {}
