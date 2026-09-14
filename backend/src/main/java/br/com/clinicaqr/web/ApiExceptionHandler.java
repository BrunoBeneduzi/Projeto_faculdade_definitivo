package br.com.clinicaqr.web;

import br.com.clinicaqr.application.*;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(NotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND) ErrorResponse notFound(RuntimeException e){return error(404,e.getMessage());}
    @ExceptionHandler(UnauthorizedException.class) @ResponseStatus(HttpStatus.UNAUTHORIZED) ErrorResponse unauthorized(RuntimeException e){return error(401,e.getMessage());}
    @ExceptionHandler(ConflictException.class) @ResponseStatus(HttpStatus.CONFLICT) ErrorResponse conflict(RuntimeException e){return error(409,e.getMessage());}
    @ExceptionHandler({IllegalArgumentException.class,MethodArgumentNotValidException.class}) @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse badRequest(Exception e){
        String message=e instanceof MethodArgumentNotValidException m ? m.getBindingResult().getFieldErrors().stream().findFirst().map(x->x.getField()+": "+x.getDefaultMessage()).orElse("Dados inválidos") : e.getMessage();
        return error(400,message);
    }
    private ErrorResponse error(int status,String message){return new ErrorResponse(Instant.now(),status,message);}
    record ErrorResponse(Instant timestamp,int status,String message){}
}