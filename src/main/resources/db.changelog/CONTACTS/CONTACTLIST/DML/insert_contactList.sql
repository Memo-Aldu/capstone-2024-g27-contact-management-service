-- Inserting contact lists with specific user_ids
INSERT INTO public.contact_list (id, list_name, user_id) VALUES
      (gen_random_uuid(), 'Track and Field Stars', '601e4b27-a170-428d-8d8e-479ae1e31746'),
      (gen_random_uuid(), 'Swimming Legends', '601e4b27-a170-428d-8d8e-479ae1e31746'),
      (gen_random_uuid(), 'Tennis Greats', '601e4b27-a170-428d-8d8e-479ae1e31746'),
      (gen_random_uuid(), 'Soccer Icons', '601e4b27-a170-428d-8d8e-479ae1e31746'),
      (gen_random_uuid(), 'Basketball Heroes', '601e4b27-a170-428d-8d8e-479ae1e31746'),
      (gen_random_uuid(), 'Golf Masters', '601e4b27-a170-428d-8d8e-479ae1e31746'),
      (gen_random_uuid(), 'Boxing Champions', '601e4b27-a170-428d-8d8e-479ae1e31746'),
      (gen_random_uuid(), 'Cycling Pros', '601e4b27-a170-428d-8d8e-479ae1e31746');
