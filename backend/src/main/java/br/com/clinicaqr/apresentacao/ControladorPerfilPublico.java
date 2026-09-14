package br.com.clinicaqr.apresentacao;
import br.com.clinicaqr.aplicacao.*;
import br.com.clinicaqr.dominio.modelo.PerfilClinico;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayOutputStream;
import java.util.*;
@RestController @RequestMapping("/api/publico")
public class ControladorPerfilPublico {
 private final ServicoPerfilClinico servico;
 public ControladorPerfilPublico(ServicoPerfilClinico servico){this.servico=servico;}
 @PostMapping("/{idPublico}") public RespostaPublica acessar(@PathVariable UUID idPublico,@Valid @RequestBody RequisicaoSenha r){return RespostaPublica.de(servico.acessarPublico(idPublico,r.senha()));}
 @GetMapping(value="/{idPublico}/codigo-qr",produces=MediaType.IMAGE_PNG_VALUE)
 public byte[] gerarCodigoQr(@PathVariable UUID idPublico)throws Exception{
  UUID usuarioId=(UUID)org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  PerfilClinico p=servico.buscarMeu(usuarioId);if(!p.idPublico().equals(idPublico))throw new ExcecaoNaoAutorizado("Código QR não pertence ao usuário");
  BitMatrix matriz=new QRCodeWriter().encode(servico.montarUrlPublica(p),BarcodeFormat.QR_CODE,360,360);
  ByteArrayOutputStream saida=new ByteArrayOutputStream();MatrixToImageWriter.writeToStream(matriz,"PNG",saida);return saida.toByteArray();
 }
 public record RequisicaoSenha(@NotBlank String senha){}
 public record RespostaPublica(String nome,String sobrenome,String sexo,String contatoEmergencia,String telefoneContatoEmergencia,String tipoSanguineo,List<String> alergias,List<String> medicamentos,List<String> doencas,List<String> cirurgias){
  static RespostaPublica de(PerfilClinico p){return new RespostaPublica(p.nome(),p.sobrenome(),p.sexo(),p.contatoEmergencia(),p.telefoneContatoEmergencia(),p.tipoSanguineo(),p.alergias(),p.medicamentos(),p.doencas(),p.cirurgias());}
 }
}