package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.dto.consulta.AgendamentoConsultaDTO;
import med.voll.api.domain.dto.consulta.CancelamentoConsultaDTO;
import med.voll.api.domain.dto.consulta.DetalhamentoConsultaDTO;
import med.voll.api.domain.dto.consulta.MotivoCancelamento;
import med.voll.api.domain.medico.Especialidade;
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AgendamentoConsultaDTO> agendamentoConsultaDTOJson;

    @Autowired
    private JacksonTester<DetalhamentoConsultaDTO> detalhamentoConsultaDTOJson;

    @Autowired
    private JacksonTester<CancelamentoConsultaDTO> cancelamentoConsultaDTOJson;

    @Autowired
    private JacksonTester<List<DetalhamentoConsultaDTO>> listarJson;

    @MockBean
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    @WithMockUser
    void agendar_cenario1() throws Exception {
        var response = mvc.perform(post("/consultas/agendar"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informacoes estao validas")
    @WithMockUser
    void agendar_cenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;

        var detalhamentoConsulta = new DetalhamentoConsultaDTO(null, 2l, 5l, data);
        when(agendaDeConsultas.agendar(any())).thenReturn(detalhamentoConsulta);

        var response = mvc
                .perform(
                        post("/consultas/agendar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(agendamentoConsultaDTOJson.write(
                                        new AgendamentoConsultaDTO(2l,5l,data,especialidade)
                                ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = detalhamentoConsultaDTOJson.write(
                detalhamentoConsulta
        ).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    @WithMockUser
    void cancelar_cenario1() throws Exception {
        var response = mvc.perform(delete("/consultas/cancelar"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 204 quando informacoes estao validas")
    @WithMockUser
    void cancelar_cenario2() throws Exception {
        var response = mvc
                .perform(
                        delete("/consultas/cancelar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(cancelamentoConsultaDTOJson.write(
                                        new CancelamentoConsultaDTO(1l, MotivoCancelamento.PACIENTE_DESISTIU)
                                ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

}