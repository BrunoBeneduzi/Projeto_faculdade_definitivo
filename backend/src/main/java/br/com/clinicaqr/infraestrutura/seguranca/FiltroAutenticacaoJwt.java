package br.com.clinicaqr.infraestrutura.seguranca;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.*;
@Component
public class FiltroAutenticacaoJwt extends OncePerRequestFilter {
 private final ServicoJwt servicoJwt;
 public FiltroAutenticacaoJwt(ServicoJwt servicoJwt){this.servicoJwt=servicoJwt;}
 @Override protected void doFilterInternal(HttpServletRequest requisicao,HttpServletResponse resposta,FilterChain cadeia) throws ServletException,IOException{
  String cabecalho=requisicao.getHeader("Authorization");
  if(cabecalho!=null&&cabecalho.startsWith("Bearer ")){try{
   UUID id=servicoJwt.obterUsuarioId(cabecalho.substring(7));
   SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(id,null,List.of()));
  }catch(Exception ignorada){SecurityContextHolder.clearContext();}}
  cadeia.doFilter(requisicao,resposta);
 }
}