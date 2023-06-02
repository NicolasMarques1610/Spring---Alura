package med.voll.api.controller;

// Trecho de código suprimido

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.dto.consulta.AgendamentoConsultaDTO;
import med.voll.api.domain.dto.consulta.CancelamentoConsultaDTO;
import med.voll.api.domain.dto.consulta.DetalhamentoConsultaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @PostMapping("/agendar")
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid AgendamentoConsultaDTO dados) {
        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<DetalhamentoConsultaDTO>> listar() {
        var consultas = agenda.listar();

        return ResponseEntity.ok(consultas);
    }

    @DeleteMapping("/cancelar")
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid CancelamentoConsultaDTO dados) {
        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }

}
