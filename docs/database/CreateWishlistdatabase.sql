drop database if exists wishlistdatabase;
Create database WishListDataBase  DEFAULT CHARACTER SET utf8;

USE wishlistdatabase;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS wishlist;
DROP TABLE IF exists items;
DROP TABLE IF EXISTS shared_wishlists;
DROP TABLE IF EXISTS shared_items;
DROP TABLE IF EXISTS reservations;


-- USERS
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
) auto_increment = 100;

-- WISHLISTS
CREATE TABLE wishlists (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
)auto_increment = 1000;

-- ITEMS
CREATE TABLE items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    wishlist_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2),
    quantity INT DEFAULT 1,
    link TEXT,
    FOREIGN KEY (wishlist_id) REFERENCES wishlists(id) ON DELETE CASCADE
) auto_increment = 10000;

-- SHARED WISHLISTS (Read-only)
CREATE TABLE shared_wishlists (
    id INT AUTO_INCREMENT PRIMARY KEY,
    original_wishlist_id INT NOT NULL,
    share_token CHAR(36) NOT NULL, -- UUID i string-format
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (original_wishlist_id) REFERENCES wishlists(id) ON DELETE CASCADE
)auto_increment = 1000;

-- SHARED ITEMS (Snapshot af items på delingstidspunktet)
CREATE TABLE shared_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    shared_wishlist_id INT NOT NULL,
    original_item_id INT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2),
    quantity INT DEFAULT 1,
    link TEXT,
    FOREIGN KEY (shared_wishlist_id) REFERENCES shared_wishlists(id) ON DELETE CASCADE
)auto_increment = 10000;

-- RESERVATIONS (Anonyme reservationer)
CREATE TABLE reservations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    shared_item_id INT NOT NULL,
    reserved_quantity INT DEFAULT 1,
    reserved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (shared_item_id) REFERENCES shared_items(id) ON DELETE CASCADE
) auto_increment = 100000;
