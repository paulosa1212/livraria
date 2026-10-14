package com.biblioteca.biblioteca.controller.common;

import com.biblioteca.biblioteca.controller.dto.ErroCampo;
import com.biblioteca.biblioteca.controller.dto.ErroResposta;
import com.biblioteca.biblioteca.exceptions.ErroNoLogimException;
import com.biblioteca.biblioteca.exceptions.RegistroDuplicadoException;
import com.biblioteca.biblioteca.exceptions.RegistroNaoEncontradoException;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice //captura exception
public class GlobalExceptionHandler {

    //OUTRO METHOD QUE TBM DA CERTO, MAS COM A RESPOSTA DIFERENTE
//    @ExceptionHandler(MethodArgumentNotValidException.class)// essa anotacao é que captura a exception
//    public ResponseEntity<ErroResposta> except(MethodArgumentNotValidException ex) {
//        List<FieldError> fieldErrors = ex.getFieldErrors();
//        List<ErroCampo>erroCampos=fieldErrors.stream()
//                .map(fieldError -> new ErroCampo(fieldError.getField(),fieldError.getDefaultMessage())).toList();
//        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErroResposta(
//                HttpStatus.UNPROCESSABLE_ENTITY.value()
//                ,"ERROR VALIDATOR"
//                ,erroCampos
//        ));
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta exception(MethodArgumentNotValidException e) {
        final var fieldErrors = e.getFieldErrors();
        List<ErroCampo> erroCampos = fieldErrors.stream().map(f -> new ErroCampo(f.getField(), f.getDefaultMessage())).toList();
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "ERRO VALIDAÇAO", erroCampos);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta erroServidor(Exception e) {
        e.printStackTrace();
        return new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "ocorreu um erro no servidor"
                , List.of()
        );

    }

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta naoEncontrsdo(RegistroNaoEncontradoException e) {
        return ErroResposta.naoEncontrado(e.getMessage());

    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta registroDuplicadoException(RegistroDuplicadoException e) {
        return ErroResposta.conflito(e.getMessage());
    }
    @ExceptionHandler(ErroNoLogimException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErroResposta naoAutorizado(ErroNoLogimException e){
        return ErroResposta.naoAutorizado(e.getMessage());
    }


}
