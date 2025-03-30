-- Brugere
INSERT INTO tb_users (email, password, name)
VALUES
    ('andreas@example.com', '1234', 'Andreas Gabel'),
    ('rasmus@example.com', '1234', 'Rasmus Mellerkær'),
    ('alexander@example.com', '1234', 'Alexander Örn'),
    ('sofie@example.com', '1234', 'Sofie Nakskov');

-- Wishlists
INSERT INTO tb_wishlists (wl_user_id, title, share_token)
VALUES
    (100, 'Andreas’ Fødselsdag', UUID()),
    (101, 'Rasmus’ Sommerønsker', UUID()),
    (102, 'Alexanders Jul', UUID()),
    (103, 'Sofies Indflytning', UUID());

-- Andreas' ønsker
INSERT INTO tb_items (it_wishlist_id, name, description, price, url, reserved)
VALUES
    (1000, 'Bluetooth Speaker', 'Lang batteritid', 499.95, 'https://example.com/speaker', FALSE),
    (1000, 'Sokker', 'Uldsokker til vinter', 79.50, 'https://example.com/socks', FALSE);

-- Rasmus
INSERT INTO tb_items (it_wishlist_id, name, description, price, url, reserved)
VALUES
    (1001, 'Solbriller', 'Polariserede Ray-Ban', 899.00, 'https://example.com/sunglasses', FALSE),
    (1001, 'Campingstol', 'Foldbar og let', 249.00, 'https://example.com/chair', FALSE);

-- Alexander
INSERT INTO tb_items (it_wishlist_id, name, description, price, url, reserved)
VALUES
    (1002, 'PlayStation 5', 'Standard edition', 4499.00, 'https://example.com/ps5', FALSE),
    (1002, 'Gaming Headset', 'Til lange nætter', 799.00, 'https://example.com/headset', FALSE);

-- Sofie
INSERT INTO tb_items (it_wishlist_id, name, description, price, url, reserved)
VALUES
    (1003, 'Køkkenknivsæt', 'Rustfrit stål', 599.00, 'https://example.com/knives', FALSE),
    (1003, 'Plante', 'Grøn og nem at passe', 129.00, 'https://example.com/plant', FALSE);

-- Andreas' delte ønskeseddel
INSERT INTO shared_wishlists (original_wishlist_id, share_token)
VALUES (1000, UUID());

-- Kopi af Andreas' 2 ønsker
INSERT INTO shared_items (shared_wishlist_id, original_item_id, name, description, price, url)
VALUES
    (2000, 10000, 'Bluetooth Speaker', 'Lang batteritid', 499.95, 'https://example.com/speaker'),
    (2000, 10001, 'Sokker', 'Uldsokker til vinter', 79.50, 'https://example.com/socks');

-- Reservationer (antag shared_item_ids 20000, 20001)
INSERT INTO tb_reservations (rsv_items_id, reserved_quantity)
VALUES
    (20000, 1),  -- Bluetooth Speaker reserveret
    (20001, 1);  -- Sokker reserveret