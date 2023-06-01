package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.dto.consulta.AgendamentoConsultaDTO;

public interface ValidaAgendamentoConsulta {
    // n eh necessario colocar o public porque fica implicito que os metodos de uma interface sao publicos
    void validar(AgendamentoConsultaDTO dados);
}
