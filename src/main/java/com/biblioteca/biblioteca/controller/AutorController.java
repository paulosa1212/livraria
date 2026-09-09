package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.controller.dto.AutorDTO;
import com.biblioteca.biblioteca.controller.mapper.AutorMapper;
import com.biblioteca.biblioteca.controller.dto.ErroResposta;
import com.biblioteca.biblioteca.entity.Autor;
import com.biblioteca.biblioteca.exceptions.OperacaoNaoPermitidaException;
import com.biblioteca.biblioteca.exceptions.RegistroDuplicadoException;
import com.biblioteca.biblioteca.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService service;
    private final AutorMapper mapper;

    @Autowired
    public AutorController(AutorService service, AutorMapper mapper) {
        this.service = service;
        this.mapper = mapper;

    }

    @GetMapping("/por-data")
    public ResponseEntity<List<AutorDTO>> listarPorDta(@RequestParam(value = "data1") LocalDate data1, @RequestParam(value = "data2") LocalDate data2) {
        List<Autor> autors = service.buscarPorData(data1, data2);
        List<AutorDTO> autorDTOS = autors.stream().map(autor -> new AutorDTO(autor.getId(), autor.getNome()
                , autor.getDataNascimento()
                , autor.getNacionalidade())).toList();
        return ResponseEntity.ok(autorDTOS);
    }

    @PostMapping
    public ResponseEntity<Object> salvarAutor(@RequestBody @Valid AutorDTO autorDTO) {



            var autorEntidade = mapper.toEntity(autorDTO);
            service.salvar(autorEntidade);


            URI locattion = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autorEntidade.getId())
                    .toUri();


            return ResponseEntity.created(locattion).build();



    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> buscarPorId(@PathVariable String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.buscarPorId(idAutor);

        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            AutorDTO dto = mapper.toDto(autor);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();

    }



    @DeleteMapping("/autor")
    public ResponseEntity<?> deletar(@RequestParam(name = "autor") Autor autor) {
        try {

            var idAutor = autor.getId();
            Optional<Autor> autorOptional = service.buscarPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            service.deletarAutor(autor);
            return ResponseEntity.noContent().build();
        } catch (OperacaoNaoPermitidaException e) {
            var erro = ErroResposta.respostaPadrao(e.getMessage());
            ResponseEntity.status(erro.status()).body(erro);
        }

        return null;
    }
    @GetMapping
    public ResponseEntity<List<AutorDTO>>listarTodos(){
        List<Autor>autors=service.listarTodos();
        List<AutorDTO>autorDTOS=autors.stream()
                .map(autor -> new AutorDTO(autor.getId()
                ,autor.getNome()
                ,autor.getDataNascimento()
                ,autor.getNacionalidade())).toList();
        return ResponseEntity.ok(autorDTOS);
    }

    @GetMapping("/por-nome")
    public ResponseEntity<AutorDTO> buscararPorNome(@RequestParam String nome) {
        List<Autor> list = service.buscaPorNome(nome);
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();


    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody @Valid AutorDTO dto) {

        try {

            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.buscarPorId(idAutor);
            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            var autor = autorOptional.get();
            autor.setNome(dto.nome());
            autor.setDataNascimento(dto.dataNascimento());
            autor.setNacionalidade(dto.nacionalidade());
            service.salvar(autor);
            return ResponseEntity.noContent().build();
        } catch (RegistroDuplicadoException e) {
            var erro = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPorId(@PathVariable UUID id) {
        try {

            service.deletarPorId(id);
            return ResponseEntity.noContent().build();

        } catch (OperacaoNaoPermitidaException e) {
            var erro = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }


    }

    @GetMapping("/buscar")
    public ResponseEntity<List<AutorDTO>> buscaDinamica(@RequestParam(required = false) String nome
            , @RequestParam(required = false) String nacionalidade
            , @RequestParam(required = false) LocalDate dataNascimento) {


        List<Autor> autors = service.pesquisaDinamica(nome, nacionalidade, dataNascimento);
        List<AutorDTO> autorDTOS = autors.stream().map(
                autor -> new AutorDTO(autor.getId()
                        , autor.getNome()
                        , autor.getDataNascimento()
                        , autor.getNacionalidade()
                )).toList();
        return ResponseEntity.ok(autorDTOS);

    }

}
