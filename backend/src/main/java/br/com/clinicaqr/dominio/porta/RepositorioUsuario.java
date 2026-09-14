package br.com.clinicaqr.dominio.porta;
import br.com.clinicaqr.dominio.modelo.Usuario;
import java.util.*;
public interface RepositorioUsuario {
 Usuario salvar(Usuario usuario); Optional<Usuario> buscarPorEmail(String email);
 Optional<Usuario> buscarPorId(UUID id); boolean existePorEmail(String email);
}