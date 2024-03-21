-- User table
INSERT INTO users (created_at, updated_at, email, name, password, role, phone)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test1@test.com', 'User1', 'test1', 'USER', '010-1111-1111'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test2@test.com', 'User2', 'test2', 'USER', '010-2222-2222'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test3@test.com', 'User3', 'test3', 'ADMIN', '010-3333-3333'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test4@test.com', 'User4', 'test4', 'EVENT_MANAGER', '010-4444-4444'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test5@test.com', '동의대학교 총학생회', 'test6', 'EVENT_MANAGER', '010-5555-5555'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test6@test.com', 'User6', 'test6', 'FOOD_TRUCK_MANAGER', '010-6666-6666'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test7@test.com', 'User7', 'test7', 'FOOD_TRUCK_MANAGER', '010-7777-7777'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test8@test.com', 'User8', 'test8', 'FOOD_TRUCK_MANAGER', '010-8888-8888'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test9@test.com', 'User9', 'test9', 'FOOD_TRUCK_MANAGER', '010-9999-9999'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test10@test.com', 'User10', 'test10', 'FOOD_TRUCK_MANAGER', '010-1010-1010');


-- Event table
INSERT INTO event (created_at, updated_at, start_at, end_at, manager_id, title, content, street_address, detail_address, zip_code, image_url)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2024-03-10 10:00:00', '2024-03-10 15:00:00', 5, '동의대학교 스탬프 투어', 'Event Content 1', '부산광역시 엄광로 176',
        '지천관 앞',
        '12345', 'https://cdn.news.unn.net/news/photo/202207/530605_335239_856.jpg'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2024-03-15 12:00:00', '2024-03-15 18:00:00', 4, '부산시민공원 빛축제',
        '부산의 대표 도심공원인 부산시민공원에서 부산진구청, 부산시민공원, 부산국립국악원 3개 기관이 협력하여 제3회 부산 희망 드림 빛축제를 개최하였다. 부산의 꿈과 희망의 빛을 모아 세계로 라는 주제로 2030부산세계박람회 유치 기원 등 테마를 구성하여 다양한 빛 시설물과 함께 특색있는 볼거리를 제공한다.',
        '부산 부산진구 시민공원로 73',
        '남2문', '67890', 'https://www.visitbusan.net/uploadImgs/files/cntnts/20191227194943122_oen'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2024-03-20 14:00:00', '2024-03-20 20:00:00', 4, 'Event 3', 'Event Content 3', '789 Pine St',
        'Unit 303', '98765', 'https://chrisandpartners.co/wp-content/uploads/2021/11/%EA%B7%B8%EB%A6%BC1.jpg'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2024-03-25 16:00:00', '2024-03-25 22:00:00', 4, 'Event 4', 'Event Content 4', '101 Elm St',
        'Room 404', '54321', 'https://chrisandpartners.co/wp-content/uploads/2021/11/%EA%B7%B8%EB%A6%BC1.jpg');

INSERT INTO food_truck (created_at, manager_id, updated_at, description, food_type, image_url, name)
VALUES (CURRENT_TIMESTAMP, 6, CURRENT_TIMESTAMP, '청년버거', 'BURGER',
        'https://on.com2us.com/wp-content/uploads/2023/01/shutterstock_1938676873-scaled.jpg', 'Burger Express'),
       (CURRENT_TIMESTAMP, 7, CURRENT_TIMESTAMP, '바삭바삭 치킨트럭', 'CHICKEN',
        'https://hips.hearstapps.com/hmg-prod/images/delish-230502-06-fried-chicken-0887-eb-index-645ec6fb0ca64.jpg?crop=0.888888888888889xw:1xh;center,top&resize=1200:*',
        'Chicken Delight'),
       (CURRENT_TIMESTAMP, 8, CURRENT_TIMESTAMP, '달달한 디저트', 'DESSERT',
        'https://staticcookist.akamaized.net/wp-content/uploads/sites/22/2022/06/LINK-TRAFFIC-18-1200x675.jpg', 'Sweet Treats Truck'),
       (CURRENT_TIMESTAMP, 9, CURRENT_TIMESTAMP, '즉석 칵테일', 'DRINK',
        'https://i.namu.wiki/i/MC8pCMtJ1hoZsxKeED62zg00dGA3a6mZaXbWJTqpLkUw6tl3d-mho_9a969GFX2g8H-7wwdEobOeP2HjAVEgsg.webp', 'Thirst Quencher Truck'),
       (CURRENT_TIMESTAMP, 10, CURRENT_TIMESTAMP, '핫~! 도그', 'HOTDOG',
        'https://static.wtable.co.kr/image/production/service/recipe/1793/78e41de9-7045-41e0-8a44-6cbbcd65ddd5.jpg?size=800x800', 'Hotdog Haven');

-- Review table
INSERT INTO food_truck_review (created_at, updated_at, content, food_truck_id, image_url, reply, score, author_id)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '청년버거 너무 맛있어요!', 1, 'https://picsum.photos/1600/900', NULL, 5, 1),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '치킨이 아주 바삭바삭해요!', 2, 'https://picsum.photos/1600/900', '감사합니다!', 4, 1),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '디저트도 다채로워서 좋아요', 3, 'https://picsum.photos/1600/900', '감사합니다!', 4, 2),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '칵테일이 시원하고 맛있어요', 4, 'https://picsum.photos/1600/900', NULL, 5, 3),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '핫도그가 정말 핫해요!', 5, 'https://picsum.photos/1600/900', NULL, 4, 4);
