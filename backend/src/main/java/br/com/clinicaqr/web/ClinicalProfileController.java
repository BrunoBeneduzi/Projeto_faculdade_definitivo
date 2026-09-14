package br.com.clinicaqr.web;

import br.com.clinicaqr.application.ClinicalProfileService;
import br.com.clinicaqr.domain.model.ClinicalProfile;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/profile")
public class ClinicalProfileController {
    private final ClinicalProfileService service;
    public ClinicalProfileController(ClinicalProfileService service){this.service=service;}

    @PutMapping public ProfileResponse save(Authentication auth,@Valid @RequestBody ProfileRequest r){
        ClinicalProfile p=service.save((UUID)auth.getPrincipal(),r.toDomain((UUID)auth.getPrincipal()),r.publicPassword());
        return ProfileResponse.from(p,service.publicUrl(p));
    }
    @GetMapping public ProfileResponse mine(Authentication auth){
        ClinicalProfile p=service.mine((UUID)auth.getPrincipal()); return ProfileResponse.from(p,service.publicUrl(p));
    }
    @DeleteMapping @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(Authentication auth){service.delete((UUID)auth.getPrincipal());}

    public record ProfileRequest(String firstName,String lastName,@NotBlank String sex,@NotBlank String emergencyContact,
      @NotBlank String bloodType,List<String> allergies,List<String> medications,List<String> diseases,List<String> surgeries,
      String publicPassword){
      ClinicalProfile toDomain(UUID userId){return new ClinicalProfile(null,userId,null,firstName,lastName,sex,emergencyContact,bloodType,allergies,medications,diseases,surgeries,null);}
    }
    public record ProfileResponse(UUID publicId,String publicUrl,String firstName,String lastName,String sex,String emergencyContact,
      String bloodType,List<String> allergies,List<String> medications,List<String> diseases,List<String> surgeries){
      static ProfileResponse from(ClinicalProfile p,String url){return new ProfileResponse(p.publicId(),url,p.firstName(),p.lastName(),p.sex(),p.emergencyContact(),p.bloodType(),p.allergies(),p.medications(),p.diseases(),p.surgeries());}
    }
}