CREATE TABLE user_friend
(
    user_id   UUID
        CONSTRAINT fk_event_id REFERENCES users (id) ON DELETE CASCADE,
    friend_id UUID
        CONSTRAINT fk_user_friend_id REFERENCES users (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, friend_id)
);
