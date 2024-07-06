package com.forumhub.controller;


import com.forumhub.DTO.TopicoDTO;
import com.forumhub.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TopicoDTO> criarTopico(@RequestBody TopicoDTO topicoDTO) {
        TopicoDTO topicoCriado = topicoService.criarTopico(topicoDTO);
        return ResponseEntity.ok(topicoCriado);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<TopicoDTO>> listarTopicos() {
        List<TopicoDTO> topicos = topicoService.listarTopicos();
        return ResponseEntity.ok(topicos);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TopicoDTO> atualizarTopico(@PathVariable Long id, @RequestBody TopicoDTO topicoDTO) {
        TopicoDTO topicoAtualizado = topicoService.atualizarTopico(id, topicoDTO);
        return ResponseEntity.ok(topicoAtualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarTopico(@PathVariable Long id) {
        topicoService.deletarTopico(id);
        return ResponseEntity.noContent().build();
    }
}






//@RestController
//@RequestMapping("/topicos")
//public class TopicoController {
//
//    @Autowired
//    private TopicoRepository topicoRepository;
//
//    @GetMapping
//    public Page<Topico> listarTopicos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
//        return topicoRepository.findAll(paginacao).map(Topico::new);
//    }
//
//
//    @GetMapping("/{id}")
//    public Topico detalharTopico(@PathVariable Long id) {
//        return topicoRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));
//    }
//
//
//
//    @PostMapping
//    @Transactional
//    public Topico criarTopico(@RequestBody @Valid Topico topico) {
//        return topicoRepository.save(topico);
//    }
//
//
//    @PutMapping("/{id}")
//    public Topico atualizarTopico(@PathVariable Long id, @RequestBody Topico topicoAtualizado) {
//        return topicoRepository.findById(id)
//                .map(topico -> {
//                    topico.setTitulo(topicoAtualizado.getTitulo());
//                    topico.setMensagem(topicoAtualizado.getMensagem());
//                    topico.setEstado(topicoAtualizado.isEstado());
//                    return topicoRepository.save(topico);
//                })
//                .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));
//    }
//
//
//
//    @DeleteMapping("/{id}")
//    public void deletarTopico(@PathVariable Long id) {
//        topicoRepository.deleteById(id);
//    }
//}
//
