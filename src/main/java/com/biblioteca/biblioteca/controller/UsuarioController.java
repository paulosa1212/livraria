package com.biblioteca.biblioteca.controller;


import com.biblioteca.biblioteca.controller.dto.UsuarioDTOEntrada;
import com.biblioteca.biblioteca.controller.dto.UsuarioDTOSaida;
import com.biblioteca.biblioteca.controller.mapper.UsuarioMapper;
import com.biblioteca.biblioteca.entity.Usuario;
import com.biblioteca.biblioteca.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;


    //    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public void salvar(@RequestBody @Valid UsuarioDTO usuarioDTO) {
//        var usuario=mapper.toentity(usuarioDTO);
//        service.salvar(usuario);
//    }
    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid UsuarioDTOEntrada dto) {
        var user = mapper.toentity(dto);
        service.salvar(user);

        URI url = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(url).build();


    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTOSaida>> findAll() {
        List<Usuario> usuarios = service.findAll();
        List<UsuarioDTOSaida> usuarioDTOS = usuarios.stream().map(
                usuario -> new UsuarioDTOSaida(
                        usuario.getLogin()
                        , usuario.getRoles()
                )
        ).toList();

        return ResponseEntity.ok(usuarioDTOS);
    }
}
