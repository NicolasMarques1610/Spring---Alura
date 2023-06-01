package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.dto.consulta.AgendamentoConsultaDTO;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarMedicoAtivo implements ValidaAgendamentoConsulta{

    @Autowired
    private MedicoRepository repository;

    public void validar(AgendamentoConsultaDTO dados) {
        // escolha do medico opcional
        if(dados.idMedico() == null) {
            return;
        }

        var medicoEstaAtivo = repository.existsByIdAndAtivoTrue(dados.idMedico());
        if(!medicoEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com o médico inativo");
        }
    }


}
