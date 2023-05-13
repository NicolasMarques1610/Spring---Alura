package endereco;

// Record é uma nova classe de dados imutável, que oferece uma maneira concisa e declarativa de definir classes de dados 
// com campos, métodos e comportamentos padrão.
public record DadosEndereco(String logradouro, String bairro, String cep, String cidade, String uf, String complemento, String numero) {
    
}
