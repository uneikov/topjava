DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

/*------------------------------- populate users table ------------------------------*/
INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

/*------------------------------- populate user_roles table ------------------------------*/
INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

/*------------------------------- populate meals table ------------------------------*/
DELETE FROM meals;
/*USER_MEAL_1*/
INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2016.09.09 12:33', 'breakfast', 450, (SELECT id from users WHERE name='User'));
/*USER_MEAL_2*/
INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2016.09.09 17:05', 'dinner', 800, (SELECT id from users WHERE name='User'));
/*ADMIN_MEAL_1*/
INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2016.09.10 20:05', 'supper', 800, (SELECT id from users WHERE name='Admin'));
