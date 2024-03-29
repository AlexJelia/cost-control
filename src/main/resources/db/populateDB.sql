DELETE
FROM user_roles;
DELETE
FROM costs;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password,costs_per_day)
VALUES ('User', 'user@yandex.ru', '{noop}password',2005),
       ('Admin', 'admin@gmail.com', '{noop}admin',1900);

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001),
       ('ROLE_USER', 100001);

INSERT INTO costs (date_time, description, cost, user_id)
VALUES ('2022-05-30 10:00:00', 'Завтрак', 500, 100000),
       ('2022-05-30 13:00:00', 'Обед', 1000, 100000),
       ('2022-05-30 20:00:00', 'Ужин', 500, 100000),
       ('2022-05-31 0:00:00', 'граничное значение', 100, 100000),
       ('2022-05-31 10:00:00', 'Завтрак', 500, 100000),
       ('2022-05-31 13:00:00', 'Обед', 1000, 100000),
       ('2022-05-31 20:00:00', 'Ужин', 510, 100000),
       ('2022-06-01 14:00:00', 'Админ ланч', 510, 100001),
       ('2022-06-01 21:00:00', 'Админ ужин', 1500, 100001);
