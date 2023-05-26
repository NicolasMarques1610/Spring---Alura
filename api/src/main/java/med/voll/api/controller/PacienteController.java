package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.dto.paciente.AtualizaPacienteDTO;
import med.voll.api.domain.dto.paciente.DetalhamentoPacienteDTO;
import med.voll.api.domain.dto.paciente.ListagemPacienteDTO;
import med.voll.api.domain.dto.paciente.PacienteDTO;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid PacienteDTO dados, UriComponentsBuilder uriBuilder) {
        var paciente = new Paciente(dados);
        repository.save(paciente);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalhamentoPacienteDTO(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<ListagemPacienteDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) { // podemos escrever o tamanho da pagina no metodo ou na propria url, se tiver na url sobreescreve o metodo
        var page = repository.findAllByAtivoTrue(paginacao).map(ListagemPacienteDTO::new);
        return ResponseEntity.ok(page); // converti no map uma lista de medicos para uma listagem de medicos
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid AtualizaPacienteDTO dados) {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DetalhamentoPacienteDTO(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var paciente = repository.findByIdAndAtivoTrue(id);
        paciente.inativar();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var paciente = repository.findByIdAndAtivoTrue(id);
        return ResponseEntity.ok(new DetalhamentoPacienteDTO(paciente));
    }
}
