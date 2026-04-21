-- =============================================================
-- SETUP PostgreSQL per a projectes Hibernate (IOC DAM/DAW)
-- Executar amb: psql postgres -f setup.sql
-- =============================================================

-- 1. Crear l'usuari (ignorar si ja existeix)
DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'hibuser') THEN
        CREATE USER hibuser WITH PASSWORD 'password';
    END IF;
END
$$;

-- 2. Crear la base de dades (executar manualment si ja existeix)
-- Si dona error "already exists", salta aquest pas i continua amb el GRANT
CREATE DATABASE hibernatedb OWNER hibuser;

-- 3. Donar tots els permisos
GRANT ALL PRIVILEGES ON DATABASE hibernatedb TO hibuser;

-- =============================================================
-- Per verificar que funciona:
--   psql -U hibuser -d hibernatedb
-- Ha de connectar sense errors.
-- =============================================================
