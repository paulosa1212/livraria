package com.biblioteca.biblioteca.validator;

import com.biblioteca.biblioteca.entity.Livro;
import com.biblioteca.biblioteca.exceptions.RegistroDuplicadoException;
import com.biblioteca.biblioteca.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {
    private final LivroRepository repository;


    public void validar(Livro livro){

        if (registroDuplicado(livro)){
            throw new RegistroDuplicadoException("ja existe livro CADASTRADO COM ESTE ISBN");


        }
    }
    public Boolean registroDuplicado(Livro livro){
        Optional<Livro>livroOptional=repository.findByIsbn(livro.getIsbn());

        if (livro.getIsbn()==null){
            return livroOptional.isPresent();
        }
        return livroOptional.filter(l-> l.getId().equals(livroOptional.get().getId())).isPresent();

    }

}
