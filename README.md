# Connect Luck 프로젝트

Connect Luck은 푸드 트럭을 매칭시켜주는 서비스입니다.

## 프로젝트 구조

프로젝트는 다음과 같은 구조로 이루어져 있습니다.

```shell
connect-luck/
📦src
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂ac
 ┃ ┃ ┃ ┗ 📂kr
 ┃ ┃ ┃ ┃ ┗ 📂deu
 ┃ ┃ ┃ ┃ ┃ ┗ 📂connect
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂luck 
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂auth # 인증 관련 패키지
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂common # 공통 패키지
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂configuration # 설정 관련 패키지
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂event # 이벤트 관련 패키지
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂food_truck # 푸드 트럭 관련 패키지
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂user # 사용자 관련 패키지
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜Application.java 
 ┃ ┗ 📂resources # 리소스 파일
 ┃ ┃ ┣ 📂static # 정적 파일
 ┃ ┃ ┃ ┣ 📂style # 스타일 파일
 ┃ ┃ ┃ ┃ ┗ 📂css 
 ┃ ┃ ┃ ┣ 📜favicon.ico 
 ┃ ┃ ┣ 📂templates
 ┃ ┃ ┃ ┣ 📂auth # 인증 관련 페이지
 ┃ ┃ ┃ ┣ 📂event  # 이벤트 관련 페이지
 ┃ ┃ ┃ ┣ 📂food_truck # 푸드 트럭 관련 페이지
 ┃ ┃ ┃ ┣ 📂fragments # 공통 페이지
 ┃ ┃ ┃ ┣ 📂layout # 레이아웃 페이지
 ┃ ┃ ┃ ┣ 📂user # 사용자 관련 페이지
```
## 의존성

프로젝트는 다음과 같은 주요 의존성을 가지고 있습니다.

- Spring Boot 3.2.3
- Spring Data JPA
- Thymeleaf
- Lombok
- MapStruct
- H2 Database 
- Springdoc OpenAPI (Swagger)

## 실행 방법

프로젝트를 로컬 환경에서 실행하려면 다음 명령어를 사용하세요.

```bash
./gradlew bootRun
```

위 명령어를 실행하면 
- http://localhost:8080 에서 웹 애플리케이션을 확인할 수 있습니다.
- http://localhost:8080/swagger-ui/index.html 에서 API 문서를 확인할 수 있습니다.

