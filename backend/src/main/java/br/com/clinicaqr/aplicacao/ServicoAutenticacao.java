package br.com.clinicaqr.aplicacao;
import br.com.clinicaqr.dominio.modelo.Usuario;
import br.com.clinicaqr.dominio.porta.RepositorioUsuario;
import br.com.clinicaqr.infraestrutura.seguranca.ServicoJwt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;
@Service
public class ServicoAutenticacao {
 private final RepositorioUsuario repositorioUsuarios; private final PasswordEncoder codificadorSenha; private final ServicoJwt servicoJwt;
 public ServicoAutenticacao(RepositorioUsuario repositorioUsuarios,PasswordEncoder codificadorSenha,ServicoJwt servicoJwt){
  this.repositorioUsuarios=repositorioUsuarios;this.codificadorSenha=codificadorSenha;this.servicoJwt=servicoJwt;
 }
 public ResultadoAutenticacao cadastrar(String nome,String sobrenome,String email,String senha){
  String emailNormalizado=email.trim().toLowerCase();
  if(repositorioUsuarios.existePorEmail(emailNormalizado))throw new ExcecaoConflito("E-mail já cadastrado");
  Usuario usuario=repositorioUsuarios.salvar(new Usuario(UUID.randomUUID(),nome.trim(),sobrenome.trim(),emailNormalizado,codificadorSenha.encode(senha)));
  return new ResultadoAutenticacao(servicoJwt.gerar(usuario),usuario.nome(),usuario.sobrenome(),usuario.email());
 }
 public ResultadoAutenticacao entrar(String email,String senha){
  Usuario usuario=repositorioUsuarios.buscarPorEmail(email.trim().toLowerCase()).orElseThrow(()->new ExcecaoNaoAutorizado("E-mail ou senha inválidos"));
  if(!codificadorSenha.matches(senha,usuario.senhaHash()))throw new ExcecaoNaoAutorizado("E-mail ou senha inválidos");
  return new ResultadoAutenticacao(servicoJwt.gerar(usuario),usuario.nome(),usuario.sobrenome(),usuario.email());
 }
 public record ResultadoAutenticacao(String token,String nome,String sobrenome,String email){}
}