drop table if exists users;
drop table if exists person;
drop table if exists cars;
drop table if exists comments;

create table users
(
    id       identity          NOT NULL PRIMARY KEY,
    username varchar(255) not null,
    password varchar(255) not null,
    PRIMARY KEY (ID)
);

create table hashedUsers
(
    id       identity          NOT NULL PRIMARY KEY,
    username varchar(255) not null,
    passwordHash varchar(64) not null,
    salt varchar(64) not null,
    totpKey varchar(255) null,
    PRIMARY KEY (ID)
);

create table persons
(
    id             identity          NOT NULL PRIMARY KEY,
    firstName      varchar(255) not null,
    lastName       varchar(255) not null,
    email          varchar(255) not null,
    PRIMARY KEY (ID)
);

create table books
(
    id             identity          NOT NULL PRIMARY KEY,
    title          varchar(255) not null,
    description    varchar(255) not null,
    author         varchar(255) not null,
    PRIMARY KEY (ID)
);

create table genres
(
    id             identity          NOT NULL PRIMARY KEY,
    name           varchar(255) not null,
    PRIMARY KEY (ID)
);

create table books_to_genres
(
    bookId              int     NOT NULL,
    genreId             int     NOT NULL
);

create table ratings
(
    bookId             int     NOT NULL,
    userId              int     NOT NULL,
    rating              int     NOT NULL
);

create table comments
(
    id             identity          NOT NULL PRIMARY KEY,
    bookId        int          NOT NULL,
    userId         int          NOT NULL,
    comment        varchar(500) NOT NULL,
    PRIMARY KEY (ID)
);

create table user_to_roles(
    userId         int          NOT NULL,
    roleId         int          NOT NULL
);

create table roles(
    id             identity          NOT NULL,
    name           varchar(255) NOT NULL,
    PRIMARY KEY (ID)
);

create table permissions(
    id             identity          NOT NULL,
    name           varchar(255) NOT NULL,
    PRIMARY KEY (ID)
);

create table role_to_permissions(
    roleId         int          NOT NULL,
    permissionId   int          NOT NULL
);