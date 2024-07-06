package com.forumhub.service;

import com.forumhub.DTO.TopicoDTO;
import com.forumhub.domain.Topico;

import com.forumhub.repository.CursoRepository;
import com.forumhub.repository.TopicoRepository;
import com.forumhub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public TopicoDTO criarTopico(TopicoDTO topicoDTO) {
        validarTopico(topicoDTO);
        Topico topico = new Topico();
        topico.setTitulo(topicoDTO.titulo());
        topico.setMensagem(topicoDTO.mensagem());
        topico.setEstado(topicoDTO.estado());
        topico.setAutor(usuarioRepository.findById(topicoDTO.autorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado")));
        topico.setCurso(cursoRepository.findById(topicoDTO.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado")));
        topico = topicoRepository.save(topico);
        return new TopicoDTO(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.isEstado(), topico.getAutor().getId(), topico.getCurso().getId());
    }

    public List<TopicoDTO> listarTopicos() {
        List<Topico> topicos = topicoRepository.findAll();
        return topicos.stream()
                .map(topico -> new TopicoDTO(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.isEstado(), topico.getAutor().getId(), topico.getCurso().getId()))
                .toList();
    }

    public TopicoDTO atualizarTopico(Long id, TopicoDTO topicoDTO) {
        validarTopico(topicoDTO);
        Optional<Topico> topicoOpt = topicoRepository.findById(id);
        if (topicoOpt.isPresent()) {
            Topico topico = topicoOpt.get();
            topico.setTitulo(topicoDTO.titulo());
            topico.setMensagem(topicoDTO.mensagem());
            topico.setEstado(topicoDTO.estado());
            topico.setAutor(usuarioRepository.findById(topicoDTO.autorId())
                    .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado")));
            topico.setCurso(cursoRepository.findById(topicoDTO.cursoId())
                    .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado")));
            topico = topicoRepository.save(topico);
            return new TopicoDTO(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.isEstado(), topico.getAutor().getId(), topico.getCurso().getId());
        } else {
            throw new IllegalArgumentException("Tópico não encontrado");
        }
    }

    public void deletarTopico(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new IllegalArgumentException("Tópico não encontrado");
        }
        topicoRepository.deleteById(id);
    }

    private void validarTopico(TopicoDTO topicoDTO) {
        if (!StringUtils.hasText(topicoDTO.titulo())) {
            throw new IllegalArgumentException("Título do tópico é obrigatório");
        }
        if (!StringUtils.hasText(topicoDTO.mensagem())) {
            throw new IllegalArgumentException("Mensagem do tópico é obrigatória");
        }
        if (topicoDTO.autorId() == null) {
            throw new IllegalArgumentException("ID do autor é obrigatório");
        }
        if (topicoDTO.cursoId() == null) {
            throw new IllegalArgumentException("ID do curso é obrigatório");
        }
    }
}
