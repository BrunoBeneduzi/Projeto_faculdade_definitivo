package br.com.clinicaqr.application;

import br.com.clinicaqr.domain.model.ClinicalProfile;
import br.com.clinicaqr.domain.model.User;
import br.com.clinicaqr.domain.port.ClinicalProfileRepository;
import br.com.clinicaqr.domain.port.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class ClinicalProfileService {
    private final ClinicalProfileRepository profiles;
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final String publicBaseUrl;

    public ClinicalProfileService(ClinicalProfileRepository profiles, UserRepository users,
            PasswordEncoder encoder, @Value("${app.public-base-url}") String publicBaseUrl) {
        this.profiles = profiles; this.users = users; this.encoder = encoder; this.publicBaseUrl = publicBaseUrl;
    }

    @Transactional
    public ClinicalProfile save(UUID userId, ClinicalProfile input, String publicPassword) {
        User user = users.findById(userId).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        ClinicalProfile old = profiles.findByUserId(userId).orElse(null);
        UUID id = old == null ? UUID.randomUUID() : old.id();
        UUID publicId = old == null ? UUID.randomUUID() : old.publicId();
        String hash = publicPassword != null && !publicPassword.isBlank()
                ? encoder.encode(publicPassword)
                : old == null ? null : old.publicPasswordHash();
        if (hash == null) throw new IllegalArgumentException("Senha pública é obrigatória");
        return profiles.save(new ClinicalProfile(id, userId, publicId,
                value(input.firstName(), user.firstName()), value(input.lastName(), user.lastName()),
                input.sex(), input.emergencyContact(), input.bloodType(), input.allergies(),
                input.medications(), input.diseases(), input.surgeries(), hash));
    }

    public ClinicalProfile mine(UUID userId) {
        return profiles.findByUserId(userId).orElseThrow(() -> new NotFoundException("Cadastro clínico não encontrado"));
    }

    public ClinicalProfile accessPublic(UUID publicId, String password) {
        ClinicalProfile p = profiles.findByPublicId(publicId)
                .orElseThrow(() -> new NotFoundException("Link público indisponível"));
        if (!encoder.matches(password, p.publicPasswordHash()))
            throw new UnauthorizedException("Senha pública inválida");
        return p;
    }

    @Transactional public void delete(UUID userId) {
        if (profiles.findByUserId(userId).isEmpty()) throw new NotFoundException("Cadastro clínico não encontrado");
        profiles.deleteByUserId(userId);
    }

    public String publicUrl(ClinicalProfile profile) { return publicBaseUrl + "/publico/" + profile.publicId(); }
    private String value(String supplied, String fallback) { return supplied == null || supplied.isBlank() ? fallback : supplied.trim(); }
}