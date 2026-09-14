package br.com.clinicaqr.aplicacao;
import br.com.clinicaqr.dominio.modelo.*;
import br.com.clinicaqr.dominio.porta.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
@Service
public class ServicoPerfilClinico {
 private final RepositorioPerfilClinico repositorioPerfis; private final RepositorioUsuario repositorioUsuarios;
 private final PasswordEncoder codificadorSenha; private final String urlBasePublica;
 public ServicoPerfilClinico(RepositorioPerfilClinico repositorioPerfis,RepositorioUsuario repositorioUsuarios,
  PasswordEncoder codificadorSenha,@Value("${aplicacao.url-base-publica}")String urlBasePublica){
  this.repositorioPerfis=repositorioPerfis;this.repositorioUsuarios=repositorioUsuarios;this.codificadorSenha=codificadorSenha;this.urlBasePublica=urlBasePublica;
 }
 @Transactional public PerfilClinico salvar(UUID usuarioId,PerfilClinico entrada,String senhaPublica){
  Usuario usuario=repositorioUsuarios.buscarPorId(usuarioId).orElseThrow(()->new ExcecaoNaoEncontrado("Usuário não encontrado"));
  PerfilClinico anterior=repositorioPerfis.buscarPorUsuarioId(usuarioId).orElse(null);
  UUID id=anterior==null?UUID.randomUUID():anterior.id(); UUID idPublico=anterior==null?UUID.randomUUID():anterior.idPublico();
  String hash=senhaPublica!=null&&!senhaPublica.isBlank()?codificadorSenha.encode(senhaPublica):anterior==null?null:anterior.senhaPublicaHash();
  if(hash==null)throw new IllegalArgumentException("Senha pública é obrigatória");
  return repositorioPerfis.salvar(new PerfilClinico(id,usuarioId,idPublico,valor(entrada.nome(),usuario.nome()),valor(entrada.sobrenome(),usuario.sobrenome()),
   entrada.sexo(),entrada.contatoEmergencia(),entrada.tipoSanguineo(),entrada.alergias(),entrada.medicamentos(),entrada.doencas(),entrada.cirurgias(),hash));
 }
 public PerfilClinico buscarMeu(UUID usuarioId){return repositorioPerfis.buscarPorUsuarioId(usuarioId).orElseThrow(()->new ExcecaoNaoEncontrado("Cadastro clínico não encontrado"));}
 public PerfilClinico acessarPublico(UUID idPublico,String senha){
  PerfilClinico perfil=repositorioPerfis.buscarPorIdPublico(idPublico).orElseThrow(()->new ExcecaoNaoEncontrado("Link público indisponível"));
  if(!codificadorSenha.matches(senha,perfil.senhaPublicaHash()))throw new ExcecaoNaoAutorizado("Senha pública inválida");return perfil;
 }
 @Transactional public void excluir(UUID usuarioId){
  if(repositorioPerfis.buscarPorUsuarioId(usuarioId).isEmpty())throw new ExcecaoNaoEncontrado("Cadastro clínico não encontrado");
  repositorioPerfis.excluirPorUsuarioId(usuarioId);
 }
 public String montarUrlPublica(PerfilClinico perfil){return urlBasePublica+"/publico/"+perfil.idPublico();}
 private String valor(String informado,String padrao){return informado==null||informado.isBlank()?padrao:informado.trim();}
}