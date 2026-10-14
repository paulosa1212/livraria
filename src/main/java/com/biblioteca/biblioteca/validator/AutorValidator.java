package com.biblioteca.biblioteca.validator;

import com.biblioteca.biblioteca.entity.Autor;
import com.biblioteca.biblioteca.exceptions.RegistroDuplicadoException;
import com.biblioteca.biblioteca.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.plaf.PanelUI;
import java.util.Optional;

@Component
public class AutorValidator {
    private AutorRepository repository;

    @Autowired
    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor){
        if (existeAutor(autor)){
            throw getEsteAutorJaEstaCadastrado();
        }
    }

    private static RegistroDuplicadoException getEsteAutorJaEstaCadastrado() {
        return new RegistroDuplicadoException("este autor ja esta cadastrado");
    }

    private boolean existeAutor(Autor autor){
        Optional<Autor>possivelAutor=repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome()
                ,autor.getDataNascimento()
                ,autor.getNacionalidade()
        );

      if (autor.getId()==null){
          return possivelAutor.isPresent();
      }
      return !autor.getId().equals(possivelAutor.get().getId())&& possivelAutor.isPresent();

    }


}
