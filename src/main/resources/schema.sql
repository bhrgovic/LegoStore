CREATE TABLE categories (
                            idCategory INT PRIMARY KEY AUTO_INCREMENT,
                            name NVARCHAR(50) NOT NULL
);

CREATE TABLE LEGO_PIECES (
                             ID_LEGO     INT primary key AUTO_INCREMENT,
                             NAME        VARCHAR(30)   NOT NULL,
                             CATEGORY    VARCHAR(50)   NOT NULL,
                             PRICE       DECIMAL(5, 2) NOT NULL
);

CREATE TABLE users (
                       username VARCHAR_IGNORECASE(50) NOT NULL PRIMARY KEY,
                       password VARCHAR_IGNORECASE(500) NOT NULL,
                       enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
                             username VARCHAR_IGNORECASE(50) NOT NULL,
                             authority VARCHAR_IGNORECASE(50) NOT NULL,
                             CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE ShoppingCarts (
                               cart_id INT PRIMARY KEY AUTO_INCREMENT,
                               usernamefk VARCHAR_IGNORECASE(50),
                               FOREIGN KEY (usernamefk) REFERENCES users(username)
);

CREATE TABLE CartItems (
                           cart_item_id INT PRIMARY KEY AUTO_INCREMENT,
                           usernamefk VARCHAR_IGNORECASE(50) NOT NULL,
                           Lego_id INT,
                           quantity INT,
                           price decimal (5,2),
                           bought bool,
                           FOREIGN KEY (usernamefk) REFERENCES users(username),
                           FOREIGN KEY (Lego_id) REFERENCES LEGO_PIECES(ID_LEGO)
);

CREATE TABLE Log (
    message VARCHAR(MAX)
);

create table orderHistory(
    IdOrderHistory int primary key auto_increment,
    userOrdered nvarchar(50) not null,
    price decimal(5,2)
);

