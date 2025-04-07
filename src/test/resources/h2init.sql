DROP TABLE IF EXISTS tb_users;
DROP TABLE IF EXISTS tb_wishlists;
DROP TABLE IF EXISTS tb_items;
DROP TABLE IF EXISTS shared_wishlists;
DROP TABLE IF EXISTS shared_items;
DROP TABLE IF EXISTS tb_reservations;

CREATE TABLE tb_users
(
    user_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(255),
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE tb_wishlists
(
    wishlist_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(255) NOT NULL,
    wl_user_id  BIGINT       NOT NULL,
    FOREIGN KEY (wl_user_id) REFERENCES tb_users (user_id)
);

CREATE TABLE tb_items
(
    item_id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    it_wishlist_id BIGINT       NOT NULL,
    name           VARCHAR(255) NOT NULL,
    description    TEXT,
    price          DECIMAL(10, 2),
    url            VARCHAR(512),
    FOREIGN KEY (it_wishlist_id) REFERENCES tb_wishlists (wishlist_id)
);

CREATE TABLE shared_wishlists
(
    shared_wishlist_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    original_wishlist_id BIGINT       NOT NULL,
    share_token          VARCHAR(255) NOT NULL,
    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (original_wishlist_id) REFERENCES tb_wishlists (wishlist_id)
);

CREATE TABLE shared_items
(
    shared_item_id     BIGINT PRIMARY KEY AUTO_INCREMENT,
    shared_wishlist_id BIGINT NOT NULL,
    original_item_id   BIGINT NOT NULL,
    name               VARCHAR(255),
    description        TEXT,
    price              DECIMAL(10, 2),
    url                VARCHAR(512),
    FOREIGN KEY (shared_wishlist_id) REFERENCES shared_wishlists (shared_wishlist_id),
    FOREIGN KEY (original_item_id) REFERENCES tb_items (item_id)
);

CREATE TABLE tb_reservations
(
    reservation_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    rsv_items_id BIGINT NOT NULL,
    FOREIGN KEY (rsv_items_id) REFERENCES shared_items (shared_item_id)
);

-- === TESTDATA ===

-- Bruger
INSERT INTO tb_users (user_id, email, password)
VALUES (1000, 'test@example.com', 'hashed-password');

-- Ønskeseddel
INSERT INTO tb_wishlists (wishlist_id, title, wl_user_id)
VALUES (1000, 'Min Fødselsdag', 1000);

-- Ønske
INSERT INTO tb_items (item_id, it_wishlist_id, name, description, price, url)
VALUES (1000, 1000, 'Ny cykel', 'Sort citybike med 7 gear', 3999.95, 'https://eksempel.dk/cykel');

-- Deling (så du kan teste shareWishlist())
INSERT INTO shared_wishlists (shared_wishlist_id, original_wishlist_id, share_token, created_at)
VALUES (1000, 1000, 'test-share-token-123', CURRENT_TIMESTAMP);

-- Kopieret ønske til readonly view
INSERT INTO shared_items (shared_item_id, shared_wishlist_id, original_item_id, name, description, price, url)
VALUES (1000,
        1000,
        1000,
        'Ny cykel',
        'Sort citybike med 7 gear',
        3999.95,
        'https://eksempel.dk/cykel');

-- En reservation
INSERT INTO tb_reservations (reservation_id, rsv_items_id)
VALUES (1000, 1000);
