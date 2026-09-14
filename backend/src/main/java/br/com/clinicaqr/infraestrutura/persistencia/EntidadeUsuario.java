package br.com.clinicaqr.infraestrutura.persistencia;
import jakarta.persistence.*;
import java.util.UUID;
@Entity @Table(name="usuarios")
public class EntidadeUsuario {
 @Id public UUID id;
 @Column(nullable=false) public String nome;
 @Column(nullable=false) public String sobrenome;
 @Column(nullable=false,unique=true) public String email;
 @Column(name="senha_hash",nullable=false) public String senhaHash;
 protected EntidadeUsuario(){}
}