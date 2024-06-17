CREATE TABLE event_guest
(
    event_id UUID
        CONSTRAINT fk_event_id REFERENCES event (id) ON DELETE CASCADE,
    guest_id UUID
        CONSTRAINT fk_user_id REFERENCES users (id) ON DELETE CASCADE,
    PRIMARY KEY (event_id, guest_id)
);
