package br.com.clinicaqr.infrastructure.persistence;

import br.com.clinicaqr.domain.model.User;
import br.com.clinicaqr.domain.port.UserRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class UserRepositoryAdapter implements UserRepository {
    private final SpringDataUserRepository repository;
    public UserRepositoryAdapter(SpringDataUserRepository repository) { this.repository=repository; }
    public User save(User u) { UserEntity e=toEntity(u); return toDomain(repository.save(e)); }
    public Optional<User> findByEmail(String email) { return repository.findByEmail(email).map(this::toDomain); }
    public Optional<User> findById(UUID id) { return repository.findById(id).map(this::toDomain); }
    public boolean existsByEmail(String email) { return repository.existsByEmail(email); }
    private UserEntity toEntity(User u) { UserEntity e=new UserEntity(); e.id=u.id(); e.firstName=u.firstName(); e.lastName=u.lastName(); e.email=u.email(); e.passwordHash=u.passwordHash(); return e; }
    private User toDomain(UserEntity e) { return new User(e.id,e.firstName,e.lastName,e.email,e.passwordHash); }
}