package br.com.clinicaqr.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
interface SpringDataClinicalProfileRepository extends JpaRepository<ClinicalProfileEntity, UUID> {
    Optional<ClinicalProfileEntity> findByUserId(UUID userId);
    Optional<ClinicalProfileEntity> findByPublicId(UUID publicId);
    void deleteByUserId(UUID userId);
}