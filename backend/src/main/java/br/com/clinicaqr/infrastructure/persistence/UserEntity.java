package br.com.clinicaqr.infrastructure.persistence;

import jakarta.persistence.*;
import java.util.UUID;

@Entity @Table(name="users")
public class UserEntity {
    @Id public UUID id;
    @Column(name="first_name", nullable=false) public String firstName;
    @Column(name="last_name", nullable=false) public String lastName;
    @Column(nullable=false, unique=true) public String email;
    @Column(name="password_hash", nullable=false) public String passwordHash;
    protected UserEntity() {}
}