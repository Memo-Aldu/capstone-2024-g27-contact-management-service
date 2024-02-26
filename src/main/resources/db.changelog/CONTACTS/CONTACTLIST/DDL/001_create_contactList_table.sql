CREATE TABLE contact_list
(
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    list_name   TEXT NOT NULL,
    user_id     UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
