ALTER TABLE perfis_clinicos
ADD COLUMN telefone_contato_emergencia VARCHAR(30) NOT NULL DEFAULT 'Não informado';

ALTER TABLE perfis_clinicos
ALTER COLUMN telefone_contato_emergencia DROP DEFAULT;
