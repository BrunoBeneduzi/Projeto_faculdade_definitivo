package br.com.clinicaqr.apresentacao;
import br.com.clinicaqr.aplicacao.ServicoPerfilClinico;
import br.com.clinicaqr.dominio.modelo.PerfilClinico;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/perfil")
public class ControladorPerfilClinico {
 private final ServicoPerfilClinico servico;
 public ControladorPerfilClinico(ServicoPerfilClinico servico){this.servico=servico;}
 @PutMapping public RespostaPerfil salvar(Authentication autenticacao,@Valid @RequestBody RequisicaoPerfil r){
  PerfilClinico p=servico.salvar((UUID)autenticacao.getPrincipal(),r.paraDominio((UUID)autenticacao.getPrincipal()),r.senhaPublica());return RespostaPerfil.de(p,servico.montarUrlPublica(p));
 }
 @GetMapping public RespostaPerfil buscarMeu(Authentication autenticacao){PerfilClinico p=servico.buscarMeu((UUID)autenticacao.getPrincipal());return RespostaPerfil.de(p,servico.montarUrlPublica(p));}
 @DeleteMapping @ResponseStatus(HttpStatus.NO_CONTENT) public void excluir(Authentication autenticacao){servico.excluir((UUID)autenticacao.getPrincipal());}
 public record RequisicaoPerfil(String nome,String sobrenome,@NotBlank String sexo,@NotBlank String contatoEmergencia,@NotBlank String telefoneContatoEmergencia,@NotBlank String tipoSanguineo,
  List<String> alergias,List<String> medicamentos,List<String> doencas,List<String> cirurgias,String senhaPublica){
  PerfilClinico paraDominio(UUID usuarioId){return new PerfilClinico(null,usuarioId,null,nome,sobrenome,sexo,contatoEmergencia,telefoneContatoEmergencia,tipoSanguineo,alergias,medicamentos,doencas,cirurgias,null);}
 }
 public record RespostaPerfil(UUID idPublico,String urlPublica,String nome,String sobrenome,String sexo,String contatoEmergencia,String telefoneContatoEmergencia,String tipoSanguineo,
  List<String> alergias,List<String> medicamentos,List<String> doencas,List<String> cirurgias){
  static RespostaPerfil de(PerfilClinico p,String url){return new RespostaPerfil(p.idPublico(),url,p.nome(),p.sobrenome(),p.sexo(),p.contatoEmergencia(),p.telefoneContatoEmergencia(),p.tipoSanguineo(),p.alergias(),p.medicamentos(),p.doencas(),p.cirurgias());}
 }
}