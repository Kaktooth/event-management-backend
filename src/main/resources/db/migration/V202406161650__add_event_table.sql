CREATE TABLE event
(
    id           UUID PRIMARY KEY,
    name         VARCHAR UNIQUE NOT NULL,
    approved     BOOLEAN        NOT NULL              DEFAULT FALSE,
    created_date TIMESTAMP      NOT NULL,
    visibility   INTEGER
        CONSTRAINT event_visibility_id REFERENCES event_visibility (id) ON DELETE CASCADE,
    chat_id      UUID
        CONSTRAINT event_chat_id REFERENCES chat (id) DEFAULT NULL,
    creator_id   UUID
        CONSTRAINT event_creator_user_id REFERENCES users (id) ON DELETE CASCADE
);
