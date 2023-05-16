package med.voll.api.aplicacao.dto;

import med.voll.api.medico.Especialidade;
import med.voll.api.medico.Medico;

public record ListagemMedicoDTO(String name, String email, String crm, Especialidade especialidade) {

    public ListagemMedicoDTO(Medico medico) {
        this(medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
