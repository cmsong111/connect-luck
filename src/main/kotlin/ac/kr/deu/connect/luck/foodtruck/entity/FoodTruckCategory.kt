package ac.kr.deu.connect.luck.foodtruck.entity

enum class FoodTruckCategory(
    val displayName: String,
    val description: String
) {
    KOREAN(
        displayName = "한식",
        description = "떡볶이, 비빔밥, 전 등 다양한 한식 메뉴를 제공합니다."
    ),
    SNACK(
        displayName = "분식",
        description = "김밥, 튀김, 라면 등 간단하고 빠르게 즐길 수 있는 분식류입니다."
    ),
    WESTERN(
        displayName = "양식",
        description = "수제버거, 감자튀김 등 서양식 요리를 제공합니다."
    ),
    JAPANESE(
        displayName = "일식",
        description = "타코야끼, 오코노미야끼 등 일본식 요리를 제공합니다."
    ),
    CHINESE(
        displayName = "중식",
        description = "짜장면, 덮밥 등 중국식 요리를 제공합니다."
    ),
    FUSION(
        displayName = "퓨전",
        description = "여러 국가의 음식 문화를 접목한 창의적인 메뉴를 제공합니다."
    ),
    DESSERT(
        displayName = "디저트",
        description = "와플, 크레페, 아이스크림 등 달콤한 디저트를 판매합니다."
    ),
    DRINK(
        displayName = "음료",
        description = "커피, 주스, 버블티 등 다양한 음료를 제공합니다."
    ),
    BAKERY(
        displayName = "베이커리",
        description = "샌드위치, 토스트, 베이글 등 다양한 제과/제빵 제품입니다."
    ),
    HEALTHY(
        displayName = "건강식",
        description = "저칼로리, 저당 등의 건강을 고려한 식단을 제공합니다."
    ),
    VEGAN(
        displayName = "비건",
        description = "동물성 재료를 사용하지 않는 채식 위주의 메뉴입니다."
    ),
    ETC(
        displayName = "기타",
        description = "기타 분류에 해당되지 않는 다양한 메뉴입니다."
    );
}
