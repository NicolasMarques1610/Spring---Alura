package med.voll.api.domain.dto.consulta;

import jakarta.validation.constraints.NotNull;

public record CancelamentoConsultaDTO(
        @NotNull
        Long idConsulta,
        @NotNull
        MotivoCancelamento motivo

) {
}
