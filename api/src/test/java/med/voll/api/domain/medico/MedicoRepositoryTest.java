package med.voll.api.domain.medico;

import med.voll.api.domain.dto.endereco.EnderecoDTO;
import med.voll.api.domain.dto.medico.MedicoDTO;
import med.voll.api.domain.dto.paciente.PacienteDTO;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.consulta.Consulta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // testa alguma coisa da camada de persistencia, do spring data jpa, usado para utilizar uma interface repository
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // anotacao para usarmos o nosso proprio banco, já que o spring por padrao usa um banco embutido
@ActiveProfiles("test") // utilizar o aplication properties -test
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria devolver null quando unico medico cadastrado não esta disponivel na data")
    void escolherMedicoAleatorioLivreNaDataCenario1() {
        //given ou arrange, significa dado que temos tais dados
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        //when ou act, significa quando disparamos alguma acao
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        //then ou assert, significa este é o resultado esperado
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver medico quando ele estiver disponivel na data")
    void escolherMedicoAleatorioLivreNaDataCenario2() {
        //given ou arrange, significa dado que temos tais dados
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        //when ou act, significa quando disparamos alguma acao
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        //then ou assert, significa este é o resultado esperado
        assertThat(medicoLivre).isEqualTo(medico);
    }

    @Test
    @DisplayName("Deveria devolver null quando há médicos disponíveis no horário e data, mas de outra especialidade")
    void escolherMedicoAleatorioLivreNaDataCenario3() {
        //given ou arrange, significa dado que temos tais dados
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        //when ou act, significa quando disparamos alguma acao
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.DERMATOLOGIA, proximaSegundaAs10);

        //then ou assert, significa este é o resultado esperado
        assertThat(medicoLivre).isNull();
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(medicoDTO(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(pacienteDTO(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private MedicoDTO medicoDTO(String nome, String email, String crm, Especialidade especialidade) {
        return new MedicoDTO(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                enderecoDTO()
        );
    }

    private PacienteDTO pacienteDTO(String nome, String email, String cpf) {
        return new PacienteDTO(
                nome,
                email,
                "61999999999",
                cpf,
                enderecoDTO()
        );
    }

    private EnderecoDTO enderecoDTO() {
        return new EnderecoDTO(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}