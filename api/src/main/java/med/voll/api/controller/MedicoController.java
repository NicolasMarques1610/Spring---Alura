package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.aplicacao.dto.ListagemMedicoDTO;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import med.voll.api.aplicacao.dto.MedicoDTO;

import java.util.List;

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
    public Page<ListagemMedicoDTO> listar(Pageable paginacao) {
        return repository.findAll(paginacao).map(ListagemMedicoDTO::new); // converti no map uma lista de medicos para uma listagem de medicos
    }
}
