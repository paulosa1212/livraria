package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.entity.Autor;
import com.biblioteca.biblioteca.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    Boolean existsByAutor(Autor autor);

    Optional<Livro>findByIsbn(String isbn);
}
