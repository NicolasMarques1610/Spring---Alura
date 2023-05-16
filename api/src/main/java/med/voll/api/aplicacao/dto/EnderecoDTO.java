package med.voll.api.aplicacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// Record é uma nova classe de dados imutável, que oferece uma maneira concisa e declarativa de definir classes de dados
// com campos, métodos e comportamentos padrão.
public record EnderecoDTO(
        @NotBlank
        String logradouro,
        @NotBlank
        String bairro,
        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String cep,
        @NotBlank
        String cidade,
        @NotBlank
        @Size(min = 2, max = 2)
        String uf,
        String complemento,
        String numero) {
    
}
