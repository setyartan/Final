USE dbase;

-- ================================================
-- DROPPING EXISTING TABLES
-- ================================================
DROP TABLE IF EXISTS periodicals_of_user;
DROP TABLE IF EXISTS periodicals_of_theme;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS blocked_users;
DROP TABLE IF EXISTS periodicals;
DROP TABLE IF EXISTS themes;

-- ------------------------------------------------
-- ROLES
-- ------------------------------------------------
CREATE TABLE roles
(
-- id has the INTEGER type (other name is INT), it is the primary key
    id   INT         NOT NULL PRIMARY KEY,

-- name has the VARCHAR type - a string with a variable length
-- names values should not be repeated (UNIQUE)
    name VARCHAR(10) NOT NULL UNIQUE
);

-- ------------------------------------------------
-- inserting data into roles table
-- ------------------------------------------------
INSERT INTO roles VALUE (0, 'admin');
INSERT INTO roles VALUE (1, 'user');

-- ------------------------------------------------
-- USERS
-- ------------------------------------------------
CREATE TABLE users
(
    id         INT PRIMARY KEY AUTO_INCREMENT,

-- 'UNIQUE' means logins values should not be repeated in login column of table
    login      VARCHAR(20) NOT NULL UNIQUE,

-- not null string columns
    password   VARCHAR(20) NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    last_name  VARCHAR(20) NOT NULL,

-- this declaration contains the foreign key constraint
-- role_id in users table is associated with id in roles table
-- role_id of user = id of role
    role_id    INTEGER     NOT NULL REFERENCES roles (id)

-- removing a row with some ID from roles table implies removing
-- all rows from users table for which ROLES_ID=ID
-- default value is ON DELETE RESTRICT, it means you cannot remove
-- row with some ID from the roles table if there are rows in
-- users table with ROLES_ID=ID
        ON DELETE CASCADE

-- the same as previous but updating is used insted deleting
        ON UPDATE RESTRICT,

    account    LONG
);

-- ------------------------------------------------
-- inserting data into roles table
-- ------------------------------------------------
-- id = 1
INSERT INTO users
VALUES (DEFAULT, 'admin', 'admin', 'Ivan', 'Ivanov', 0, 0);
INSERT INTO users
VALUES (DEFAULT, 'aaa', '12345', 'Ivan', 'Ivanov', 1, 1000000);
INSERT INTO users
VALUES (DEFAULT, 'bbb', '12345', 'Ivan', 'Ivanov', 1, 100000000);
INSERT INTO users
VALUES (DEFAULT, 'ccc', '12345', 'Ivan', 'Ivanov', 1, 10000);

-- ------------------------------------------------
-- USERS
-- ------------------------------------------------

CREATE TABLE blocked_users
(
    user_id INT REFERENCES users (id) ON DELETE CASCADE,
    UNIQUE (user_id)
);

-- ------------------------------------------------
-- PERIODICALS
-- ------------------------------------------------
CREATE TABLE periodicals
(
    id    INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(20) UNIQUE,
    price INT(10)
);

-- ------------------------------------------------
-- inserting data into periodicals table
-- ------------------------------------------------
INSERT INTO periodicals
VALUES (DEFAULT, 'EvgeniyIsLove', 1000);
INSERT INTO periodicals
VALUES (DEFAULT, 'EvgeniyIsTheBest', 100000);
INSERT INTO periodicals
VALUES (DEFAULT, 'EvgeniyIsGenius', 100);
INSERT INTO periodicals
VALUES (DEFAULT, 'EvgeniyIsGenius2', 10000);

SELECT *
FROM periodicals
ORDER BY name AND price DESC;


-- ------------------------------------------------
-- PERIODICALS_OF_USER REFERENCES
-- ------------------------------------------------
CREATE TABLE periodicals_of_user
(
    user_id       INT REFERENCES users (id) ON DELETE CASCADE,
    periodical_id INT REFERENCES periodicals (id) ON DELETE CASCADE,
    UNIQUE (user_id, periodical_id)
);

INSERT INTO periodicals_of_user
VALUES (2, 1);
INSERT INTO periodicals_of_user
VALUES (2, 2);

-- ------------------------------------------------
-- THEMES
-- ------------------------------------------------
CREATE TABLE themes
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) UNIQUE
);

-- ------------------------------------------------
-- inserting data into themes table
-- ------------------------------------------------

INSERT INTO themes
VALUES (DEFAULT, 'Love');
INSERT INTO themes
VALUES (DEFAULT, 'Government');
INSERT INTO themes
VALUES (DEFAULT, 'Environment');

-- ------------------------------------------------
-- THEMES_OF_PERIODICALS REFERENCES
-- ------------------------------------------------
CREATE TABLE periodicals_of_theme
(
    theme_id      INT REFERENCES themes (id) ON DELETE CASCADE,
    periodical_id INT REFERENCES periodicals (id) ON DELETE CASCADE,
    UNIQUE (theme_id, periodical_id)
);

INSERT INTO periodicals_of_theme
VALUES (1, 1);
INSERT INTO periodicals_of_theme
VALUES (2, 2);
INSERT INTO periodicals_of_theme
VALUES (3, 3);
INSERT INTO periodicals_of_theme
VALUES (3, 4);

-- --------------------------------------------------------------
-- test database:
-- --------------------------------------------------------------
SELECT *
FROM periodicals_of_theme;
SELECT *
FROM themes;
SELECT *
FROM periodicals_of_user;
SELECT *
FROM periodicals;
SELECT *
FROM users;
SELECT *
FROM roles;


