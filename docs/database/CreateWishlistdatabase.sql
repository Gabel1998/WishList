DROP
DATABASE IF EXISTS wishlistdatabase;
CREATE
DATABASE wishlistdatabase DEFAULT CHARACTER SET utf8;

USE
wishlistdatabase;

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
) AUTO_INCREMENT = 100;

-- WISHLISTS
CREATE TABLE tb_wishlists
(
    wishlist_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    wl_user_id  BIGINT NOT NULL,
    title       VARCHAR(255),
    share_token VARCHAR(255),
    FOREIGN KEY (wl_user_id) REFERENCES tb_users (user_id) ON DELETE CASCADE
) AUTO_INCREMENT = 1000;

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
) AUTO_INCREMENT = 10000;

-- SHARED WISHLISTS
CREATE TABLE shared_wishlists
(
    shared_wishlist_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sw_wishlist_id     BIGINT   NOT NULL,
    share_token        CHAR(36) NOT NULL,
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sw_wishlist_id) REFERENCES tb_wishlists (wishlist_id) ON DELETE CASCADE
) AUTO_INCREMENT = 2000;

-- SHARED ITEMS
CREATE TABLE shared_items
(
    shared_items_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    si_shared_wishlist_id BIGINT NOT NULL,
    original_item_id   BIGINT,
    name               VARCHAR(255),
    description        TEXT,
    price              DECIMAL(10, 2),
    url                VARCHAR(255),
    FOREIGN KEY (si_sw_id) REFERENCES shared_wishlists (shared_wishlist_id) ON DELETE CASCADE
) AUTO_INCREMENT = 20000;

-- RESERVATIONS
CREATE TABLE tb_reservations
(
    reservation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rsv_items_id   BIGINT UNIQUE NOT NULL,
    reserved_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (rsv_items_id) REFERENCES shared_items (shared_items_id) ON DELETE CASCADE
) AUTO_INCREMENT = 100000;