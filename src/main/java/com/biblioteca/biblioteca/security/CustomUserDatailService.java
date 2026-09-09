package com.biblioteca.biblioteca.security;

import com.biblioteca.biblioteca.entity.Usuario;
import com.biblioteca.biblioteca.enuns.Roles;
import com.biblioteca.biblioteca.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomUserDatailService implements UserDetailsService {
    private final UsuarioService service;

//    @Override
//    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
//
//        Usuario usuario = service.obterPorLogin(nome)
//                .orElseThrow(() -> new UsernameNotFoundException("Usuário não existe"));
//        System.out.println("USUÁRIO ENCONTRADO: " + usuario.getNome());
//        System.out.println("SENHA NO BANCO: " + usuario.getSenha());
//        return User.builder()
//                .username(usuario.getNome())
//                .password(usuario.getSenha())
//                .roles(usuario.getRoles()
//                        .stream()
//                        .map(Roles::name)
//                        .toArray(String[]::new))
//                .build();
//    }
@Override
public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {



    Usuario usuario = service.obterPorLogin(nome)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não existe"));

    System.out.println(">>> USUARIO ENCONTRADO: " + usuario.getLogin());
    System.out.println(">>> ROLE: " + usuario.getRoles());

    return User.builder()
            .username(usuario.getLogin())
            .password(usuario.getSenha())
            .roles(String.valueOf(usuario.getRoles()))
            .build();
}
}
