package br.com.clinicaqr.application;

import br.com.clinicaqr.domain.model.User;
import br.com.clinicaqr.domain.port.UserRepository;
import br.com.clinicaqr.infrastructure.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthService(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
        this.users = users; this.encoder = encoder; this.jwt = jwt;
    }

    public AuthResult register(String firstName, String lastName, String email, String password) {
        String normalized = email.trim().toLowerCase();
        if (users.existsByEmail(normalized)) throw new ConflictException("E-mail já cadastrado");
        User user = users.save(new User(UUID.randomUUID(), firstName.trim(), lastName.trim(),
                normalized, encoder.encode(password)));
        return new AuthResult(jwt.generate(user), user.firstName(), user.lastName(), user.email());
    }

    public AuthResult login(String email, String password) {
        User user = users.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new UnauthorizedException("E-mail ou senha inválidos"));
        if (!encoder.matches(password, user.passwordHash()))
            throw new UnauthorizedException("E-mail ou senha inválidos");
        return new AuthResult(jwt.generate(user), user.firstName(), user.lastName(), user.email());
    }

    public record AuthResult(String token, String firstName, String lastName, String email) {}
}