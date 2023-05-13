package med.voll.api.medico;

import endereco.DadosEndereco;

// Record é uma nova classe de dados imutável, que oferece uma maneira concisa e declarativa de definir classes de dados 
// com campos, métodos e comportamentos padrão.
public record DadosCadastroMedico(String nome, String email, String crm, Especialidade especialidade, DadosEndereco endereco) {

}
