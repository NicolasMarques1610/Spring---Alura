package med.voll.api.aplicacao.dto;

import med.voll.api.medico.Especialidade;
import med.voll.api.medico.Medico;

public record ListagemMedicoDTO(Long id, String name, String email, String crm, Especialidade especialidade) {

    public ListagemMedicoDTO(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}