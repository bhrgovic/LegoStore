
insert into categories (name) values('MINDSTORMS');
insert into categories (name) values('CITY');
insert into categories (name) values('DUPLO');
insert into categories (name) values('BIONICLE');
insert into categories (name) values('NINJAGO');
insert into categories (name) values('RACERS');

INSERT INTO LEGO_PIECES(NAME, CATEGORY, PRICE) VALUES('Mindstorms v3', 'MINDSTORMS', 342.12);
INSERT INTO LEGO_PIECES(NAME, CATEGORY, PRICE) VALUES('Firefighters', 'CITY', 42.12);
INSERT INTO LEGO_PIECES(NAME, CATEGORY, PRICE) VALUES('Camp Nou', 'CITY', 312.12);
INSERT INTO LEGO_PIECES(NAME, CATEGORY, PRICE) VALUES('Train', 'CITY', 142.12);
INSERT INTO LEGO_PIECES(NAME, CATEGORY, PRICE) VALUES('PEPPA PIG', 'DUPLO', 22.12);

INSERT INTO users VALUES('user', '$2a$12$RNULg/Myxn7Ib0b0Ljscfedb7O7oAzmsvIctsloTNMjt7ORdFaCs2', true); --password: password
INSERT INTO users VALUES('admin', '$2a$12$RNULg/Myxn7Ib0b0Ljscfedb7O7oAzmsvIctsloTNMjt7ORdFaCs2', true); --password: password

INSERT INTO authorities VALUES('user', 'ROLE_USER');
INSERT INTO authorities VALUES('admin', 'ROLE_ADMIN');
INSERT INTO authorities VALUES('admin', 'ROLE_USER');


insert into CartItems(usernamefk,Lego_id,quantity) values('admin',1,1);
insert into CartItems(usernamefk,Lego_id,quantity) values('admin',2,1);

insert into orderHistory(userOrdered,price) values('admin',499.99);
insert into orderHistory(userOrdered,price) values('admin',489.99);
insert into orderHistory(userOrdered,price) values('admin',199.99);
