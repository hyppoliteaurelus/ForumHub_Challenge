package com.forumhub.controller;

import com.forumhub.DTO.CursoDTO;
import com.forumhub.domain.Curso;
import com.forumhub.repository.CursoRepository;
import com.forumhub.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CursoDTO> criarCurso(@RequestBody CursoDTO cursoDTO) {
        CursoDTO cursoCriado = cursoService.criarCurso(cursoDTO);
        return ResponseEntity.ok(cursoCriado);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<CursoDTO>> listarCursos() {
        List<CursoDTO> cursos = cursoService.listarCursos();
        return ResponseEntity.ok(cursos);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CursoDTO> atualizarCurso(@PathVariable Long id, @RequestBody CursoDTO cursoDTO) {
        CursoDTO cursoAtualizado = cursoService.atualizarCurso(id, cursoDTO);
        return ResponseEntity.ok(cursoAtualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarCurso(@PathVariable Long id) {
        cursoService.deletarCurso(id);
        return ResponseEntity.noContent().build();
    }
}




//
//@RestController
//@RequestMapping("/cursos")
//public class CursoController {
//
//    @Autowired
//    private CursoRepository cursoRepository;
//
//    @GetMapping
//    public List<Curso> listarCursos() {
//        return cursoRepository.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public Curso detalharCurso(@PathVariable Long id) {
//        return cursoRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));
//    }
//
//    @PostMapping
//    public Curso criarCurso(@RequestBody Curso curso) {
//        return cursoRepository.save(curso);
//    }
//
//    @PutMapping("/{id}")
//    public Curso atualizarCurso(@PathVariable Long id, @RequestBody Curso cursoAtualizado) {
//        return cursoRepository.findById(id)
//                .map(curso -> {
//                    curso.setNome(cursoAtualizado.getNome());
//                    curso.setCategoria(cursoAtualizado.getCategoria());
//                    return cursoRepository.save(curso);
//                })
//                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));
//    }
//
//    @DeleteMapping("/{id}")
//    public void deletarCurso(@PathVariable Long id) {
//        cursoRepository.deleteById(id);
//    }
//}
