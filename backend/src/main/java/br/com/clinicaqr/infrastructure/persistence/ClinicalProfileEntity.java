package br.com.clinicaqr.infrastructure.persistence;

import jakarta.persistence.*;
import java.util.*;

@Entity @Table(name="clinical_profiles")
public class ClinicalProfileEntity {
    @Id public UUID id;
    @Column(name="user_id", nullable=false, unique=true) public UUID userId;
    @Column(name="public_id", nullable=false, unique=true) public UUID publicId;
    @Column(name="first_name", nullable=false) public String firstName;
    @Column(name="last_name", nullable=false) public String lastName;
    public String sex;
    @Column(name="emergency_contact", nullable=false) public String emergencyContact;
    @Column(name="blood_type", nullable=false) public String bloodType;
    @Column(name="public_password_hash", nullable=false) public String publicPasswordHash;
    @ElementCollection(fetch=FetchType.EAGER) @CollectionTable(name="profile_allergies", joinColumns=@JoinColumn(name="profile_id")) @Column(name="value") public List<String> allergies=new ArrayList<>();
    @ElementCollection(fetch=FetchType.EAGER) @CollectionTable(name="profile_medications", joinColumns=@JoinColumn(name="profile_id")) @Column(name="value") public List<String> medications=new ArrayList<>();
    @ElementCollection(fetch=FetchType.EAGER) @CollectionTable(name="profile_diseases", joinColumns=@JoinColumn(name="profile_id")) @Column(name="value") public List<String> diseases=new ArrayList<>();
    @ElementCollection(fetch=FetchType.EAGER) @CollectionTable(name="profile_surgeries", joinColumns=@JoinColumn(name="profile_id")) @Column(name="value") public List<String> surgeries=new ArrayList<>();
    protected ClinicalProfileEntity() {}
}