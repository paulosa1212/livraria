package com.biblioteca.biblioteca.controller.mapper;


import com.biblioteca.biblioteca.controller.dto.UsuarioDTOEntrada;
import com.biblioteca.biblioteca.controller.dto.UsuarioDTOSaida;
import com.biblioteca.biblioteca.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

     Usuario toentity(UsuarioDTOEntrada dto);

     UsuarioDTOSaida toDto(Usuario usuario);
}
