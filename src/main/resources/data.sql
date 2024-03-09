-- User table
INSERT INTO users (created_at, updated_at, email, name, password, role)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test1@test.com', 'User1', 'test1', 'USER'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test2@test.com', 'User2', 'test2', 'USER'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test3@test.com', 'User3', 'test3', 'ADMIN'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test4@test.com', 'User4', 'test4', 'FOOD_TRUCK_MANAGER'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test5@test.com', 'User5', 'test5', 'EVENT_MANAGER'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test6@test.com', '동의대학교 총학생회', 'test6', 'EVENT_MANAGER');

