package com.forumhub.controller;

import com.forumhub.DTO.UsuarioDTO;
import com.forumhub.domain.Usuario;
import com.forumhub.repository.UsuarioRepository;
import com.forumhub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioCriado = usuarioService.criarUsuario(usuarioDTO);
        return ResponseEntity.ok(usuarioCriado);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}








//@RestController
//@RequestMapping("/usuarios")
//public class UsuarioController {
//
//    @Autowired
//    private UsuarioRepository usuarioRepository;
//
//    @GetMapping
//    public List<Usuario> listarUsuarios() {
//        return usuarioRepository.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public Usuario detalharUsuario(@PathVariable Long id) {
//        return usuarioRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
//    }
//
//    @PostMapping
//    public Usuario criarUsuario(@RequestBody Usuario usuario) {
//        return usuarioRepository.save(usuario);
//    }
//
//    @PutMapping("/{id}")
//    public Usuario atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
//        return usuarioRepository.findById(id)
//                .map(usuario -> {
//                    usuario.setNome(usuarioAtualizado.getNome());
//                    usuario.setUsername(usuarioAtualizado.getUsername());
//                    usuario.setPassword(usuarioAtualizado.getPassword());
//                    usuario.setEstado(usuarioAtualizado.getEstado());
//                    return usuarioRepository.save(usuario);
//                })
//                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
//    }
//
//    @DeleteMapping("/{id}")
//    public void deletarUsuario(@PathVariable Long id) {
//        usuarioRepository.deleteById(id);
//    }
//}