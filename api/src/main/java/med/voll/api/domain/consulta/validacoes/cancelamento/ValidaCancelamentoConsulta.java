package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.dto.consulta.CancelamentoConsultaDTO;

public interface ValidaCancelamentoConsulta {
    void validar(CancelamentoConsultaDTO dados);
}
