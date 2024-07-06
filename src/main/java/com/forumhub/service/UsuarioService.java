package com.forumhub.service;

import com.forumhub.DTO.UsuarioDTO;
import com.forumhub.domain.Usuario;
import com.forumhub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioDTO criarUsuario(UsuarioDTO usuarioDTO) {
        validarUsuario(usuarioDTO);
        Usuario usuario = new Usuario(usuarioDTO.nome(), usuarioDTO.username(), passwordEncoder.encode(usuarioDTO.password()), usuarioDTO.estado());
        usuario = usuarioRepository.save(usuario);
        return new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getUsername(), null, usuario.getEstado());
    }

    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getUsername(), null, usuario.getEstado()))
                .toList();
    }

    public UsuarioDTO atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        validarUsuario(usuarioDTO);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setNome(usuarioDTO.nome());
            usuario.setUsername(usuarioDTO.username());
            if (StringUtils.hasText(usuarioDTO.password())) {
                usuario.setPassword(passwordEncoder.encode(usuarioDTO.password()));
            }
            usuario.setEstado(usuarioDTO.estado());
            usuario = usuarioRepository.save(usuario);
            return new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getUsername(), null, usuario.getEstado());
        } else {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
    }

    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    private void validarUsuario(UsuarioDTO usuarioDTO) {
        if (!StringUtils.hasText(usuarioDTO.nome())) {
            throw new IllegalArgumentException("Nome do usuário é obrigatório");
        }
        if (!StringUtils.hasText(usuarioDTO.username())) {
            throw new IllegalArgumentException("Username do usuário é obrigatório");
        }
        if (usuarioDTO.password() != null && !StringUtils.hasText(usuarioDTO.password())) {
            throw new IllegalArgumentException("Senha do usuário é obrigatória");
        }
    }
}
