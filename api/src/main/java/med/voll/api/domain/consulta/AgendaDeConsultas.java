package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidaAgendamentoConsulta;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidaCancelamentoConsulta;
import med.voll.api.domain.dto.consulta.AgendamentoConsultaDTO;
import med.voll.api.domain.dto.consulta.CancelamentoConsultaDTO;
import med.voll.api.domain.dto.consulta.DetalhamentoConsultaDTO;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidaAgendamentoConsulta> validaAgendamento;

    @Autowired
    private List<ValidaCancelamentoConsulta> validaCancelamento;

    public DetalhamentoConsultaDTO agendar(AgendamentoConsultaDTO dados) {
        if(!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe!");
        }

        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe!");
        }

        validaAgendamento.forEach(v -> v.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);
        if(medico == null) {
            throw new ValidacaoException("Não tem médicos disponíveis nessa data!");
        }

        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        consultaRepository.save(consulta);

        return new DetalhamentoConsultaDTO(consulta);
    }

    public void cancelar(CancelamentoConsultaDTO dados) {
        if(!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informada não existe!");
        }

        validaCancelamento.forEach(v -> v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }

    public List<DetalhamentoConsultaDTO> listar() {
        var consultas = consultaRepository.findAllByMotivoCancelamentoIsNull();
        return consultas.stream().map(DetalhamentoConsultaDTO::new).toList();
    }

    private Medico escolherMedico(AgendamentoConsultaDTO dados) {
        if(dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if(dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatório quando médico não for escolhido!");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }


}
