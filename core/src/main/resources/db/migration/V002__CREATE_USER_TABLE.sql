CREATE TABLE IF NOT EXISTS "user"
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    email      varchar(255) NOT NULL UNIQUE,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    full_name varchar(255) NOT NULL
);
