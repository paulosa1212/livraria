package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.entity.Autor;


import com.biblioteca.biblioteca.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private AutorRepository repository;

    @Autowired
    public AutorService(AutorRepository repository) {
        this.repository = repository;
    }


    public Autor salvar(Autor autor) {

        return repository.save(autor);

    }


    public List<Autor> listarTodos() {
        return repository.findAll();
    }

    public Optional<Autor> buscarPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletarAutor(UUID id) {
        repository.findById(id);
    }

    public List<Autor> buscaPorNome(String nome) {
        return repository.findByNome(nome);
    }

    public void atualizar(UUID id, Autor autor) {
        if (id == null) {
            repository.save(autor);

        } else {
            autor.setId(id);
            repository.save(autor);
        }

    }

    public List<Autor> buscarNomeOuNacionalidade(String nome, String nacionalidade) {
        if (nome != null && nacionalidade != null) {
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);

        }
        if (nome != null) {
            return repository.findByNome(nome);

        }
        if (nacionalidade != null) {
           return repository.findByNacionalidade(nacionalidade);

        }

        return repository.findAll();

    }
}
