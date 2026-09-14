package br.com.clinicaqr.domain.port;

import br.com.clinicaqr.domain.model.ClinicalProfile;
import java.util.Optional;
import java.util.UUID;

public interface ClinicalProfileRepository {
    ClinicalProfile save(ClinicalProfile profile);
    Optional<ClinicalProfile> findByUserId(UUID userId);
    Optional<ClinicalProfile> findByPublicId(UUID publicId);
    void deleteByUserId(UUID userId);
}