package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.entity.Livro;
import com.biblioteca.biblioteca.exceptions.RegistroNaoEncontradoException;
import com.biblioteca.biblioteca.repository.LivroRepository;
import com.biblioteca.biblioteca.validator.LivroValidator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator validator;

    public Livro salvar(Livro livro){
        validator.validar(livro);
        return livroRepository.save(livro);
    }

    public List<Livro>listarTodos(){
        return livroRepository.findAll();
    }

    public void deletarPorId(UUID id){
        if (!livroRepository.existsById(id)){
            throw new RegistroNaoEncontradoException("livro nao encontrado");
        }
        livroRepository.deleteById(id);
    }
    public Livro buscarPorId(UUID id){
      return livroRepository.findById(id).orElseThrow(()->new RegistroNaoEncontradoException("livro nao encontrado"));
    }

}
