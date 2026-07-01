package com.biblioteca.biblioteca.dto;

import com.biblioteca.biblioteca.entity.Autor;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(UUID id,String nome, LocalDate dataNascimento, String nacionalidade) {


    public Autor mapearAutor(){
        Autor autor=new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionaliade(this.nacionalidade);
        return autor;
    }

}
