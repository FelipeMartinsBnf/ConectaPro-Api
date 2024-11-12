package com.conectaPro.conectaproApi.Service;

import com.conectaPro.conectaproApi.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// A anotação @Service indica que esta classe é um componente de serviço no contexto do Spring.
// Isso permite que o Spring a reconheça como um bean e a gerencie.
@Service
public class AuthService implements UserDetailsService {

    // A anotação @Autowired permite que o Spring faça a injeção de dependência do UsuarioRepository,
    // que é responsável por interagir com a base de dados para operações relacionadas a usuários.
    @Autowired
    private UsuarioRepository usuarioRepository;

    // O método loadUserByUsername é implementado da interface UserDetailsService.
    // Este método é chamado para carregar os detalhes do usuário com base no nome de usuário (ou e-mail).
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Aqui, estamos usando o usuarioRepository para buscar um usuário pelo e-mail (username).
        // Se o usuário não for encontrado, uma exceção UsernameNotFoundException será lançada.
        return usuarioRepository.findByEmail(username);
    }
}
