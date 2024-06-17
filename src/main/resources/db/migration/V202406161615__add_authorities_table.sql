CREATE TABLE authorities
(
    id        UUID    NOT NULL,
    username  VARCHAR NOT NULL,
    authority INTEGER NOT NULL REFERENCES user_authority (id),
    user_id   UUID    NOT NULL REFERENCES users (id) ON DELETE CASCADE
);
create unique index ix_auth_account on authorities (username, authority);