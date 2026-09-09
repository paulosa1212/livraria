package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.BibliotecaApplication;
import com.biblioteca.biblioteca.entity.Autor;


import com.biblioteca.biblioteca.exceptions.OperacaoNaoPermitidaException;
import com.biblioteca.biblioteca.exceptions.RegistroDuplicadoException;
import com.biblioteca.biblioteca.repository.AutorRepository;
import com.biblioteca.biblioteca.repository.LivroRepository;
import com.biblioteca.biblioteca.validator.AutorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;

    @Autowired
    public AutorService(AutorRepository repository, AutorValidator validator,LivroRepository livroRepository) {
        this.repository = repository;
        this.validator = validator;
        this.livroRepository=livroRepository;
    }


    public Autor salvar(Autor autor) {
        validator.validar(autor);
        return repository.save(autor);

    }


    public List<Autor> listarTodos() {
        return repository.findAll();
    }

    public Optional<Autor> buscarPorId(UUID id) {
        return repository.findById(id);
    }

    public Void deletarAutor(Autor autor) {
        if (autorPossuiLivro(autor)){

            throw new OperacaoNaoPermitidaException("NAO É PERMITODO DELETAR AUTOR QUE TENHA LIVRO");
        }
        repository.deleteById(autor.getId());

        return null;
    }

    public List<Autor> buscaPorNome(String nome) {
        return repository.findByNome(nome);
    }

    public void atualizar(UUID id, Autor autor) {
        if (id == null) {
            repository.save(autor);

        } else {
            validator.validar(autor);
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
    public boolean autorPossuiLivro(Autor autor){
        boolean possui = livroRepository.existsByAutor(autor);

        System.out.println("Autor: " + autor.getNome());
        System.out.println("Possui livro: " + possui);

        return possui;
    }

    public void deletarPorId(UUID id){
        Autor autor =repository.findById(id).orElseThrow(()->new RuntimeException());

        if (autorPossuiLivro(autor)){

            throw new OperacaoNaoPermitidaException("nao é permitido delatar autor que tenha livros");
        }
        repository.deleteById(id);
    }
    public List<Autor> buscarPorData(LocalDate data,LocalDate data2){
       List<Autor>autores=repository.findByDataNascimentoBetween(data,data2);
       return autores;
    }

  public List<Autor> pesquisaDinamica(String nome,String nacionalidade,LocalDate dataNascimento){
        var autor=new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        autor.setDataNascimento(dataNascimento);
        ExampleMatcher matcher=ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor>autorExample=Example.of(autor,matcher);
        return repository.findAll(autorExample);

  }



}
