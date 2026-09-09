package com.biblioteca.biblioteca.controller.mapper;

import com.biblioteca.biblioteca.controller.dto.AutorDTO;
import com.biblioteca.biblioteca.entity.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);
    AutorDTO toDto(Autor autor);
}
