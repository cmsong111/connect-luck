-- User table

INSERT INTO users (created_at, updated_at, email, name, password, phone)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test1@test.com', 'User1', '$2a$10$WDkAAQdIbzFjdI1qGiueL.MGxSeI1khstMmVPuT/KEQkUz.RXTZ2u',
        '010-1111-1111'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test2@test.com', 'User2', '$2a$10$Z8ehQ.YaREkqcguDP/dX..IL4KyN3il9st.DC5VRcZegobg7JUOo6',
        '010-2222-2222'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test3@test.com', 'User3', '$2a$10$OdU9Fx6Q.eZ4ah9Qg4mFRuwAZFRpYuM.i7ebLDwzwd/MhSa1va462',
        '010-3333-3333'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test4@test.com', 'User4', '$2a$10$Mms25SF1beM78RfGr1Uh2e9yrGZmNYbVI5TW6tjT4PeAa8SPZVjeG',
        '010-4444-4444'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test5@test.com', '동의대학교 총학생회', '$2a$10$OMFRxD2wnt5wBzVZdIlQXegQKFvW53KGj0MgjUni8gaDbufEOg8Ru',
        '010-5555-5555'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test6@test.com', 'User6', '$2a$10$IiHU7oYPY6QlJ0BgLbb3ZOQdLyYQrdVEM9zIrOkYV5nUYAEZ5XrdS',
        '010-6666-6666'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test7@test.com', 'User7', '$2a$10$v09rUTPHS9.F8ucKTDQOQehllhOS2pAjghnFRCteoC0Hqnr3SsvwS',
        '010-7777-7777'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test8@test.com', 'User8', '$2a$10$2FSbkUsqf0VcHLnb0f52P.4zKiI5MfHV90dfN2cYeO2EaATl8QGp',
        '010-8888-8888'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test9@test.com', 'User9', '$2a$10$kS8sybs8szmK4AvKx2e1buurVutp6ZUmhMt9vytHLCKx6l9/0h.2G',
        '010-9999-9999'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test10@test.com', 'User10', '$2a$10$Oi98TGDRd4zJhl6PWLGQtufD.D9p967eyFNd3LwSOjYQ89AgC6tFW',
        '010-1010-1010');

-- user Role
INSERT INTO users_roles (users_id, roles)
VALUES (1, 'USER'),
       (2, 'USER'),
       (3, 'ADMIN'),
       (3, 'USER'),
       (4, 'EVENT_MANAGER'),
       (5, 'EVENT_MANAGER'),
       (6, 'FOOD_TRUCK_MANAGER'),
       (7, 'FOOD_TRUCK_MANAGER'),
       (8, 'FOOD_TRUCK_MANAGER'),
       (9, 'FOOD_TRUCK_MANAGER'),
       (10, 'FOOD_TRUCK_MANAGER');


-- Event table
INSERT INTO event (created_at, updated_at, start_at, end_at, manager_id, title, content, street_address, detail_address, zip_code, image_url, status)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2024-03-10 10:00:00', '2024-03-10 15:00:00', 5, '동의대학교 스탬프 투어', 'Event Content 1', '부산광역시 엄광로 176',
        '지천관 앞',
        '12345', 'https://cdn.news.unn.net/news/photo/202207/530605_335239_856.jpg', 'EVENT_END'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2024-03-15 12:00:00', '2024-03-15 18:00:00', 4, '부산시민공원 빛축제',
        '부산의 대표 도심공원인 부산시민공원에서 부산진구청, 부산시민공원, 부산국립국악원 3개 기관이 협력하여 제3회 부산 희망 드림 빛축제를 개최하였다. 부산의 꿈과 희망의 빛을 모아 세계로 라는 주제로 2030부산세계박람회 유치 기원 등 테마를 구성하여 다양한 빛 시설물과 함께 특색있는 볼거리를 제공한다.',
        '부산 부산진구 시민공원로 73',
        '남2문', '67890', 'https://www.visitbusan.net/uploadImgs/files/cntnts/20191227194943122_oen', 'OPEN_FOR_APPLICATION'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2024-03-20 14:00:00', '2024-03-20 20:00:00', 4, 'Event 3', 'Event Content 3', '789 Pine St',
        'Unit 303', '98765', 'https://chrisandpartners.co/wp-content/uploads/2021/11/%EA%B7%B8%EB%A6%BC1.jpg', 'BEFORE_APPLICATION'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2024-03-25 16:00:00', '2024-03-25 22:00:00', 4, 'Event 4', 'Event Content 4', '101 Elm St',
        'Room 404', '54321', 'https://chrisandpartners.co/wp-content/uploads/2021/11/%EA%B7%B8%EB%A6%BC1.jpg', 'BEFORE_APPLICATION');

INSERT INTO food_truck (created_at, manager_id, updated_at, description, food_type, image_url, name)
VALUES (CURRENT_TIMESTAMP, 6, CURRENT_TIMESTAMP, '신상 수제버거집의 청년버거', 'BURGER',
        'https://on.com2us.com/wp-content/uploads/2023/01/shutterstock_1938676873-scaled.jpg', '청년버거'),
       (CURRENT_TIMESTAMP, 7, CURRENT_TIMESTAMP, '한마리 닭통구이 트럭', 'CHICKEN',
        'https://hips.hearstapps.com/hmg-prod/images/delish-230502-06-fried-chicken-0887-eb-index-645ec6fb0ca64.jpg?crop=0.888888888888889xw:1xh;center,top&resize=1200:*',
        '바삭바삭 치킨트럭'),
       (CURRENT_TIMESTAMP, 8, CURRENT_TIMESTAMP, '달달한 디저트', 'DESSERT',
        'https://staticcookist.akamaized.net/wp-content/uploads/sites/22/2022/06/LINK-TRAFFIC-18-1200x675.jpg', 'Sweet Treats Truck'),
       (CURRENT_TIMESTAMP, 9, CURRENT_TIMESTAMP, '즉석 칵테일', 'DRINK',
        'https://i.namu.wiki/i/MC8pCMtJ1hoZsxKeED62zg00dGA3a6mZaXbWJTqpLkUw6tl3d-mho_9a969GFX2g8H-7wwdEobOeP2HjAVEgsg.webp', 'Thirst Quencher Truck'),
       (CURRENT_TIMESTAMP, 10, CURRENT_TIMESTAMP, '핫~! 도그', 'HOTDOG',
        'https://static.wtable.co.kr/image/production/service/recipe/1793/78e41de9-7045-41e0-8a44-6cbbcd65ddd5.jpg?size=800x800', 'Hotdog Haven');

-- Review table
INSERT INTO food_truck_review (created_at, updated_at, content, food_truck_id, image_url, reply, score, author_id)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '청년버거 너무 맛있어요!', 1, 'https://picsum.photos/1600/900', NULL, 5, 1),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '음식이 정말 맛있어요!', 1, 'https://picsum.photos/1600/900', NULL, 5, 2),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '서비스가 훌륭해요. 빠른 응대 감사합니다!', 1, 'https://picsum.photos/1600/900', NULL, 5, 3),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '햄버거가 쫄깃하고 맛있어요. 다음에 또 방문할게요!', 1, 'https://picsum.photos/1600/900', NULL, 4, 4),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '음식 트럭이 깨끗하고 분위기도 좋아요. 추천합니다!', 1, 'https://picsum.photos/1600/900', NULL, 5, 5),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '햄버거 사이즈가 크고 맛있습니다. 가격도 저렴해서 좋아요!', 1, 'https://picsum.photos/1600/900', NULL, 4, 6),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '치킨이 아주 바삭바삭해요!', 2, 'https://picsum.photos/1600/900', '감사합니다!', 4, 1),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '디저트도 다채로워서 좋아요', 3, 'https://picsum.photos/1600/900', '감사합니다!', 4, 2),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '칵테일이 시원하고 맛있어요', 4, 'https://picsum.photos/1600/900', NULL, 5, 3),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '핫도그가 정말 핫해요!', 5, 'https://picsum.photos/1600/900', NULL, 4, 4);

-- Menu table
INSERT INTO food_truck_menu (price, created_at, food_truck_id, updated_at, description, image_url, name)
VALUES (15000, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, '신제품 맥크리스피 토마토 치즈 크러스트', 'https://picsum.photos/1600/900', '맥크리스피 토마토 치즈 크러스트'),
       (16000, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, '신제품 맥스파이시 토마토 치즈 크러스트', 'https://picsum.photos/1600/900', '맥스파이시 토마토 치즈 크러스트'),
       (18000, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, '더블 쿼터파운더 치즈', 'https://picsum.photos/1600/900', '더블 쿼터파운더 치즈'),
       (16000, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, '쿼터파운더 치즈', 'https://picsum.photos/1600/900', '쿼터파운더 치즈'),
       (17000, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, '빅맥', 'https://picsum.photos/1600/900', '빅맥'),
       (17000, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, '신제품 맥크리스피 디럭스 버거', 'https://picsum.photos/1600/900', '맥크리스피 디럭스 버거');

-- Event Food Truck Now
INSERT INTO now (created_at, updated_at, is_operating, latitude, longitude, food_truck_id)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 35.1441, 129.035, 1),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, 35.1634, 129.9823, 2),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 35.0479, 129.9669, 3),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, 35.1615, 129.1608, 4),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, 35.1795, 129.2, 5);
