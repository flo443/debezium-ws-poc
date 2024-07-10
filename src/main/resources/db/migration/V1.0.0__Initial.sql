CREATE TABLE IF NOT EXISTS "upload_keys" (
    "upload_token" UUID NOT NULL PRIMARY KEY,
    "token_status" VARCHAR,
    "creation_date" TIMESTAMP
);