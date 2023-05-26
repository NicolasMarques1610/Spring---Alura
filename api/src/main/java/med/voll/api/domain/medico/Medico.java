package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.dto.medico.AtualizaMedicoDTO;
import med.voll.api.domain.dto.medico.MedicoDTO;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Medico(MedicoDTO dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(AtualizaMedicoDTO dados) {
        if(dados.nome() != null ) this.nome = dados.nome();
        if(dados.telefone() != null) this.telefone = dados.telefone();
        if(dados.endereco() != null) this.endereco.atualizarInformacoes(dados.endereco());
    }

    // Não é excluir pois nós desabilitamos esse médico mas ele continua salvo no banco de dados
    public void inativar() {
        this.ativo = false;
    }
}
