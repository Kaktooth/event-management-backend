CREATE TABLE chat_guest
(
    chat_id  UUID
        CONSTRAINT fk_chat_id REFERENCES chat (id) ON DELETE CASCADE,
    guest_id UUID
        CONSTRAINT fk_guest_user_id REFERENCES users (id) ON DELETE CASCADE,
    PRIMARY KEY (chat_id, guest_id)
);
