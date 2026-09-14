package br.com.clinicaqr.dominio.modelo;
import java.util.UUID;
public record Usuario(UUID id, String nome, String sobrenome, String email, String senhaHash) {
    public Usuario { if (email == null || email.isBlank()) throw new IllegalArgumentException("E-mail é obrigatório"); }
}