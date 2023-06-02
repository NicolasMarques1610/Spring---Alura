package med.voll.api.controller;

import med.voll.api.domain.dto.endereco.EnderecoDTO;
import med.voll.api.domain.dto.medico.DetalhamentoMedicoDTO;
import med.voll.api.domain.dto.medico.MedicoDTO;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<MedicoDTO> medicoDTOJson;

    @Autowired
    private JacksonTester<DetalhamentoMedicoDTO> detalhamentoMedicoDTOJson;

    @MockBean
    private MedicoRepository repository;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    @WithMockUser
    void cadastrar_cenario1() throws Exception {
        var response = mvc
                .perform(post("/medicos/cadastrar"))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informacoes estao validas")
    @WithMockUser
    void cadastrar_cenario2() throws Exception {
        var cadastro = new MedicoDTO(
                "Medico",
                "medico@voll.med",
                "51 99999-9999",
                "123456",
                Especialidade.CARDIOLOGIA,
                enderecoDTO());

        when(repository.save(any())).thenReturn(new Medico(cadastro));

        var response = mvc
                .perform(post("/medicos/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(medicoDTOJson.write(cadastro).getJson()))
                .andReturn().getResponse();

        var detalhamento = new DetalhamentoMedicoDTO(
                null,
                cadastro.nome(),
                cadastro.email(),
                cadastro.crm(),
                cadastro.telefone(),
                cadastro.especialidade(),
                new Endereco(cadastro.endereco())
        );
        var jsonEsperado = detalhamentoMedicoDTOJson.write(detalhamento).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    private EnderecoDTO enderecoDTO() {
        return new EnderecoDTO(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }

}