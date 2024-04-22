DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_user WHERE usename = 'developer') THEN
        CREATE USER admin WITH PASSWORD 'localpassword';
END IF;
END $$;
