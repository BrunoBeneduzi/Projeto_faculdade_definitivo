package br.com.clinicaqr.domain.model;

import java.util.List;
import java.util.UUID;

public record ClinicalProfile(
        UUID id, UUID userId, UUID publicId, String firstName, String lastName, String sex,
        String emergencyContact, String bloodType, List<String> allergies, List<String> medications,
        List<String> diseases, List<String> surgeries, String publicPasswordHash) {
    public ClinicalProfile {
        allergies = allergies == null ? List.of() : List.copyOf(allergies);
        medications = medications == null ? List.of() : List.copyOf(medications);
        diseases = diseases == null ? List.of() : List.copyOf(diseases);
        surgeries = surgeries == null ? List.of() : List.copyOf(surgeries);
    }
}