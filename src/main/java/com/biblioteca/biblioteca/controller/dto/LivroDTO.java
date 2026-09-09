package com.biblioteca.biblioteca.controller.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.UUID;

public record LivroDTO(
        UUID id,
        @NotBlank(message = "o titulo é obrigatorio")
        String titulo,
        LocalDate dataPublicacao,

        String isbn,
        @NotBlank(message = "o autor é obrigatorio")
        String autorNome) {
}
