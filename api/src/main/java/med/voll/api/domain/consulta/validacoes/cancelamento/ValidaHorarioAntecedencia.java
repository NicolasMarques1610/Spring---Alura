package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.dto.consulta.CancelamentoConsultaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidaHorarioAntecedenciaCancelamento")
public class ValidaHorarioAntecedencia implements ValidaCancelamentoConsulta {

    @Autowired
    ConsultaRepository repository;

    @Override
    public void validar(CancelamentoConsultaDTO dados) {
        var consulta = repository.getReferenceById(dados.idConsulta());
        var agora = LocalDateTime.now();
        var diferencaHoras = Duration.between(agora, consulta.getData()).toHours();

        if(diferencaHoras < 24) {
            throw new ValidacaoException("Consulta só pode ser cancelada com antecedência de 24 horas");
        }
    }
}
