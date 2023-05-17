package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.aplicacao.dto.AtualizaPacienteDTO;
import med.voll.api.aplicacao.dto.ListagemPacienteDTO;
import med.voll.api.aplicacao.dto.PacienteDTO;
import med.voll.api.paciente.Paciente;
import med.voll.api.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid PacienteDTO dados) {
        repository.save(new Paciente(dados));
    }

    @GetMapping
    public Page<ListagemPacienteDTO> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) { // podemos escrever o tamanho da pagina no metodo ou na propria url, se tiver na url sobreescreve o metodo
        return repository.findAllByAtivoTrue(paginacao).map(ListagemPacienteDTO::new); // converti no map uma lista de medicos para uma listagem de medicos
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid AtualizaPacienteDTO dados) {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.inativar();
    }
}
