-- === TESTDATA ===

-- Brugere
INSERT INTO tb_users (email, password, name)
VALUES ('andreas@example.com', 'password123', 'Andreas Gabel'),
       ('sofie@example.com', 'hunter2', 'Sofie Hansen');

-- Ønskelister
INSERT INTO tb_wishlists (wl_user_id, title, share_token)
VALUES (100, 'Andreas’ Fødselsdagsliste', 'token-andreas'),
       (101, 'Sofies Juleønsker', 'token-sofie');

-- Ønsker
INSERT INTO tb_items (it_wishlist_id, name, description, price, url, reserved)
VALUES (1000, 'PlayStation 5', 'Nyeste generation konsol', 4999.95, 'https://example.com/ps5', false),
       (1000, 'Bose Headphones', 'Støjdæmpende høretelefoner', 2499.00, 'https://example.com/bose', false),
       (1001, 'Harry Potter bogsæt', 'Alle 7 bøger', 699.00, 'https://example.com/hpbooks', false);

-- Delte ønskelister
INSERT INTO shared_wishlists (sw_wishlist_id, share_token)
VALUES (1000, 'share-uuid-andreas'),
       (1001, 'share-uuid-sofie');

-- Delte ønsker (kopi fra tb_items)
INSERT INTO shared_items (si_shared_wishlist_id, original_item_id, name, description, price, url)
VALUES (2000, 10000, 'PlayStation 5', 'Nyeste generation konsol', 4999.95, 'https://example.com/ps5'),
       (2000, 10001, 'Bose Headphones', 'Støjdæmpende høretelefoner', 2499.00, 'https://example.com/bose'),
       (2001, 10002, 'Harry Potter bogsæt', 'Alle 7 bøger', 699.00, 'https://example.com/hpbooks');

-- Reservationer (fx en ven reserverer et ønske)
INSERT INTO tb_reservations (rsv_items_id)
VALUES (20000), -- PS5 fra Andreas' delt liste
       (20002); -- Harry Potter bøger fra Sofies delt liste
