package br.com.clinicaqr.apresentacao;
import br.com.clinicaqr.aplicacao.*;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
@RestControllerAdvice
public class TratadorExcecoesApi {
 @ExceptionHandler(ExcecaoNaoEncontrado.class) @ResponseStatus(HttpStatus.NOT_FOUND) RespostaErro naoEncontrado(RuntimeException e){return erro(404,e.getMessage());}
 @ExceptionHandler(ExcecaoNaoAutorizado.class) @ResponseStatus(HttpStatus.UNAUTHORIZED) RespostaErro naoAutorizado(RuntimeException e){return erro(401,e.getMessage());}
 @ExceptionHandler(ExcecaoConflito.class) @ResponseStatus(HttpStatus.CONFLICT) RespostaErro conflito(RuntimeException e){return erro(409,e.getMessage());}
 @ExceptionHandler({IllegalArgumentException.class,MethodArgumentNotValidException.class}) @ResponseStatus(HttpStatus.BAD_REQUEST)
 RespostaErro requisicaoInvalida(Exception e){
  String mensagem=e instanceof MethodArgumentNotValidException m?m.getBindingResult().getFieldErrors().stream().findFirst().map(x->x.getField()+": "+x.getDefaultMessage()).orElse("Dados inválidos"):e.getMessage();
  return erro(400,mensagem);
 }
 private RespostaErro erro(int estado,String mensagem){return new RespostaErro(Instant.now(),estado,mensagem);}
 record RespostaErro(Instant dataHora,int estado,String mensagem){}
}