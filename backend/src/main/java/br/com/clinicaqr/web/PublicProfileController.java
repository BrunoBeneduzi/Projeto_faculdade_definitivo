package br.com.clinicaqr.web;

import br.com.clinicaqr.application.ClinicalProfileService;
import br.com.clinicaqr.domain.model.ClinicalProfile;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayOutputStream;
import java.util.*;

@RestController @RequestMapping("/api/public")
public class PublicProfileController {
    private final ClinicalProfileService service;
    public PublicProfileController(ClinicalProfileService service){this.service=service;}

    @PostMapping("/{publicId}") public PublicResponse access(@PathVariable UUID publicId,@Valid @RequestBody PasswordRequest r){
        return PublicResponse.from(service.accessPublic(publicId,r.password()));
    }
    @GetMapping(value="/{publicId}/qr",produces=MediaType.IMAGE_PNG_VALUE)
    public byte[] qr(@PathVariable UUID publicId) throws Exception {
        ClinicalProfile p=service.mine(currentUserId());
        if(!p.publicId().equals(publicId)) throw new br.com.clinicaqr.application.UnauthorizedException("QR Code não pertence ao usuário");
        BitMatrix matrix=new QRCodeWriter().encode(service.publicUrl(p),BarcodeFormat.QR_CODE,360,360);
        ByteArrayOutputStream out=new ByteArrayOutputStream(); MatrixToImageWriter.writeToStream(matrix,"PNG",out); return out.toByteArray();
    }
    private UUID currentUserId(){return (UUID)org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();}
    public record PasswordRequest(@NotBlank String password){}
    public record PublicResponse(String firstName,String lastName,String sex,String emergencyContact,String bloodType,
      List<String> allergies,List<String> medications,List<String> diseases,List<String> surgeries){
      static PublicResponse from(ClinicalProfile p){return new PublicResponse(p.firstName(),p.lastName(),p.sex(),p.emergencyContact(),p.bloodType(),p.allergies(),p.medications(),p.diseases(),p.surgeries());}
    }
}