package com.forumhub.controller;

import com.forumhub.DTO.TopicoDTO;
import com.forumhub.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService topicoService;

    @Autowired
    public TopicoController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    @GetMapping
    public ResponseEntity<List<TopicoDTO>> listarTopicos() {
        List<TopicoDTO> topicos = topicoService.listarTodos();
        return ResponseEntity.ok(topicos);
    }

    @PostMapping
    public ResponseEntity<TopicoDTO> cadastrarTopico(@RequestBody TopicoDTO topicoDTO) {
        TopicoDTO novoTopico = topicoService.cadastrarTopico(topicoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoTopico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDTO> atualizarTopico(@PathVariable Long id, @RequestBody TopicoDTO topicoDTO) {
        TopicoDTO topicoAtualizado = topicoService.atualizarTopico(id, topicoDTO);
        return ResponseEntity.ok(topicoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTopico(@PathVariable Long id) {
        topicoService.deletarTopico(id);
        return ResponseEntity.noContent().build();
    }
}
