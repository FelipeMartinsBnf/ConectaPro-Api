package com.conectaPro.conectaproApi.domain.repository;

import com.conectaPro.conectaproApi.domain.entity.servico.Servico;
import com.conectaPro.conectaproApi.domain.entity.usuario.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Vamos dizer para o Spring que isso é um teste com o JPA
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Para dizer que não vamos substituir o banco para os testes (Nãu usar em memoria por exemplo
@ActiveProfiles("test") // Dizer para o Spring ler o application-test.properties
class ServicoRepositoryTest {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private TestEntityManager em; // gerenciador de entiades para as entidades

    @Test
    @DisplayName("deve retornar um array Vazio quando não existe nenhuma tag correspondente")
    void testarProcuraComTagsSenario1(){
        cadastrarServico("Servico de teste", "descrição", "Itajubá", null, null);
        cadastrarServico("Servico 2", "sem desc", "local1", null, null);
        var resultado = servicoRepository.procuraTags(Collections.singletonList("Test"));
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("deve retornar 2 servicos com a tag que foi enviada")
    void testarProcuraComTagsSenario2(){

        ArrayList<String> tagsCertas = new ArrayList<>();
        tagsCertas.add("Testes");
        tagsCertas.add("Java");

        ArrayList<String> tags = new ArrayList<>();
        tags.add("TS");
        tags.add("Js");

        var usuario = cadastrarUsuario("Teste", "t@teste.com.br", "654321","22345684456");
        var servico1 = cadastrarServico("Servico de teste", "descrição", "Itajubá", tagsCertas, usuario);
        var servico2 = cadastrarServico("Servico 2", "sem desc", "local1", tagsCertas, usuario);
        cadastrarServico("Servico de errado", ".", "São Paulo", tags, usuario);

        ArrayList<Servico> expected = new ArrayList<>();
        expected.add(servico1);
        expected.add(servico2);

        var resultado = servicoRepository.procuraTags(tagsCertas);
        assertThat(resultado).isEqualTo(expected);
    }

    @Test
    @DisplayName("Deve retornar vazio quando a pesquisamos algo que não existe no banco")
    void testarProcuraFiltradaSenario1(){
        var usuario = cadastrarUsuario("Teste", "t@teste.com.br", "123456","12345684456");
        cadastrarServico("Servico de teste", "descrição", "Itajubá", null, usuario);
        cadastrarServico("Servico 2", "sem desc", "local1", null, usuario);

        var resultado = servicoRepository.procuraFiltrada("FiltroNome", "FiltroLocal");
        assertThat(resultado).isEmpty();

    }

    @Test
    @DisplayName("Deve retornar o servicos corretos pesquisando pelos 2 campos")
    void testarProcuraFiltradaSenario2(){
        var usuario = cadastrarUsuario("Teste", "t@teste.com.br", "123456","12345684456");
        var servico1 = cadastrarServico("Servico de teste no Spring", "descrição", "Itajubá", null, usuario);
        var servico2 = cadastrarServico("Servico Spring Boot", "sem desc", "Itajubá", null, usuario);
        cadastrarServico("Servico Java", "sem desc", "São Paulo", null, usuario);

        ArrayList<Servico> expected = new ArrayList<>();
        expected.add(servico1);
        expected.add(servico2);

        var resultado = servicoRepository.procuraFiltrada("Spring", "Itajubá");
        assertThat(resultado).isEqualTo(expected);

    }


    @Test
    @DisplayName("Deve retornar o servicos corretos pesquisando por 1 campo")
    void testarProcuraFiltradaSenario3(){
        var usuario = cadastrarUsuario("Teste", "t@teste.com.br", "123456","12345684456");
        var servico1 = cadastrarServico("Servico de teste no Spring", "descrição", "Florianópolis", null, usuario);
        cadastrarServico("Servico React", "sem desc", "Itajubá", null, usuario);
        cadastrarServico("Servico Java", "sem desc", "São Paulo", null, usuario);

        ArrayList<Servico> expected = new ArrayList<>();
        expected.add(servico1);

        var resultado = servicoRepository.procuraFiltrada("Spring", null);
        assertThat(resultado).isEqualTo(expected);

    }

    private Usuario cadastrarUsuario(String nome, String email, String senha, String cpf){
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setSenha(senha);
        usuario.setEmail(email);
        usuario.setCpf(cpf);
        em.persist(usuario);
        return usuario;
    }

    private Servico cadastrarServico(String nome, String desc, String localizacao, List<String> tags, Usuario usuario){
        Servico servico = new Servico();
        servico.setNome(nome);
        servico.setDescricao(desc);
        servico.setLocalizacao(localizacao);
        servico.setTags(tags);
        servico.setUsuario(usuario);
        em.persist(servico);
        return servico;
    }

}