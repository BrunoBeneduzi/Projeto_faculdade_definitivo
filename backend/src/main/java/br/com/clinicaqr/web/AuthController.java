package br.com.clinicaqr.web;

import br.com.clinicaqr.application.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService service){this.service=service;}
    @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED)
    public AuthService.AuthResult register(@Valid @RequestBody RegisterRequest r){
        return service.register(r.firstName(),r.lastName(),r.email(),r.password());
    }
    @PostMapping("/login")
    public AuthService.AuthResult login(@Valid @RequestBody LoginRequest r){return service.login(r.email(),r.password());}
    public record RegisterRequest(@NotBlank String firstName,@NotBlank String lastName,@Email @NotBlank String email,@Size(min=8) String password){}
    public record LoginRequest(@Email @NotBlank String email,@NotBlank String password){}
}