package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.controller.dto.CadastroLivroDTO;
import com.biblioteca.biblioteca.controller.dto.LivroDTO;
import com.biblioteca.biblioteca.controller.mapper.LivroMapper;
import com.biblioteca.biblioteca.entity.Livro;
import com.biblioteca.biblioteca.service.LivroService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livros")
@AllArgsConstructor
public class LivroController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;



    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody CadastroLivroDTO dto) {
        var livro = livroMapper.toEntity(dto);
        livroService.salvar(livro);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(livro.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<LivroDTO>> listarTodos() {
        var livros = livroService.listarTodos()
                .stream()
                .map(livroMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> buscarPorId(@PathVariable UUID id) {
        var livro = livroService.buscarPorId(id);
        return ResponseEntity.ok(livroMapper.toDto(livro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @RequestBody CadastroLivroDTO dto) {
        var livro = livroService.buscarPorId(id);
        var autor = livroMapper.toEntity(dto).getAutor();
        livro.setTitulo(dto.titulo());
        livro.setDataPublicacao(dto.dataPublicacao());
        livro.setAutor(autor);
        livro.setIsbn(dto.isbn());
        livroService.salvar(livro);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        livroService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
