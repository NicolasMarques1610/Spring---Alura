package med.voll.api.aplicacao.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.medico.Especialidade;

// Record é uma nova classe de dados imutável, que oferece uma maneira concisa e declarativa de definir classes de dados 
// com campos, métodos e comportamentos padrão.
// Usando notações do bean validation
public record MedicoDTO(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "\\d{2} \\d{4,5}-\\d{4}")
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crm,
        @NotNull
        Especialidade especialidade,
        @NotNull
        @Valid
        EnderecoDTO endereco) {

}
