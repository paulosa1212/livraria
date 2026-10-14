package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.controller.dto.UsuarioDTOEntrada;
import com.biblioteca.biblioteca.entity.Usuario;
import com.biblioteca.biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;


    public Usuario salvar(Usuario usuario) {
        var senha = encoder.encode(usuario.getSenha());
        usuario.setSenha(senha);
       return repository.save(usuario);


    }


    public Optional<Usuario> obterPorLogin(String login) {
        Optional<Usuario>usuarioOptional=repository.findByLogin(login);
        if (usuarioOptional.isEmpty()){
            throw new UsernameNotFoundException("nao encontrado");
        }
        return usuarioOptional;

    }
    public List<Usuario>findAll(){
        return repository.findAll();
    }

    public Usuario buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("nao encontrado"));
    }

    public void deletarPorId(UUID id) {
        if (!repository.existsById(id)) {
            throw new UsernameNotFoundException("nao encontrado");
        }
        repository.deleteById(id);
    }
}
