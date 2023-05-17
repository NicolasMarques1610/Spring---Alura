package med.voll.api.aplicacao.dto;

import med.voll.api.paciente.Paciente;

public record ListagemPacienteDTO(Long id, String nome, String email, String cpf) {

    public ListagemPacienteDTO(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
