package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

    public List<Autor> findByNome(String nome);
    public List<Autor> findByNacionalidade(String nacionalidade);
    public List<Autor> findByNomeAndNacionalidade(String nome,String nacionalidade);
}
