package com.biblioteca.biblioteca.controller.mapper;

import com.biblioteca.biblioteca.controller.dto.CadastroLivroDTO;
import com.biblioteca.biblioteca.controller.dto.LivroDTO;
import com.biblioteca.biblioteca.entity.Livro;
import com.biblioteca.biblioteca.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {
    @Autowired
    AutorRepository autorRepository;


    @Mapping(target = "autor", expression = "java(autorRepository.findById(dto.idAutor()).orElse(null))")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    @Mapping(source = "autor.nome", target = "autorNome")
    public abstract LivroDTO toDto(Livro livro);

}
