package com.biblioteca.biblioteca.controller.dto;

import com.biblioteca.biblioteca.entity.Usuario;
import com.biblioteca.biblioteca.enuns.Roles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

import java.util.List;


public record UsuarioDTOEntrada(
        @NotBlank(message = "nome obrigatorio") String login,
                        @Size(min = 6,message = "a senha deve ter pelo menos 6 digitos") String senha,
                        @NotBlank(message = "digite as permiçoes do usuario") Roles roles) {

    public Usuario mapearUsuario(){
        Usuario usuario=new Usuario();
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setRoles(roles);
        return usuario;
    }

}
