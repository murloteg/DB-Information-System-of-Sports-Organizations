DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'sport-info-db') THEN
        CREATE DATABASE sport-info-db;
END IF;
END $$;
