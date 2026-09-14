CREATE TABLE users (
 id UUID PRIMARY KEY, first_name VARCHAR(100) NOT NULL, last_name VARCHAR(100) NOT NULL,
 email VARCHAR(255) NOT NULL UNIQUE, password_hash VARCHAR(255) NOT NULL
);
CREATE TABLE clinical_profiles (
 id UUID PRIMARY KEY, user_id UUID NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
 public_id UUID NOT NULL UNIQUE, first_name VARCHAR(100) NOT NULL, last_name VARCHAR(100) NOT NULL,
 sex VARCHAR(40), emergency_contact VARCHAR(255) NOT NULL, blood_type VARCHAR(5) NOT NULL,
 public_password_hash VARCHAR(255) NOT NULL
);
CREATE TABLE profile_allergies (profile_id UUID NOT NULL REFERENCES clinical_profiles(id) ON DELETE CASCADE, value VARCHAR(255));
CREATE TABLE profile_medications (profile_id UUID NOT NULL REFERENCES clinical_profiles(id) ON DELETE CASCADE, value VARCHAR(255));
CREATE TABLE profile_diseases (profile_id UUID NOT NULL REFERENCES clinical_profiles(id) ON DELETE CASCADE, value VARCHAR(255));
CREATE TABLE profile_surgeries (profile_id UUID NOT NULL REFERENCES clinical_profiles(id) ON DELETE CASCADE, value VARCHAR(255));
CREATE INDEX idx_clinical_profiles_public_id ON clinical_profiles(public_id);