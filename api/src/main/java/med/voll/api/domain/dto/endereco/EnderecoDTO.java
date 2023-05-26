package med.voll.api.domain.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// Record é uma nova classe de dados imutável, que oferece uma maneira concisa e declarativa de definir classes de dados
// com campos, métodos e comportamentos padrão.
public record EnderecoDTO(
        @NotBlank(message = "{logradouro.obrigatorio}")
        String logradouro,
        @NotBlank(message = "{bairro.obrigatorio}")
        String bairro,
        @NotBlank(message = "{cep.obrigatorio}")
        @Pattern(regexp = "\\d{8}", message = "{cep.invalido}")
        String cep,
        @NotBlank(message = "{cidade.obrigatorio}")
        String cidade,
        @NotBlank(message = "{uf.obrigatorio}")
        @Size(min = 2, max = 2, message = "{uf.invalido}")
        String uf,
        String complemento,
        String numero) {
    
}
