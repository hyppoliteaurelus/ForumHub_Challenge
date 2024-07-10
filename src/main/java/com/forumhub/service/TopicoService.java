package com.forumhub.service;

import com.forumhub.DTO.TopicoDTO;
import com.forumhub.DTO.UsuarioDTO;
import com.forumhub.domain.Topico;
import com.forumhub.exception.ResourceNotFoundException;
import com.forumhub.repository.TopicoRepository;
import com.forumhub.repository.UsuarioRepository;
import com.forumhub.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    @Autowired
    public TopicoService(TopicoRepository topicoRepository, UsuarioRepository usuarioRepository, CursoRepository cursoRepository) {
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }

    public TopicoDTO cadastrarTopico(TopicoDTO topicoDTO) {
        Topico topico = new Topico();
        topico.setTitulo(topicoDTO.titulo());
        topico.setMensagem(topicoDTO.mensagem());
        topico.setDataCriacao(LocalDateTime.now()); // Definindo a data de criação como o momento atual
        topico.setEstado(topicoDTO.estado());

        // Busca o autor no banco de dados pelo ID e associa ao tópico
        topico.setAutor(usuarioRepository.findById(topicoDTO.autorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado")));

        // Busca o curso no banco de dados pelo ID e associa ao tópico
        topico.setCurso(cursoRepository.findById(topicoDTO.cursoId())
                .orElseThrow(() -> new  ResourceNotFoundException("Curso não encontrado")));

        Topico savedTopico = topicoRepository.save(topico);
        return converterParaDTO(savedTopico);
    }


    public List<TopicoDTO> listarTodos() {
        List<Topico> topicos = topicoRepository.findAll();
        return topicos.stream()
                .map(topico -> {
                    Long autorId = topico.getAutor() != null ? topico.getAutor().getId() : null;
                    Long cursoId = topico.getCurso() != null ? topico.getCurso().getId() : null;
                    return new TopicoDTO(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(),topico.isEstado(), autorId, cursoId);
                })
                .collect(Collectors.toList());
    }

    public TopicoDTO atualizarTopico(Long id, TopicoDTO topicoDTO) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException("Tópico não encontrado"));

        topico.setTitulo(topicoDTO.titulo());
        topico.setMensagem(topicoDTO.mensagem());
        // A data de criação e o estado geralmente não são atualizados diretamente

        // Atualiza o autor do tópico buscando no banco de dados pelo ID
        topico.setAutor(usuarioRepository.findById(topicoDTO.autorId())
                .orElseThrow(() -> new  ResourceNotFoundException("Autor não encontrado")));

        // Atualiza o curso do tópico buscando no banco de dados pelo ID
        topico.setCurso(cursoRepository.findById(topicoDTO.cursoId())
                .orElseThrow(() -> new  ResourceNotFoundException("Curso não encontrado")));

        Topico updatedTopico = topicoRepository.save(topico);
        return converterParaDTO(updatedTopico);
    }

    public void deletarTopico(Long id) {
        topicoRepository.deleteById(id);
    }

    private TopicoDTO converterParaDTO(Topico topico) {
        return new TopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.isEstado(),
                topico.getAutor().getId(),
                topico.getCurso().getId()
        );
    }
}





