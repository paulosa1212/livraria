package com.biblioteca.biblioteca.entity;

import com.biblioteca.biblioteca.enuns.Roles;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String login;
    @Column
    private String senha;
    @Enumerated(EnumType.STRING)
    private Roles roles;


}
