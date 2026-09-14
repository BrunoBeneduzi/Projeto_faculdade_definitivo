package br.com.clinicaqr.domain.model;

import java.util.UUID;

public record User(UUID id, String firstName, String lastName, String email, String passwordHash) {
    public User {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("E-mail é obrigatório");
    }
}