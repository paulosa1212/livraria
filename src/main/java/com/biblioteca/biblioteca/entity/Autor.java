package com.biblioteca.biblioteca.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autores")
@Getter
@Setter
@ToString
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "nome", length = 100, nullable = false)
    @NotEmpty
    @NotBlank
    private String nome;
    @Column(name = "data_nscimento" ,nullable = false)
    private LocalDate dataNascimento;

    @Column(length = 80, nullable = false)
    @NotEmpty
    private String nacionaliade;

    @Column(name = "data_cadastro", updatable = false)
    @CreationTimestamp
    private LocalDate dataDoCadastro;

    @Column(name = "data_atualizacao")
    @UpdateTimestamp
    private LocalDate dataAtualizacao;

    @OneToMany(mappedBy = "autor")
    public List<Livro>livros;

    @Deprecated
    public Autor(){

    }

    public Autor(UUID id, String nome, LocalDate dataNascimento, String nacionaliade, LocalDate dataDoCadastro, LocalDate dataAtualizacao) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.nacionaliade = nacionaliade;
        this.dataDoCadastro = dataDoCadastro;
        this.dataAtualizacao = dataAtualizacao;
    }
}
