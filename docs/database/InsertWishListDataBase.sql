INSERT INTO users (name, email, password)
VALUES
  ('Andreas Gabel', 'andreas@example.com', '1234'),
  ('Rasmus Mellerkær', 'rasmus@example.com', '1234'),
  ('Alexander Örn', 'alexander@example.com', '1234'),
  ('Sofie Nakskov', 'sofie@example.com', '1234');

-- Forudsætter at brugerne får ID 100–103
INSERT INTO wishlists (user_id, name)
VALUES
  (100, 'Andreas’ Fødselsdag'),
  (101, 'Rasmus’ Sommerønsker'),
  (102, 'Alexanders Jul'),
  (103, 'Sofies Indflytning');

-- Andreas
INSERT INTO items (wishlist_id, name, description, price, quantity, link)
VALUES
  (1000, 'Bluetooth Speaker', 'Lang batteritid', 499.95, 1, 'https://example.com/speaker'),
  (1000, 'Sokker', 'Uldsokker til vinter', 79.50, 3, 'https://example.com/socks');

-- Rasmus
INSERT INTO items (wishlist_id, name, description, price, quantity, link)
VALUES
  (1001, 'Solbriller', 'Polariserede Ray-Ban', 899.00, 1, 'https://example.com/sunglasses'),
  (1001, 'Campingstol', 'Foldbar og let', 249.00, 1, 'https://example.com/chair');

-- Alexander
INSERT INTO items (wishlist_id, name, description, price, quantity, link)
VALUES
  (1002, 'PlayStation 5', 'Standard edition', 4499.00, 1, 'https://example.com/ps5'),
  (1002, 'Gaming Headset', 'Til lange nætter', 799.00, 1, 'https://example.com/headset');

-- Sofie
INSERT INTO items (wishlist_id, name, description, price, quantity, link)
VALUES
  (1003, 'Køkkenknivsæt', 'Rustfrit stål', 599.00, 1, 'https://example.com/knives'),
  (1003, 'Plante', 'Grøn og nem at passe', 129.00, 2, 'https://example.com/plant');

-- Husk: UUID() genererer en unik token hver gang
INSERT INTO shared_wishlists (original_wishlist_id, share_token)
VALUES (1000, UUID());

-- Kopi af Andreas' 2 ønsker fra wishlist_id 1000
INSERT INTO shared_items (shared_wishlist_id, original_item_id, name, description, price, quantity, link)
VALUES
  (1000, 10000, 'Bluetooth Speaker', 'Lang batteritid', 499.95, 1, 'https://example.com/speaker'),
  (1000, 10001, 'Sokker', 'Uldsokker til vinter', 79.50, 3, 'https://example.com/socks');

-- Antag shared_item_id 10000 og 10001
INSERT INTO reservations (shared_item_id, reserved_quantity)
VALUES
  (10000, 1),  -- Bluetooth Speaker reserveret
  (10001, 2);  -- 2 ud af 3 sokker reserveret
