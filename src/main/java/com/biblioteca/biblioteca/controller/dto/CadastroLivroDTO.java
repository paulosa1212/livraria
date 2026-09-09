package com.biblioteca.biblioteca.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.ISBN;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;


public record CadastroLivroDTO(
        @NotBlank(message = "nome obrigatorio")
        @Size(min = 3, max = 80, message = "tamaho fora do padrao")
        String titulo
        , @NotNull LocalDate dataPublicacao
        , @NotNull UUID idAutor

        , @ISBN String isbn
) {

}
