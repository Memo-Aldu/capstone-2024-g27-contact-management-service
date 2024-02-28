CREATE TABLE contact_list
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    list_name   TEXT NOT NULL,
    user_id     UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
