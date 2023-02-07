--liquibase formatted sql

--changeset apejkovic:1
CREATE TABLE users (
    id INT(11) NOT NULL AUTO_INCREMENT,
    first_name varchar(60) NOT NULL,
    last_name varchar(60) NOT NULL,
    username varchar(60) NOT NULL UNIQUE,
    password varchar(300) NOT NULL,
    email varchar(60) NOT NULL UNIQUE,
    enabled BOOLEAN NOT NULL,
    about varchar(600) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    secret_question varchar(60) NOT NULL,
    secret_answer varchar(60) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO users VALUES
    (1,'Test','Admin','admin','$2a$10$c4k24Pk4lNy/v9wEZRsuT.LrTsYRLK7Jj7.mLahhCZwCgoWwAY7IW','admin@mail.com', true, 'About Me Text', '2017-10-10 10:45:08', 'Question?', 'Answer'),
    (2,'Test','User','user','$2a$10$c4k24Pk4lNy/v9wEZRsuT.LrTsYRLK7Jj7.mLahhCZwCgoWwAY7IW','user@mail.com', true, 'About Me Text', '2018-11-14 09:55:09', 'Question?', 'Answer'),
    (3,'Finn','Mertens','finn','$2a$10$89sFnX8cLps2Oaa/yAizpuhDQ6OsEICMOjlKRnOa6SaVNLhhrCOWy','finn@mail.com', true, 'About Me Text', '2018-12-12 10:50:03', 'Question?', 'Answer'),
    (4,'Marceline','Abadeer','marceline','$2a$10$89sFnX8cLps2Oaa/yAizpuhDQ6OsEICMOjlKRnOa6SaVNLhhrCOWy','marceline@mail.com', true, 'About Me Text', '2018-12-13 11:53:04', 'Question?', 'Answer'),
    (5,'Princess','Bubblegum','bubble','$2a$10$89sFnX8cLps2Oaa/yAizpuhDQ6OsEICMOjlKRnOa6SaVNLhhrCOWy','princess@mail.com', true, 'About Me Text', '2018-12-14 11:54:05', 'Question?', 'Answer'),
    (6,'Simon','Petrikov','iceking','$2a$10$89sFnX8cLps2Oaa/yAizpuhDQ6OsEICMOjlKRnOa6SaVNLhhrCOWy','simon@mail.com', true, 'About Me Text', '2018-12-15 11:55:06', 'Question?', 'Answer'),
    (7,'Jake','The Dog','jake','$2a$10$89sFnX8cLps2Oaa/yAizpuhDQ6OsEICMOjlKRnOa6SaVNLhhrCOWy','jake@mail.com', true, 'About Me Text', '2018-12-16 11:56:07', 'Question?', 'Answer'),
    (8,'Vatroslav','Palikuća','piroman','$2a$10$89sFnX8cLps2Oaa/yAizpuhDQ6OsEICMOjlKRnOa6SaVNLhhrCOWy','plamen@mail.com', true, 'About Me Text', '2019-02-16 11:48:31', 'Question?', 'Answer'),
    (9,'Josip','Broz','tito','$2a$10$89sFnX8cLps2Oaa/yAizpuhDQ6OsEICMOjlKRnOa6SaVNLhhrCOWy','sfrj@mail.com', true, 'About Me Text', '2019-05-25 10:00:00', 'Question?', 'Answer'),
    (10,'Filet','Oslić','filet','$2a$10$89sFnX8cLps2Oaa/yAizpuhDQ6OsEICMOjlKRnOa6SaVNLhhrCOWy','oslic@mail.com', true, 'About Me Text', '2019-05-31 10:32:47', 'Question?', 'Answer'),
    (11,'Deda','Hadži-Dedić','dedoje','$2a$10$89sFnX8cLps2Oaa/yAizpuhDQ6OsEICMOjlKRnOa6SaVNLhhrCOWy','deda@mail.com', true, 'About Me Text', '2019-06-06 10:03:15', 'Question?', 'Answer'),
    (12,'Zgembo','Brbljar','zgembo','$2a$10$89sFnX8cLps2Oaa/yAizpuhDQ6OsEICMOjlKRnOa6SaVNLhhrCOWy','zgembo@mail.com', true, 'About Me Text', '2020-07-14 10:15:00', 'Question?', 'Answer');

CREATE TABLE heroes (
    id INT(11) NOT NULL AUTO_INCREMENT,
    name varchar(60) NOT NULL UNIQUE,
    type varchar(60) NOT NULL,
    level integer NOT NULL,
    creation_date TIMESTAMP,
    user_id INT(11) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

INSERT INTO heroes VALUES
    (1,'TestHero','Engineer',50, '2018-12-15 10:10:10', '2'),
    (2,'Rod','Warrior',80, '2018-12-17 11:50:05', '7'),
    (3,'Nestvarna','Mesmer',60, '2018-12-17 10:21:06', '3'),
    (4,'Studena','Guardian',50, '2018-12-17 11:52:07', '3'),
    (5,'Mora','Revenant',75, '2018-12-18 10:13:08', '3'),
    (6,'Opsena','Thief',70, '2018-12-19 11:24:09', '3'),
    (7,'Ledena','Elementalist',80, '2018-12-20 10:55:10', '6'),
    (8,'Madrigaile','Ranger',80, '2018-12-21 11:56:11', '5'),
    (9,'Tigraine','Elementalist',40, '2018-12-22 10:57:12', '5'),
    (10,'Aladdin','Thief',35, '2018-12-23 11:41:12', '4'),
    (11,'Vrbena','Guardian',55, '2018-12-31 11:59:59', '3'),
    (12,'Lucky Luke','Ranger',65, '2019-01-10 09:12:32', '4'),
    (13,'Semafor','Engineer',40, '2019-06-02 07:18:31', '10'),
    (14,'Uglješa','Revenant',20, '2019-06-10 01:00:00', '8'),
    (15,'Baba','Necromancer',80, '2019-06-12 04:30:25', '11'),
    (16,'Navrdeda','Revenant',1, '2019-06-12 04:31:45', '11'),
    (17,'Drndoje','Warrior',25, '2019-06-13 10:58:00', '10'),
    (18,'Frfljavi','Elementalist',10, '2020-07-16 10:23:51', '12');

CREATE TABLE roles (
    id INT(11) NOT NULL AUTO_INCREMENT,
    name VARCHAR(36) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

INSERT INTO roles VALUES
	(1, 'ADMIN'),
	(2, 'USER');

CREATE TABLE users_roles (
  id INT(11) NOT NULL AUTO_INCREMENT,
  user_id INT(11) NOT NULL,
  role_id INT(11) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT
    FOREIGN KEY (user_id)
    REFERENCES users(id),
  CONSTRAINT
    FOREIGN KEY (role_id)
    REFERENCES roles(id)
  );

INSERT INTO users_roles (user_id, role_id) VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (3, 2),
    (4, 2),
    (5, 2),
    (6, 2),
    (7, 2),
    (8, 2),
    (9, 2),
    (10, 2),
    (11, 2),
    (12, 2);

CREATE TABLE permissions (
    id INT(11) NOT NULL AUTO_INCREMENT,
    name VARCHAR(36) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

INSERT INTO permissions VALUES
	(1, 'READ'),
	(2, 'WRITE'),
	(3, 'UPDATE'),
	(4, 'DELETE');

CREATE TABLE roles_permissions (
  id INT(11) NOT NULL AUTO_INCREMENT,
  role_id INT(11) NOT NULL,
  permission_id INT(11) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT
    FOREIGN KEY (role_id)
    REFERENCES roles(id),
  CONSTRAINT
    FOREIGN KEY (permission_id)
    REFERENCES permissions(id)
  );

INSERT INTO roles_permissions (role_id, permission_id) VALUES
    (1, 4),
    (2, 1),
    (2, 2),
    (2, 3);

--CREATE TABLE events (
--    event_id VARCHAR(60),
--    category VARCHAR(60),
--    object VARCHAR(100),
--    action VARCHAR(60),
--    user VARCHAR(60),
--    date TIMESTAMP,
--    KEY (event_id)
--);
--
--CREATE TABLE properties (
--    property_id VARCHAR(36),
--    name VARCHAR(36),
--    value VARCHAR(60),
--    PRIMARY KEY (property_id)
--);
--
--INSERT INTO properties VALUES
--    ('1', 'registration_code', '123456789'),
--    ('2', 'primary_admin_id', '6'),
--    ('3', 'random_key', 'someValue'),
--    ('4', 'allow_users_key_share', 'yes');
--
--CREATE TABLE `samsara`.`verificationtoken` (
--    `id` BIGINT(20) NOT NULL,
--    `expiryDate` DATETIME(6) NOT NULL,
--    `token` VARCHAR(255) NOT NULL,
--    `user_id` VARCHAR(36) NOT NULL,
--    PRIMARY KEY (`id`),
--    INDEX `user_id_fk_idx` (`user_id` ASC),
--    CONSTRAINT `user_id_fk`
--        FOREIGN KEY (`user_id`)
--        REFERENCES `samsara`.`users` (`user_id`)
--        ON DELETE NO ACTION
--        ON UPDATE NO ACTION
--    );
--
--CREATE TABLE avatars (
--    id BIGINT(20) NOT NULL AUTO_INCREMENT,
--    image_name VARCHAR(255) NOT NULL,
--    image_type VARCHAR(255) NOT NULL,
--    image MEDIUMBLOB NOT NULL,
--    user_id VARCHAR(36) NOT NULL,
--    PRIMARY KEY (id),
--    CONSTRAINT
--        FOREIGN KEY (user_id)
--        REFERENCES samsara.users(user_id)
--        ON DELETE NO ACTION
--        ON UPDATE NO ACTION
--);
