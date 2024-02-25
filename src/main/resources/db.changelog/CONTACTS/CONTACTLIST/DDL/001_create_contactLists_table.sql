INSERT INTO contact_service_db.contact 
(contact_first_name, contact_last_name, contact_preferred_name, contact_email, contact_phone, contact_fax, address_id, do_not_contact) VALUES
('John', 'Doe', 'Johnny', 'john.doe@email.com', '823-555-0100', '823-555-0101', floor(random() * 89999999 + 10000000)::bigint, false),
('Jane', 'Smith', 'Janie', 'jane.smith@email.com', '823-555-0200', '823-555-0201', floor(random() * 89999999 + 10000000)::bigint, false),
('Emily', 'Johnson', 'Em', 'emily.johnson@email.com', '823-555-0300', '823-555-0301', floor(random() * 89999999 + 10000000)::bigint, true),
('Michael', 'Brown', 'Mike', 'michael.brown@email.com', '823-555-0400', '823-555-0401', floor(random() * 89999999 + 10000000)::bigint, false),
('Jessica', 'Davis', 'Jess', 'jessica.davis@email.com', '823-555-0500', '823-555-0501', floor(random() * 89999999 + 10000000)::bigint, false),
('William', 'Miller', 'Will', 'william.miller@email.com', '823-555-0600', '823-555-0601', floor(random() * 89999999 + 10000000)::bigint, true),
('Sarah', 'Wilson', 'Sally', 'sarah.wilson@email.com', '823-555-0700', '823-555-0701', floor(random() * 89999999 + 10000000)::bigint, false),
('David', 'Moore', 'Dave', 'david.moore@email.com', '823-555-0800', '823-555-0801', floor(random() * 89999999 + 10000000)::bigint, false),
('Laura', 'Taylor', 'Laurie', 'laura.taylor@email.com', '823-555-0900', '823-555-0901', floor(random() * 89999999 + 10000000)::bigint, true),
('James', 'Anderson', 'Jim', 'james.anderson@email.com', '823-555-1000', '823-555-1001', floor(random() * 89999999 + 10000000)::bigint, false);
