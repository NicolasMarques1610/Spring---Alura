package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.dto.consulta.AgendamentoConsultaDTO;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaPacienteAtivo implements ValidaAgendamentoConsulta{

    @Autowired
    private PacienteRepository repository;

    public void validar(AgendamentoConsultaDTO dados) {
        var pacieteEstaAtivo = repository.existsByIdAndAtivoTrue(dados.idPaciente());
        if(!pacieteEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com paciente inativo");
        }
    }
}
