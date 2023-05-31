package med.voll.api.controller;

// Trecho de código suprimido

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.dto.consulta.AgendamentoConsultaDTO;
import med.voll.api.domain.dto.consulta.CancelamentoConsultaDTO;
import med.voll.api.domain.dto.consulta.DetalhamentoConsultaDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    private AgendaDeConsultas agenda;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid AgendamentoConsultaDTO dados) {
        agenda.agendar(dados);
        return ResponseEntity.ok(new DetalhamentoConsultaDTO(null, null, null, null));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid CancelamentoConsultaDTO dados) {
        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }

}
