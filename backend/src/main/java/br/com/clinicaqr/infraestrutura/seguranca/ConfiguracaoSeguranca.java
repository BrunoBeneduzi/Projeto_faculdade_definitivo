package br.com.clinicaqr.infraestrutura.seguranca;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;
import java.util.List;
@Configuration
public class ConfiguracaoSeguranca {
 @Bean PasswordEncoder codificadorSenha(){return new BCryptPasswordEncoder();}
 @Bean SecurityFilterChain cadeiaFiltros(HttpSecurity http,FiltroAutenticacaoJwt filtro) throws Exception{
  return http.csrf(c->c.disable()).cors(c->c.configurationSource(configuracaoCors())).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(a->a.requestMatchers("/api/autenticacao/**").permitAll().requestMatchers(HttpMethod.POST,"/api/publico/*").permitAll()
   .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll().anyRequest().authenticated()).addFilterBefore(filtro,UsernamePasswordAuthenticationFilter.class).build();
 }
 @Bean CorsConfigurationSource configuracaoCors(){
  CorsConfiguration configuracao=new CorsConfiguration();configuracao.setAllowedOrigins(List.of("http://localhost:5173"));
  configuracao.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));configuracao.setAllowedHeaders(List.of("*"));
  UrlBasedCorsConfigurationSource fonte=new UrlBasedCorsConfigurationSource();fonte.registerCorsConfiguration("/**",configuracao);return fonte;
 }
}