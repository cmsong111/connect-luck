-- User table
INSERT INTO users (created_at, updated_at, email, name, password, role)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test1@test.com', 'User1', 'test1', 'USER'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test2@test.com', 'User2', 'test2', 'USER'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test3@test.com', 'User3', 'test3', 'ADMIN'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test4@test.com', 'User4', 'test4', 'FOOD_TRUCK_MANAGER'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test5@test.com', 'User5', 'test5', 'EVENT_MANAGER'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test6@test.com', '동의대학교 총학생회', 'test6', 'EVENT_MANAGER');

-- Event table
INSERT INTO event (created_at, updated_at, start_at, end_at, manager_id, title, content, street_address, detail_address, zip_code, image_url)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2024-03-10 10:00:00', '2024-03-10 15:00:00', 6, '동의대학교 스탬프 투어', 'Event Content 1', '부산광역시 엄광로 176', '지천관 앞',
        '12345', 'https://cdn.news.unn.net/news/photo/202207/530605_335239_856.jpg'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2024-03-15 12:00:00', '2024-03-15 18:00:00', 5, 'Event 2', 'Event Content 2', '456 Oak St',
        'Suite 202', '67890', 'https://www.visitbusan.net/uploadImgs/files/cntnts/20191227194943122_oen'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2024-03-20 14:00:00', '2024-03-20 20:00:00', 5, 'Event 3', 'Event Content 3', '789 Pine St',
        'Unit 303', '98765', 'https://chrisandpartners.co/wp-content/uploads/2021/11/%EA%B7%B8%EB%A6%BC1.jpg'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2024-03-25 16:00:00', '2024-03-25 22:00:00', 5, 'Event 4', 'Event Content 4', '101 Elm St',
        'Room 404', '54321', 'https://chrisandpartners.co/wp-content/uploads/2021/11/%EA%B7%B8%EB%A6%BC1.jpg');
