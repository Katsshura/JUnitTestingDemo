CREATE TABLE IF NOT EXISTS "task"
(
    id          BIGSERIAL PRIMARY KEY,
    created_at  TIMESTAMP NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP NOT NULL DEFAULT now(),
    description varchar(255),
    user_id     BIGINT,
    CONSTRAINT fk_task_user_id
        FOREIGN KEY (user_id)
            REFERENCES "user" (id)
);

CREATE INDEX IF NOT EXISTS idx_task_user_id ON task (user_id);
