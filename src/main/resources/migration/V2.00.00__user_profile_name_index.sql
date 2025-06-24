CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE INDEX IF NOT EXISTS user_profile_first_second_name_idx ON user_profile
    USING gin (first_name gin_trgm_ops, second_name gin_trgm_ops);
