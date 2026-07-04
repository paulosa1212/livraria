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
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autorDTO) {
        var author = autorDTO.mapearAutor();
        service.salvar(author);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(author.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> buscarPorId(@PathVariable String id) {
        var idAuthor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.buscarPorId(idAuthor);

        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            AutorDTO autorDTO = new AutorDTO(autor.getId()
                    , autor.getNome()
                    , autor.getDataNascimento()
                    , autor.getNacionalidade());
            return ResponseEntity.ok(autorDTO);
        }
        return ResponseEntity.noContent().build();
    }

//    @GetMapping
//    public ResponseEntity<List<AutorDTO>> listarTodos() {
//        List<Autor> lista = service.listarTodos();
//        List<AutorDTO> autorDTOS = lista.stream().map(a -> new AutorDTO(a.getId()
//                , a.getNome()
//                , a.getDataNascimento()
//                , a.getNacionaliade())).toList();
//        return ResponseEntity.ok(autorDTOS);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.buscarPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deletarAutor(autorOptional.get().getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable String id, @RequestBody AutorDTO autorDTO) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.buscarPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Autor autor = autorOptional.get();
        autor.setNome(autorDTO.nome());
        autor.setDataNascimento(autorDTO.dataNascimento());
        autor.setNacionalidade(autorDTO.nacionalidade());
        return ResponseEntity.noContent().build();
    }

//    @GetMapping(params = "nome")
//    public ResponseEntity<List<AutorDTO>> buscarPorNome(@RequestParam String nome) {
//        Optional<List<Autor>> autorOptional = Optional.ofNullable(service.buscaPorNome(nome));
//        if (autorOptional.isPresent()) {
//           List <Autor> autor = autorOptional.get();
//           List <AutorDTO> autorDTO = autor.stream().map(a->new AutorDTO(a.getId()
//           ,a.getNome()
//           ,a.getDataNascimento()
//           ,a.getNacionaliade())).toList();
//            return ResponseEntity.ok(autorDTO);
//
//        }
//        return ResponseEntity.notFound().build();
//
//    }
    @GetMapping
    public ResponseEntity<List<AutorDTO>> listarComParametros (@RequestParam(required = false, name = "nome") String nome
    ,@RequestParam(required = false,name = "nacionalidade") String nacionalidade){
        var autors=service.buscarNomeOuNacionalidade(nome,nacionalidade);
        if (autors.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        List<AutorDTO> autorDTO=autors.stream().map(autor -> new AutorDTO(autor.getId()
        ,autor.getNome()
        ,autor.getDataNascimento()
        ,autor.getNacionalidade())).toList();
        return ResponseEntity.ok(autorDTO);
    }

}
