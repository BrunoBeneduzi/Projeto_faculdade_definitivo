package br.com.clinicaqr.infraestrutura.persistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
interface RepositorioUsuarioSpringData extends JpaRepository<EntidadeUsuario,UUID>{
 Optional<EntidadeUsuario> findByEmail(String email); boolean existsByEmail(String email);
}
interface RepositorioPerfilClinicoSpringData extends JpaRepository<EntidadePerfilClinico,UUID>{
 Optional<EntidadePerfilClinico> findByUsuarioId(UUID usuarioId);
 Optional<EntidadePerfilClinico> findByIdPublico(UUID idPublico);
 void deleteByUsuarioId(UUID usuarioId);
}