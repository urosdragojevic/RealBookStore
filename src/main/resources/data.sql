insert into users(username, password)
values ('bruce', 'wayne'),
       ('sam', 'security_rules'),
       ('tom', 'guessmeifyoucan'),
       ('toeLover', 'blood_everywhere');

insert into persons(firstName, lastName, email)
values ('Bruce', 'Wayne', 'notBatman@gmail.com'),
       ('Sam', 'Vimes', 'night-watch@gmail.com'),
       ('Tom', 'Riddle', 'theyGotMyNose@gmail.com'),
       ('Quentin', 'Tarantino', 'qt5@gmail.com');

insert into books(title, description, author)
values ('The Lord of the Rings', 'Set in Middle-earth, the story began as a sequel to Tolkien''s 1937 children''s book The Hobbit, but eventually developed into a much larger work.', 'J.R.R. Tolkien'),
       ('Dune', 'Dune is set in the distant future in a feudal interstellar society in which various noble houses control planetary fiefs.', 'Frank Herbert'),
       ('Grundrisse', 'The series of seven notebooks rough-drafted by Marx, chiefly for purposes of self-clarification, during the winter of 1857-8.', 'Karl Marx');

insert into genres(name)
values ('non-fiction'),
       ('sci-fi'),
       ('epic fantasy'),
       ('horror');

insert into books_to_genres(bookId, genreId)
values (1, 3),
       (1, 2),
       (2, 2),
       (3, 1);

insert into ratings(bookId, userId, rating)
values (1, 3, 5),
        (3, 2, 1),
        (3, 1, 3),
        (1, 1, 5),
        (1, 2, 4);

insert into comments(bookId, userId, comment)
values (1, 1, 'They are taking the hobbits to Isengard. P.S. I am not Batman');

insert into roles(name)
values ('ADMIN'),
       ('MANAGER'),
       ('REVIEWER');

insert into user_to_roles(userId, roleId)
values (1, 3),
       (2, 3),
       (3, 1),
       (4, 2);

