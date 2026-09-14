package br.com.clinicaqr.infraestrutura.persistencia;
import br.com.clinicaqr.dominio.modelo.Usuario;
import br.com.clinicaqr.dominio.porta.RepositorioUsuario;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public class AdaptadorRepositorioUsuario implements RepositorioUsuario {
 private final RepositorioUsuarioSpringData repositorio;
 public AdaptadorRepositorioUsuario(RepositorioUsuarioSpringData repositorio){this.repositorio=repositorio;}
 public Usuario salvar(Usuario usuario){return paraDominio(repositorio.save(paraEntidade(usuario)));}
 public Optional<Usuario> buscarPorEmail(String email){return repositorio.findByEmail(email).map(this::paraDominio);}
 public Optional<Usuario> buscarPorId(UUID id){return repositorio.findById(id).map(this::paraDominio);}
 public boolean existePorEmail(String email){return repositorio.existsByEmail(email);}
 private EntidadeUsuario paraEntidade(Usuario u){EntidadeUsuario e=new EntidadeUsuario();e.id=u.id();e.nome=u.nome();e.sobrenome=u.sobrenome();e.email=u.email();e.senhaHash=u.senhaHash();return e;}
 private Usuario paraDominio(EntidadeUsuario e){return new Usuario(e.id,e.nome,e.sobrenome,e.email,e.senhaHash);}
}