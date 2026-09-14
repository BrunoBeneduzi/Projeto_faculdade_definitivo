package br.com.clinicaqr.infrastructure.persistence;

import br.com.clinicaqr.domain.model.ClinicalProfile;
import br.com.clinicaqr.domain.port.ClinicalProfileRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class ClinicalProfileRepositoryAdapter implements ClinicalProfileRepository {
    private final SpringDataClinicalProfileRepository repository;
    public ClinicalProfileRepositoryAdapter(SpringDataClinicalProfileRepository repository) { this.repository=repository; }
    public ClinicalProfile save(ClinicalProfile p) { return toDomain(repository.save(toEntity(p))); }
    public Optional<ClinicalProfile> findByUserId(UUID id) { return repository.findByUserId(id).map(this::toDomain); }
    public Optional<ClinicalProfile> findByPublicId(UUID id) { return repository.findByPublicId(id).map(this::toDomain); }
    public void deleteByUserId(UUID id) { repository.deleteByUserId(id); }
    private ClinicalProfileEntity toEntity(ClinicalProfile p) {
        ClinicalProfileEntity e=new ClinicalProfileEntity(); e.id=p.id(); e.userId=p.userId(); e.publicId=p.publicId();
        e.firstName=p.firstName(); e.lastName=p.lastName(); e.sex=p.sex(); e.emergencyContact=p.emergencyContact();
        e.bloodType=p.bloodType(); e.allergies=new ArrayList<>(p.allergies()); e.medications=new ArrayList<>(p.medications());
        e.diseases=new ArrayList<>(p.diseases()); e.surgeries=new ArrayList<>(p.surgeries()); e.publicPasswordHash=p.publicPasswordHash(); return e;
    }
    private ClinicalProfile toDomain(ClinicalProfileEntity e) {
        return new ClinicalProfile(e.id,e.userId,e.publicId,e.firstName,e.lastName,e.sex,e.emergencyContact,
                e.bloodType,e.allergies,e.medications,e.diseases,e.surgeries,e.publicPasswordHash);
    }
}