ALTER TABLE users RENAME TO usuarios;
ALTER TABLE usuarios RENAME COLUMN first_name TO nome;
ALTER TABLE usuarios RENAME COLUMN last_name TO sobrenome;
ALTER TABLE usuarios RENAME COLUMN password_hash TO senha_hash;

ALTER TABLE clinical_profiles RENAME TO perfis_clinicos;
ALTER TABLE perfis_clinicos RENAME COLUMN user_id TO usuario_id;
ALTER TABLE perfis_clinicos RENAME COLUMN public_id TO id_publico;
ALTER TABLE perfis_clinicos RENAME COLUMN first_name TO nome;
ALTER TABLE perfis_clinicos RENAME COLUMN last_name TO sobrenome;
ALTER TABLE perfis_clinicos RENAME COLUMN emergency_contact TO contato_emergencia;
ALTER TABLE perfis_clinicos RENAME COLUMN blood_type TO tipo_sanguineo;
ALTER TABLE perfis_clinicos RENAME COLUMN public_password_hash TO senha_publica_hash;

ALTER TABLE profile_allergies RENAME TO perfil_alergias;
ALTER TABLE perfil_alergias RENAME COLUMN profile_id TO perfil_id;
ALTER TABLE perfil_alergias RENAME COLUMN value TO valor;
ALTER TABLE profile_medications RENAME TO perfil_medicamentos;
ALTER TABLE perfil_medicamentos RENAME COLUMN profile_id TO perfil_id;
ALTER TABLE perfil_medicamentos RENAME COLUMN value TO valor;
ALTER TABLE profile_diseases RENAME TO perfil_doencas;
ALTER TABLE perfil_doencas RENAME COLUMN profile_id TO perfil_id;
ALTER TABLE perfil_doencas RENAME COLUMN value TO valor;
ALTER TABLE profile_surgeries RENAME TO perfil_cirurgias;
ALTER TABLE perfil_cirurgias RENAME COLUMN profile_id TO perfil_id;
ALTER TABLE perfil_cirurgias RENAME COLUMN value TO valor;
