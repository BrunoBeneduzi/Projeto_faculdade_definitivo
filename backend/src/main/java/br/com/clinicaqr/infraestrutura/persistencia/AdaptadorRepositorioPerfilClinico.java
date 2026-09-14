package br.com.clinicaqr.infraestrutura.persistencia;
import br.com.clinicaqr.dominio.modelo.PerfilClinico;
import br.com.clinicaqr.dominio.porta.RepositorioPerfilClinico;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public class AdaptadorRepositorioPerfilClinico implements RepositorioPerfilClinico {
 private final RepositorioPerfilClinicoSpringData repositorio;
 public AdaptadorRepositorioPerfilClinico(RepositorioPerfilClinicoSpringData repositorio){this.repositorio=repositorio;}
 public PerfilClinico salvar(PerfilClinico perfil){return paraDominio(repositorio.save(paraEntidade(perfil)));}
 public Optional<PerfilClinico> buscarPorUsuarioId(UUID id){return repositorio.findByUsuarioId(id).map(this::paraDominio);}
 public Optional<PerfilClinico> buscarPorIdPublico(UUID id){return repositorio.findByIdPublico(id).map(this::paraDominio);}
 public void excluirPorUsuarioId(UUID id){repositorio.deleteByUsuarioId(id);}
 private EntidadePerfilClinico paraEntidade(PerfilClinico p){
  EntidadePerfilClinico e=new EntidadePerfilClinico();e.id=p.id();e.usuarioId=p.usuarioId();e.idPublico=p.idPublico();e.nome=p.nome();e.sobrenome=p.sobrenome();
  e.sexo=p.sexo();e.contatoEmergencia=p.contatoEmergencia();e.tipoSanguineo=p.tipoSanguineo();e.alergias=new ArrayList<>(p.alergias());
  e.medicamentos=new ArrayList<>(p.medicamentos());e.doencas=new ArrayList<>(p.doencas());e.cirurgias=new ArrayList<>(p.cirurgias());e.senhaPublicaHash=p.senhaPublicaHash();return e;
 }
 private PerfilClinico paraDominio(EntidadePerfilClinico e){return new PerfilClinico(e.id,e.usuarioId,e.idPublico,e.nome,e.sobrenome,e.sexo,e.contatoEmergencia,e.tipoSanguineo,e.alergias,e.medicamentos,e.doencas,e.cirurgias,e.senhaPublicaHash);}
}