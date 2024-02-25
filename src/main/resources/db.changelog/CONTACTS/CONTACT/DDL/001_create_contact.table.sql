DROP TABLE IF EXISTS contacts CASCADE;
CREATE TABLE contacts
(
    id             UUID                 DEFAULT uuid_generate_v4() PRIMARY KEY,
    contact_first_name TEXT           NULL,
    contact_last_name  TEXT           NULL,
    contact_preferred_name  TEXT      NULL,
    contact_email      TEXT           NULL,
    contact_phone      TEXT           NULL,
    contact_fax        TEXT           NULL,
    address_id         UUID           NULL,
    do_not_contact     BOOLEAN        NOT NULL DEFAULT false,
);

