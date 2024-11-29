-- Inserting contact lists with specific user_ids
INSERT INTO contact_service_db.contact_list (id, list_name) VALUES
      (gen_random_uuid(), 'Track and Field Stars'),
      (gen_random_uuid(), 'Cycling Pros');
