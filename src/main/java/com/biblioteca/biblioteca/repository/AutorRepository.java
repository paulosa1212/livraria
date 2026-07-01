package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

    public Autor findByNome(String nome);
    public Optional<Autor> findByNomeOrNacionaliade(String nome, String nacionalidade);
}
