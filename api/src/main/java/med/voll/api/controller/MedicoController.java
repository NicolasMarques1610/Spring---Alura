package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.aplicacao.dto.*;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid MedicoDTO dados, UriComponentsBuilder uriBuilder) { //requestBody para pegar do corpo da requisiçao
        var medico = new Medico(dados);
        repository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalhamentoMedicoDTO(medico)); // recebe a url com o id do medico criado e o body com o detalhes do medico
    }

    @GetMapping
    public ResponseEntity<Page<ListagemMedicoDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) { // podemos escrever o tamanho da pagina no metodo ou na propria url, se tiver na url sobreescreve o metodo
        var page = repository.findAllByAtivoTrue(paginacao).map(ListagemMedicoDTO::new); // converti no map uma lista de medicos para uma listagem de medicos
        return ResponseEntity.ok(page); //retorno o 200 mais a mensagem com a page de medico
    }

    @PutMapping
    @Transactional // Como tem essa assinatura não é necessário chamar o repository pois faz automaticamente no banco de dados
    public ResponseEntity atualizar(@RequestBody @Valid AtualizaMedicoDTO dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        // ok é o 200 padrão só que agora estamo retornando na mensagem o médico com as informações atualizadas e as que não podemos mexer, por isso foi criado um novo DTO que contém essas informações
        return ResponseEntity.ok(new DetalhamentoMedicoDTO(medico));
    }

//    Exclusão Física - apaga do banco de dados
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void excluir(@PathVariable Long id) {
//        repository.deleteById(id);
//    }

    @DeleteMapping("/{id}")
    @Transactional
    // com o ResponseEntity conseguimos controlar a resposta devolvida (por exemplo 200 OK)
    public ResponseEntity excluir(@PathVariable Long id) {
        var medico = repository.findByIdAndAtivoTrue(id);
        medico.inativar();

        return ResponseEntity.noContent().build(); // noContent é o 204 e buildamos o objeto com o build
    }

    @GetMapping("/{id}")
    // com o ResponseEntity conseguimos controlar a resposta devolvida (por exemplo 200 OK)
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.findByIdAndAtivoTrue(id);
        return ResponseEntity.ok(new DetalhamentoMedicoDTO(medico));
    }
}
