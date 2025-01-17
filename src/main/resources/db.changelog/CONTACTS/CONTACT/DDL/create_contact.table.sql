DROP TABLE IF EXISTS contact CASCADE;
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
CREATE TABLE contact
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    contact_list_id UUID NULL REFERENCES public.contact_list(id) ON DELETE SET NULL,
    contact_first_name TEXT           NULL,
    contact_last_name  TEXT           NULL,
    contact_preferred_name  TEXT      NULL,
    contact_email      TEXT           NULL,
    contact_phone      TEXT           NULL,
    contact_fax        TEXT           NULL,
    address_id         UUID           NULL,
    do_not_contact     BOOLEAN        NOT NULL DEFAULT false
);

