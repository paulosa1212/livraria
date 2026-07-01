package com.biblioteca.biblioteca.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;
@Getter
@Setter
@ToString
@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "titulo_livro", length = 120, nullable = false)
    @NotEmpty
    private String titulo;

    @Column(name = "data_publicacao")
    @Past
    private LocalDate dataPublicacao;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;

    private String isbn;

    @Column(name = "data_cadastro", updatable = false)
    @CreationTimestamp
    private LocalDate dataCadastro;

    @Deprecated
    public Livro() {

    }

    public Livro(UUID id, String titulo, LocalDate dataPublicacao, Autor autor, String isbn, LocalDate dataCadastro) {
        this.id = id;
        this.titulo = titulo;
        this.dataPublicacao = dataPublicacao;
        this.autor = autor;
        this.isbn = isbn;
        this.dataCadastro = dataCadastro;
    }
}
