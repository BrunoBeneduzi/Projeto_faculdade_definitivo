package br.com.clinicaqr.dominio.porta;
import br.com.clinicaqr.dominio.modelo.PerfilClinico;
import java.util.*;
public interface RepositorioPerfilClinico {
 PerfilClinico salvar(PerfilClinico perfil); Optional<PerfilClinico> buscarPorUsuarioId(UUID usuarioId);
 Optional<PerfilClinico> buscarPorIdPublico(UUID idPublico); void excluirPorUsuarioId(UUID usuarioId);
}