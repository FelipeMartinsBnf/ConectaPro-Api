package com.conectaPro.conectaproApi.controller;

import com.conectaPro.conectaproApi.domain.entity.servico.Servico;
import com.conectaPro.conectaproApi.domain.entity.servico.ServicoDetalhadoDto;
import com.conectaPro.conectaproApi.domain.entity.servico.ServicoForm;
import com.conectaPro.conectaproApi.domain.entity.usuario.TipoUsuario;
import com.conectaPro.conectaproApi.domain.entity.usuario.Usuario;
import com.conectaPro.conectaproApi.domain.entity.usuario.UsuarioDto;
import com.conectaPro.conectaproApi.domain.repository.ServicoRepository;
import com.conectaPro.conectaproApi.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ServicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<ServicoForm> servicoForm;

    @Autowired
    private JacksonTester<ServicoDetalhadoDto> servicoDetalhadoDto;

    @MockBean
    private ServicoRepository servicoRepository;

    @Test
    @DisplayName("Deve retornar http 400 quando as informações forem inválidas")
    @WithMockUser
    void criarServico_cenario1() throws Exception {
        var response = mvc.perform(post("/api/servico")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar http 400 quando as informações são válidas")
    @WithMockUser
    void criarServico_cenario2() throws Exception {
        Usuario usuario1 = new Usuario(1L, "tester", "tester@email.com", "123456", "12332125562", TipoUsuario.Prestador, LocalDateTime.now(), 5f);

        var servico = new Servico(null, "Servico de teste", "teste do controller", "Itajuba", false, Collections.singletonList("img"), Collections.singletonList("tags"), LocalDateTime.now(), usuario1);
        var servicoDto = new ServicoDetalhadoDto(servico);
        when(servicoRepository.save(any())).thenReturn(servico);

        var response = mvc.perform(
                post("/api/servico")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(servicoForm.write(
                                new ServicoForm("Servico de teste", "teste do controller", "Itajuba", false, Collections.singletonList("img"), Collections.singletonList("tags"), usuario1.getId() )
                        ).getJson())
        )
                .andReturn().getResponse();

        System.out.println(response.getContentAsString());

        var jsonEsperado = servicoDetalhadoDto.write(
                servicoDto
        ).getJson();

        var respostaString = response.getContentAsString().replaceAll("\"dataCriacao\":\"[^\"]*\"", "\"dataCriacao\":\""+null+"\"");
        jsonEsperado = jsonEsperado.replaceAll("\"dataCriacao\":\"[^\"]*\"", "\"dataCriacao\":\""+null+"\"");

        assertThat(respostaString).isEqualTo(jsonEsperado);
    }

}