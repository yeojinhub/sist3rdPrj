# sist3rdPrj
sist 쌍용교육센터 3차 프로젝트
<br>
중고거래 플랫폼 사이트
<br>
88마켓(6/16~7/29)

<p>
<img width="100%" src="https://github.com/user-attachments/assets/74ae51a2-cd0e-4fa3-9180-8fbb6839c9d4">
</p>

<a id="목차"></a>
## 📌 목차
1. [📝 개요](#-개요)
2. [🎯 프로젝트 목표](#-프로젝트-목표)
3. [📅 개발 기간](#-개발-기간)
4. [🧑‍🤝‍🧑 멤버 구성](#-멤버-구성)
5. [🛠️ 기술 스택](#기술스택)
6. [🗂 아키텍처 구조](#-아키텍처-구조)
7. [🛢 ERD Diagram](#-Entity-Relationship-Diagram)

## 📝 개요
> 🔹 **프로젝트명** : 88마켓
> 
> 🔹 **유형** : Spring Boot 기반 중고거래 플랫폼 사이트
> 
> 🔹 **설명** : 
> 
> 개인과 기업 모두가 이용 가능한 **B2C·C2C 통합 중고거래 웹 서비스**로,
> 
> 상품 등록, 거래, 문의, 신고, 관리자용 운영 시스템까지 구현 
>
> 🔹 **차별점** :
> 
> **기업 기획관**을 통한 재고·이월상품 판매 지원
> 
> **실거래 환경 반영** (다음 주소 API, 가상 결제 API, 소셜 로그인, JWT 보안)
> 
> **관리자 페이지**를 통한 효율적 운영 관리
> 

## 🎯 프로젝트 목표
> 🔹 기업 전용 판매 영역인 기획관 구현
> 
> 🔹 거래 상태 관리, 1:1 문의·답변, 신고 기능 제공
> 
> 🔹 다음 주소찾기 API, 가상 결제 API 등 다양한 외부 API 연동으로 실무 환경 경험
> 
> 🔹 관리자 페이지를 통한 플랫폼 운영의 효율성 극대화
> 
<p align="right">
  <a href="#목차">🔝 목차로 돌아가기</a>
</p>

## 📅 개발 기간
> 2025-6-16 ~ 2025-7-29 (6주)
> 

## 🧑‍🤝‍🧑 멤버 구성
> ### 👩‍💻 이여진(조장)\[[@yeojinhub\]](https://github.com/yeojinhub)
> 🔹 **사용자 회원 기능** : 회원가입, 로그인
>
> 🔹 **사용자 마이페이지** : 내 정보 변경, 탈퇴하기
> 
> 🔹 **관리자 계정 관리** : 회원/기업/관리자 계정 CRUD
> 
> ### 👨‍💻 강태일(부조장)\[[@tgncosist2\]](https://github.com/tgncosist2)
> 🔹 **사용자 게시판** : 이벤트, 공지사항, FAQ, 문의사항
>
> 🔹 **사용자 사기조회** : 이메일, 전화번호 등 검색하여 사기 조회
>
> 🔹 **기업 기획관** : 결제 API 기능 구현
> 
> 🔹 **관리자 게시판 관리** : 이벤트, 공지사항, FAQ, 문의사항 CRUD
> 
> ### 👩‍💻 김민경\[[@min-7343\]](https://github.com/min-7343)
> 🔹 **사용자 메인페이지** : UI 및 키워드 검색
>
> 🔹 **사용자 카테고리** : 카테고리별 상품 리스트, 찜, 상품 광고 기능 구현
>
> 🔹 **사용자 마이페이지** : 상품 판매, 구매, 찜 내역 관리
>  
> 🔹 **관리자 상품 관리** : 상품 및 거래 관리 CRUD
> 
> ### 👨‍💻 장태규\[[@taegu825\]](https://github.com/taegu825)
> 🔹 **관리자 로그인**
>
> 🔹 **관리자 대시보드** : 상품 거래/등록 통계, 최근 문의 내역
> 
> ### 👨‍💻 유명규\[[@RyuMG\]](https://github.com/RyuMG)
> 🔹 **사용자 기획관** : 기업별 상품 리스트, 등록, 상세페이지 기능 구현
> 
> 🔹 **관리자 기획관 관리** : 전체 기업의 상품 및 주문 관리 CRUD
>
> 🔹 **기업 상품 관리** : 해당 기업의 상품 및 주문 관리 CRUD
> 
> ### 👨‍💻 이대웅\[[@bkj0517\]](https://github.com/bkj0517)
> 🔹 **사용자 상품** : 상품 등록 및 상세페이지
> 
> 🔹 **사용자 채팅** : 채팅 및 신고 기능 구현 
>
> 🔹 **관리자 채팅 관리** : 채팅 로그 저장
>
> 🔹 **관리자 신고 관리** : 신고 CRUD
> 
<p align="right">
  <a href="#목차">🔝 목차로 돌아가기</a>
</p>

<a id="기술스택"></a>
## 🛠️ 기술 스택
> ### 🌐 Frontend
> 
> ![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
> ![CSS](https://img.shields.io/badge/css-%231572B6.svg?style=for-the-badge&logo=css&logoColor=white)
> ![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
> ![jQuery](https://img.shields.io/badge/jquery-%230769AD.svg?style=for-the-badge&logo=jquery&logoColor=white)
> ![Thymeleaf 3.5.3](https://img.shields.io/badge/Thymeleaf%203.5.3-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)
> 
> ### ⚙️ Backend
>
> ![Java 17](https://img.shields.io/badge/java%2017-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
> ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)
> ![Spring MVC](https://img.shields.io/badge/Spring%20MVC-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
> ![Spring Security](https://img.shields.io/badge/Spring%20Security-%236DB33F.svg?style=for-the-badge&logo=springsecurity&logoColor=white)
> ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
> ![JPA](https://img.shields.io/badge/JPA-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
> ![MyBatis](https://img.shields.io/badge/MyBatis-5B5B5B?style=for-the-badge)
> 
> ### 🛢 Database
> 
> ![Oracle Database 19c](https://img.shields.io/badge/Oracle%20Database%2019c-F80000?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyBmaWxsPSIjZmZmIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSI0OCIgaGVpZ2h0PSI0OCIgdmlld0JveD0iMCAwIDQ4IDQ4Ij48ZWxsaXBzZSBjeD0iMjQiIGN5PSIxMiIgcng9IjIwIiByeT0iNiIvPjxlbGxpcHNlIGN4PSIyNCIgY3k9IjI0IiByeD0iMjAiIHJ5PSI2Ii8+PGVsbGlwc2UgY3g9IjI0IiBjeT0iMzYiIHJ4PSIyMCIgcnk9IjYiLz48L3N2Zz4=)
>
> ### 🔗 External API
> 
> ![다음 주소 API](https://img.shields.io/badge/Daum%20Address%20API-FFCD00?style=for-the-badge&logo=kakaotalk&logoColor=000000)
> ![네이버 로그인 API](https://img.shields.io/badge/Naver%20Login%20API-03C75A?style=for-the-badge&logo=naver&logoColor=white)
> ![카카오 로그인 API](https://img.shields.io/badge/Kakao%20Login%20API-FFCD00?style=for-the-badge&logo=kakaotalk&logoColor=000000)
> ![가상 결제 API](https://img.shields.io/badge/Payment%20API-005BAC?style=for-the-badge&logo=paypal&logoColor=white)

> ### 🧰 Development Tools
> 
> #### IDE ![Eclipse](https://img.shields.io/badge/Eclipse-FE7A16.svg?style=for-the-badge&logo=Eclipse&logoColor=white)
> #### Build tools ![Maven](https://img.shields.io/badge/Maven-C71A36.svg?style=for-the-badge&logo=apachemaven&logoColor=white)
> #### Version control ![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white) ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
> #### CI/CD ![Jenkins](https://img.shields.io/badge/jenkins-%232C5263.svg?style=for-the-badge&logo=jenkins&logoColor=white)
> #### Library & Utility ![Lombok](https://img.shields.io/badge/Lombok-%23CC0000.svg?style=for-the-badge&logo=ruby-on-rails&logoColor=white)
>
> ### 🤝 Collaboration Tools
>
> ![Figma](https://img.shields.io/badge/figma-%23F24E1E.svg?style=for-the-badge&logo=figma&logoColor=white)
> ![ERDCloud](https://img.shields.io/badge/ERDCloud-000000?style=for-the-badge&logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCA2NCA2NCI%2BPHJlY3QgeD0iMiIgeT0iMTgiIHdpZHRoPSIxOCIgaGVpZ2h0PSIyOCIgcng9IjMiIHJ5PSIzIiBmaWxsPSJ3aGl0ZSIvPjxyZWN0IHg9IjQ0IiB5PSIxOCIgd2lkdGg9IjE4IiBoZWlnaHQ9IjI4IiByeD0iMyIgcnk9IjMiIGZpbGw9IndoaXRlIi8%2BPHBhdGggZD0iTTI0LDMyIEwzNCwyMiBMNDQsMzIgTDM0LDQyIFoiIGZpbGw9IndoaXRlIi8%2BPC9zdmc%2B)
> ![draw.io](https://img.shields.io/badge/draw.io-F08705?style=for-the-badge&logo=diagramsdotnet&logoColor=white)
> ![KakaoTalk](https://img.shields.io/badge/kakaotalk-ffcd00.svg?style=for-the-badge&logo=kakaotalk&logoColor=000000)
> ![Discord](https://img.shields.io/badge/Discord-%235865F2.svg?style=for-the-badge&logo=discord&logoColor=white)
> 
<p align="right">
  <a href="#목차">🔝 목차로 돌아가기</a>
</p>

## 🗂 아키텍처 구조
> 🔹 **Spring MVC + Security + JWT 구조**
> 
> Spring MVC 기반 계층 구조에 **Spring Security + JWT 인증**을 결합하여,
> 
> 클라이언트 요청에서 데이터베이스 처리까지의 전 과정을 단계별로 구성했습니다.
>
> 🔹 **전체 흐름**
> 
> **View(Thymeleaf + JS)** → **Controller(Spring MVC)** → **Service** → **DAO(MyBatis)** → **SQL Mapper XML** → **DB**
>
> 
> 🔹 **인증 흐름**
>
> **Client** → **JWT Fliter** → **Security Context** → **Controller** → **Service** → **DAO** → **DB**
>

## 🛢 Entity Relationship Diagram
<p>
  <img width="80%" src="https://github.com/user-attachments/assets/acf26617-c09d-4a42-9241-c455b5585e58">
</p>

<p align="right">
  <a href="#목차">🔝 목차로 돌아가기</a>
</p>
