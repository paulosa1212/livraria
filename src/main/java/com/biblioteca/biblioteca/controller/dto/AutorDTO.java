package com.biblioteca.biblioteca.controller.dto;

import com.biblioteca.biblioteca.entity.Autor;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "nome obrigatorio")
        @Size(min = 3, max = 80, message = "tamaho fora do padrao")
        String nome
        , @NotNull(message = "data obrigatoria")
        @Past(message = "a data deve ser uma data anterior a data atual")
        LocalDate dataNascimento
        , @NotBlank(message = "campo obrigatorio") String nacionalidade) {


    public Autor mapearAutor() {
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }

}
