package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.dto.AutorDTO;
import com.biblioteca.biblioteca.entity.Autor;
import com.biblioteca.biblioteca.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService service;

    @Autowired
    public AutorController(AutorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> salvarAutor(@RequestBody AutorDTO autor) {
        var autorENtidade = autor.mapearAutor();
        service.salvar(autorENtidade);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(autorENtidade.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> buscarPorId(@PathVariable String id){
        var idAutor= UUID.fromString(id);
        Optional<Autor> autorOptional=service.buscarPorId(idAutor);
        if (autorOptional.isPresent()){
            Autor autor=autorOptional.get();
            AutorDTO dto=new AutorDTO(autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionaliade());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deletar(@PathVariable String id){
        var idAutor=UUID.fromString(id);
        Optional<Autor>autorOptional=service.buscarPorId(idAutor);
        if (autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        service.deletarAutor(autorOptional.get().getId());
        return ResponseEntity.noContent().build();
    }
    @GetMapping(params = "nome")
    public ResponseEntity<AutorDTO> buscarPorNome(@RequestParam  String nome){
        Optional<Autor>autorOptional= Optional.ofNullable(service.buscaPorNome(nome));
        if (autorOptional.isPresent()){
            Autor autor=autorOptional.get();
            AutorDTO autorDTO=new AutorDTO(autor.getId(),autor.getNome(),autor.getDataNascimento(),autor.getNacionaliade());
            return ResponseEntity.ok(autorDTO);

        }
        else {
            return ResponseEntity.notFound().build();
        }

    }
    @GetMapping
    public ResponseEntity<List<AutorDTO>> listarTodos(){
        List<Autor>autors=service.listarTodos();
        List<AutorDTO>autorDTOS=autors.stream().map(autor->new AutorDTO(autor.getId(),
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionaliade())).toList();
        return ResponseEntity.ok(autorDTOS);
    }


}
