package br.com.clinicaqr.infraestrutura.seguranca;
import br.com.clinicaqr.dominio.modelo.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
@Component
public class ServicoJwt {
 private final SecretKey chave;private final long duracao;
 public ServicoJwt(@Value("${aplicacao.jwt.chave}")String segredo,@Value("${aplicacao.jwt.duracao-ms}")long duracao){
  this.chave=Keys.hmacShaKeyFor(segredo.getBytes(StandardCharsets.UTF_8));this.duracao=duracao;
 }
 public String gerar(Usuario usuario){Instant agora=Instant.now();return Jwts.builder().subject(usuario.id().toString()).claim("email",usuario.email()).issuedAt(Date.from(agora)).expiration(Date.from(agora.plusMillis(duracao))).signWith(chave).compact();}
 public UUID obterUsuarioId(String token){return UUID.fromString(Jwts.parser().verifyWith(chave).build().parseSignedClaims(token).getPayload().getSubject());}
}