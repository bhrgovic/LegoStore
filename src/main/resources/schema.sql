CREATE TABLE LEGO_PIECES
(
    ID_LEGO     INT GENERATED ALWAYS AS IDENTITY,
    NAME     VARCHAR(30)   NOT NULL,
    CATEGORY VARCHAR(20)   NOT NULL,
    PRICE    DECIMAL(5, 2) NOT NULL,
    PRIMARY KEY (ID_LEGO)
);


create table users(
                      username varchar_ignorecase(50) not null primary key,
                      password varchar_ignorecase(500) not null,
                      enabled boolean not null
);

create table authorities (
                             username varchar_ignorecase(50) not null,
                             authority varchar_ignorecase(50) not null,
                             constraint fk_authorities_users foreign key(username) references users(username)
);


-- Shopping Cart table
CREATE TABLE ShoppingCarts (
                               cart_id INT PRIMARY KEY auto_increment ,
                               usernamefk varchar_ignorecase(50),
                               FOREIGN KEY (usernamefk)references users(username)
);

-- Cart Items table
CREATE TABLE CartItems (
                           cart_item_id INT PRIMARY KEY auto_increment,
                           usernamefk varchar_ignorecase(50) not null,
                           Lego_id INT,
                           quantity INT,
                           FOREIGN KEY (usernamefk) REFERENCES users(username),
                           FOREIGN KEY (Lego_id) REFERENCES LEGO_PIECES(ID_LEGO)
);

CREATE TABLE login_logs (
                            log_id INT PRIMARY KEY AUTO_INCREMENT,
                            user_id INT,
                            login_time TIMESTAMP,
                            ip_address VARCHAR(255)
);