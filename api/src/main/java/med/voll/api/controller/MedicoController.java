package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.aplicacao.dto.AtualizaMedicoDTO;
import med.voll.api.aplicacao.dto.ListagemMedicoDTO;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import med.voll.api.aplicacao.dto.MedicoDTO;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid MedicoDTO dados) { //requestBody para pegar do corpo da requisiçao
        repository.save(new Medico(dados));
    }

    @GetMapping
    public Page<ListagemMedicoDTO> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) { // podemos escrever o tamanho da pagina no metodo ou na propria url, se tiver na url sobreescreve o metodo
        return repository.findAllByAtivoTrue(paginacao).map(ListagemMedicoDTO::new); // converti no map uma lista de medicos para uma listagem de medicos
    }

    @PutMapping
    @Transactional // Como tem essa assinatura não é necessário chamar o repository pois faz automaticamente no banco de dados
    public void atualizar(@RequestBody @Valid AtualizaMedicoDTO dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    //Exclusão Física - apaga do banco de dados
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void excluir(@PathVariable Long id) {
//        repository.deleteById(id);
//    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.inativar();
    }
}
