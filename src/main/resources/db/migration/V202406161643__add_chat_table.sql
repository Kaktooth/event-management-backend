CREATE TABLE chat
(
    id           UUID PRIMARY KEY,
    name         VARCHAR   NOT NULL,
    created_date TIMESTAMP NOT NULL,
    creator_id   UUID
        CONSTRAINT chat_creator_user_id REFERENCES users (id) ON DELETE CASCADE
);
