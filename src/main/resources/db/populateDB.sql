DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories) VALUES
(100000, '2018-05-16 10:00:00', 'Завтрак', 600),
(100000, '2018-05-16 13:00:00', 'Обед', 1000),
(100000, '2018-05-16 19:00:00', 'Ужин', 400),
(100000, '2018-05-17 11:00:00', 'Завтрак', 500),
(100000, '2018-05-17 14:00:00', 'Обед', 900),
(100000, '2018-05-17 19:00:00', 'Ужин', 510),
(100000, '2018-05-18 10:00:00', 'Завтрак', 700),
(100000, '2018-05-18 13:00:00', 'Обед', 700),
(100000, '2018-05-18 20:00:00', 'Ужин', 700),
(100001, '2018-05-22 14:00:00', 'Админ Обед', 2100),
(100001, '2018-05-21 10:00:00', 'Админ Завтрак', 1600),
(100001, '2018-05-23 20:00:00', 'Админ Ужин', 1700);