package br.com.clinicaqr.apresentacao;
import br.com.clinicaqr.aplicacao.ServicoAutenticacao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/autenticacao")
public class ControladorAutenticacao {
 private final ServicoAutenticacao servico;
 public ControladorAutenticacao(ServicoAutenticacao servico){this.servico=servico;}
 @PostMapping("/cadastro") @ResponseStatus(HttpStatus.CREATED)
 public ServicoAutenticacao.ResultadoAutenticacao cadastrar(@Valid @RequestBody RequisicaoCadastro r){return servico.cadastrar(r.nome(),r.sobrenome(),r.email(),r.senha());}
 @PostMapping("/entrar")
 public ServicoAutenticacao.ResultadoAutenticacao entrar(@Valid @RequestBody RequisicaoEntrada r){return servico.entrar(r.email(),r.senha());}
 public record RequisicaoCadastro(@NotBlank String nome,@NotBlank String sobrenome,@Email @NotBlank String email,@Size(min=8)String senha){}
 public record RequisicaoEntrada(@Email @NotBlank String email,@NotBlank String senha){}
}