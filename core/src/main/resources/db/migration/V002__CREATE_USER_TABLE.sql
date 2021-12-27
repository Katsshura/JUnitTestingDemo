CREATE TABLE IF NOT EXISTS "user"
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    email      varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    full_name varchar(255)
);
