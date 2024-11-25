-- Inserting contact lists with specific user_ids
INSERT INTO contact_service_db.contact_list (id, list_name) VALUES
      (gen_random_uuid(), 'Track and Field Stars'),
      (gen_random_uuid(), 'Swimming Legends'),
      (gen_random_uuid(), 'Tennis Greats'),
      (gen_random_uuid(), 'Soccer Icons'),
      (gen_random_uuid(), 'Basketball Heroes'),
      (gen_random_uuid(), 'Golf Masters'),
      (gen_random_uuid(), 'Boxing Champions'),
      (gen_random_uuid(), 'Cycling Pros');