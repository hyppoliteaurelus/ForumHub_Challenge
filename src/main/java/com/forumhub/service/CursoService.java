package com.forumhub.service;

import com.forumhub.DTO.CursoDTO;
import com.forumhub.domain.Curso;
import com.forumhub.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    // Método para criar um novo curso
    public CursoDTO criarCurso(CursoDTO cursoDTO) {
        validarCurso(cursoDTO);
        Curso curso = new Curso(cursoDTO.id(), cursoDTO.nome(), cursoDTO.categoria());
        curso = cursoRepository.save(curso);
        return new CursoDTO(curso.getId(), curso.getNome(), curso.getCategoria());
    }

    // Método para listar todos os cursos
    public List<CursoDTO> listarCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        return cursos.stream()
                .map(curso -> new CursoDTO(curso.getId(), curso.getNome(), curso.getCategoria()))
                .toList();
    }

    // Método para atualizar um curso existente
    public CursoDTO atualizarCurso(Long id, CursoDTO cursoDTO) {
        validarCurso(cursoDTO);
        Optional<Curso> cursoOpt = cursoRepository.findById(id);
        if (cursoOpt.isPresent()) {
            Curso curso = cursoOpt.get();
            curso.setNome(cursoDTO.nome());
            curso.setCategoria(cursoDTO.categoria());
            curso = cursoRepository.save(curso);
            return new CursoDTO(curso.getId(), curso.getNome(), curso.getCategoria());
        } else {
            throw new IllegalArgumentException("Curso não encontrado");
        }
    }

    // Método para deletar um curso
    public void deletarCurso(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new IllegalArgumentException("Curso não encontrado");
        }
        cursoRepository.deleteById(id);
    }

    // Método para validar os dados do curso
    private void validarCurso(CursoDTO cursoDTO) {
        if (!StringUtils.hasText(cursoDTO.nome())) {
            throw new IllegalArgumentException("Nome do curso é obrigatório");
        }
        if (!StringUtils.hasText(cursoDTO.categoria())) {
            throw new IllegalArgumentException("Categoria do curso é obrigatória");
        }
    }
}
