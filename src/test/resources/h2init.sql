DROP TABLE IF EXISTS tb_reservations;
DROP TABLE IF EXISTS shared_items;
DROP TABLE IF EXISTS shared_wishlists;
DROP TABLE IF EXISTS tb_items;
DROP TABLE IF EXISTS tb_wishlists;
DROP TABLE IF EXISTS tb_users;

-- USERS
CREATE TABLE tb_users
(
    user_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    name     VARCHAR(255)        NOT NULL
);
ALTER TABLE tb_users
    ALTER COLUMN user_id RESTART WITH 100;

-- WISHLISTS
CREATE TABLE tb_wishlists
(
    wishlist_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    wl_user_id  BIGINT NOT NULL,
    title       VARCHAR(255),
    share_token VARCHAR(255),
    FOREIGN KEY (wl_user_id) REFERENCES tb_users (user_id) ON DELETE CASCADE
);
ALTER TABLE tb_wishlists
    ALTER COLUMN wishlist_id RESTART WITH 1000;

-- ITEMS
CREATE TABLE tb_items
(
    item_id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    it_wishlist_id BIGINT NOT NULL,
    name           VARCHAR(255),
    description    TEXT,
    price          DECIMAL(10, 2),
    url            VARCHAR(255),
    reserved       BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (it_wishlist_id) REFERENCES tb_wishlists (wishlist_id) ON DELETE CASCADE
);
ALTER TABLE tb_items
    ALTER COLUMN item_id RESTART WITH 10000;

-- SHARED WISHLISTS
CREATE TABLE shared_wishlists
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    original_wishlist_id BIGINT   NOT NULL,
    share_token          CHAR(36) NOT NULL,
    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (original_wishlist_id) REFERENCES tb_wishlists (wishlist_id) ON DELETE CASCADE
);
ALTER TABLE shared_wishlists
    ALTER COLUMN id RESTART WITH 2000;

-- SHARED ITEMS
CREATE TABLE shared_items
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    shared_wishlist_id BIGINT NOT NULL,
    original_item_id   BIGINT,
    name               VARCHAR(255),
    description        TEXT,
    price              DECIMAL(10, 2),
    url                VARCHAR(255),
    FOREIGN KEY (shared_wishlist_id) REFERENCES shared_wishlists (id) ON DELETE CASCADE
);
ALTER TABLE shared_items
    ALTER COLUMN id RESTART WITH 20000;

-- RESERVATIONS
CREATE TABLE tb_reservations
(
    reservation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rsv_items_id   BIGINT UNIQUE NOT NULL,
    reserved_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (rsv_items_id) REFERENCES shared_items (id) ON DELETE CASCADE
);
ALTER TABLE tb_reservations
    ALTER COLUMN reservation_id RESTART WITH 100000;

-- Insert testdata

-- Opret bruger med user_id = 100
INSERT INTO tb_users (email, password, name)
VALUES ('test@example.com', 'password123', 'Test User');

-- Opret ønskeseddel med wishlist_id = 1000 for bruger 100
INSERT INTO tb_wishlists (wl_user_id, title, share_token)
VALUES (100, 'Test Wishlist', NULL);

-- Opret item med item_id = 10000 til ønskeseddel 1000
INSERT INTO tb_items (it_wishlist_id, name, description, price, url, reserved)
VALUES (1000, 'Test Item', 'This is a test item', 123.45, 'https://example.com', FALSE);

-- Opret delt ønskeseddel med id = 2000 for ønskeseddel 1000 med et test-token
INSERT INTO shared_wishlists (original_wishlist_id, share_token)
VALUES (1000, 'test-token');

-- Opret delt item med id = 20000 til den delte ønskeseddel og link til item 10000
INSERT INTO shared_items (shared_wishlist_id, original_item_id, name, description, price, url)
VALUES (2000, 10000, 'Test Shared Item', 'This is a shared test item', 123.45, 'https://example.com/shared');

-- Opret reservation for delt item 20000
--INSERT INTO tb_reservations (rsv_items_id)
--VALUES (20000);
