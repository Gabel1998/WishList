-- Slet eksisterende tabeller, hvis de findes
DROP TABLE IF EXISTS tb_reservations;
DROP TABLE IF EXISTS shared_items;
DROP TABLE IF EXISTS shared_wishlists;
DROP TABLE IF EXISTS tb_items;
DROP TABLE IF EXISTS tb_wishlists;
DROP TABLE IF EXISTS tb_users;

-- USERS
CREATE TABLE tb_users
(
    user_id  INT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    name     VARCHAR(255)        NOT NULL
);

-- WISHLISTS
CREATE TABLE tb_wishlists
(
    wishlist_id INT AUTO_INCREMENT PRIMARY KEY,
    wl_user_id  INT NOT NULL,
    name       VARCHAR(255),
    share_token VARCHAR(255),
    FOREIGN KEY (wl_user_id) REFERENCES tb_users (user_id) ON DELETE CASCADE
);

-- ITEMS
CREATE TABLE tb_items
(
    item_id        INT AUTO_INCREMENT PRIMARY KEY,
    it_wishlist_id INT NOT NULL,
    name           VARCHAR(255),
    description    VARCHAR(500), -- H2 understøtter ikke TEXT, så brug VARCHAR
    price          DECIMAL(10, 2),
    url            VARCHAR(255),
    reserved       BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (it_wishlist_id) REFERENCES tb_wishlists (wishlist_id) ON DELETE CASCADE
);

-- SHARED WISHLISTS
CREATE TABLE shared_wishlists
(
    shared_wishlist_id INT AUTO_INCREMENT PRIMARY KEY,
    sw_wishlist_id     INT      NOT NULL,
    share_token        CHAR(36) NOT NULL,
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sw_wishlist_id) REFERENCES tb_wishlists (wishlist_id) ON DELETE CASCADE
);

-- SHARED ITEMS
CREATE TABLE shared_items
(
    shared_items_id       INT AUTO_INCREMENT PRIMARY KEY,
    si_shared_wishlist_id INT NOT NULL,
    original_item_id      INT,
    name                  VARCHAR(255),
    description           VARCHAR(500),
    price                 DECIMAL(10, 2),
    url                   VARCHAR(255),
    FOREIGN KEY (si_shared_wishlist_id) REFERENCES shared_wishlists (shared_wishlist_id) ON DELETE CASCADE
);

-- RESERVATIONS
CREATE TABLE tb_reservations
(
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    rsv_items_id   INT UNIQUE NOT NULL,
    reserved_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (rsv_items_id) REFERENCES shared_items (shared_items_id) ON DELETE CASCADE
);


-- Brugere
INSERT INTO tb_users (user_id, email, password, name)
VALUES (100, 'test@example.com', '1234', 'Test Bruger');

-- Ønskeseddel
INSERT INTO tb_wishlists (wishlist_id, wl_user_id, name, share_token)
VALUES (1000, 100, 'Fødselsdag', 'abc123-token');

-- Item
INSERT INTO tb_items (item_id, it_wishlist_id, name, description, price, url, reserved)
VALUES (10000, 1000, 'Test Gave', 'Beskrivelse', 250.00, 'http://gave.dk', false);

-- Deling
INSERT INTO shared_wishlists (shared_wishlist_id, sw_wishlist_id, share_token)
VALUES (2000, 1000, 'shared-abc');

INSERT INTO shared_items (shared_items_id, si_shared_wishlist_id, original_item_id, name, description, price, url)
VALUES (20000, 2000, 10000, 'Del Gave', 'Beskrivelse', 150.00, 'http://del.dk');
