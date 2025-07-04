-- 가데이터 삽입 (각 테이블당 최소 3개씩)

/* USERS */
INSERT INTO USERS(USER_NUM, NAME, EMAIL, PASS, TEL, ZIPCODE, ADDRESS)
VALUES (SEQ_USER_NUM.NEXTVAL, '홍길동',    'hong@example.com',    'pass123', '010-1111-1111', '12345', '서울 강남구 역삼동 123-45');
INSERT INTO USERS(USER_NUM, NAME, EMAIL, PASS, TEL, ZIPCODE, ADDRESS)
VALUES (SEQ_USER_NUM.NEXTVAL, '김영희',    'younghee@example.com','pwd456',   '010-2222-2222', '23456', '경기 성남시 분당구 서현동 67-89');
INSERT INTO USERS(USER_NUM, NAME, EMAIL, PASS, TEL, ZIPCODE, ADDRESS)
VALUES (SEQ_USER_NUM.NEXTVAL, '이민호',    'minho@example.com',   'abc789',   '010-3333-3333', '34567', '인천 남동구 구월동 12-34');

/* ADMIN */
INSERT INTO ADMIN(ADM_NUM, NAME, ID, PASS, TEL)
VALUES (SEQ_ADM_NUM.NEXTVAL, '관리자A',
				'admin' || LPAD(SEQ_ADM_NUM.NEXTVAL, 2, '0'),
				'admPass1', '010-1111-1111');
INSERT INTO ADMIN(ADM_NUM, NAME, ID, PASS, TEL)
VALUES (SEQ_ADM_NUM.NEXTVAL, '관리자B',
				'admin' || LPAD(SEQ_ADM_NUM.NEXTVAL, 2, '0'),
				'admPass2', '010-2222-2222');
INSERT INTO ADMIN(ADM_NUM, NAME, ID, PASS, TEL)
VALUES (SEQ_ADM_NUM.NEXTVAL, '관리자C',
				'admin' || LPAD(SEQ_ADM_NUM.NEXTVAL, 2, '0'),
				'admPass3', '010-3333-3333');

/* CATEGORY */
INSERT INTO CATEGORY(CAT_NUM, NAME)
VALUES (SEQ_CAT_NUM.NEXTVAL, '전자제품');
INSERT INTO CATEGORY(CAT_NUM, NAME)
VALUES (SEQ_CAT_NUM.NEXTVAL, '도서');
INSERT INTO CATEGORY(CAT_NUM, NAME)
VALUES (SEQ_CAT_NUM.NEXTVAL, '의류');

/* COMPANY */
INSERT INTO COMPANY(COM_NUM, COM_NAME, CEO_NAME, NAME, ID, PASS, TEL, ZIPCODE, ADDRESS)
VALUES (SEQ_COM_NUM.NEXTVAL, 'ABC Corp',    '김대표', '홍길동', 'abc123', 'compPass1', '02-111-2222', '56789', '서울 마포구 월드컵북로 400');
INSERT INTO COMPANY(COM_NUM, COM_NAME, CEO_NAME, NAME, ID, PASS, TEL, ZIPCODE, ADDRESS)
VALUES (SEQ_COM_NUM.NEXTVAL, 'XYZ Ltd',     '이대표', '김영희', 'xyz123', 'compPass2', '031-333-4444', '67890', '경기 성남시 분당구 판교로 50');
INSERT INTO COMPANY(COM_NUM, COM_NAME, CEO_NAME, NAME, ID, PASS, TEL, ZIPCODE, ADDRESS)
VALUES (SEQ_COM_NUM.NEXTVAL, '테크노베이션','박대표', '이민호', 'tech123','compPass3', '042-555-6666', '78901', '대전 서구 둔산로 99');

/* PRODUCT */
INSERT INTO PRODUCT(PRD_NUM, TITLE, CONTENT, PRICE, USER_NUM, CAT_NUM, COM_NUM, LOCATION1)
VALUES (SEQ_PRD_NUM.NEXTVAL, '최신 스마트폰',     '2025년형 풀스크린 스마트폰', 900000, 1, 1, 1, '덕평휴게소');
INSERT INTO PRODUCT(PRD_NUM, TITLE, CONTENT, PRICE, USER_NUM, CAT_NUM, COM_NUM, LOCATION1)
VALUES (SEQ_PRD_NUM.NEXTVAL, '베스트셀러 소설',   '2025 화제의 소설 단행본',     20000, 2, 2, 2, '행담도휴게소');
INSERT INTO PRODUCT(PRD_NUM, TITLE, CONTENT, PRICE, USER_NUM, CAT_NUM, COM_NUM, LOCATION1)
VALUES (SEQ_PRD_NUM.NEXTVAL, '여름 반팔 티셔츠',   '시원한 코튼 반팔티',           15000, 3, 3, 3, '죽암휴게소');

/* NOTICE */
INSERT INTO NOTICE(NOT_NUM, ADM_NUM, TITLE, CONTENT, NAME)
VALUES (SEQ_NOT_NUM.NEXTVAL, 1, '시스템 점검 안내',    '7월 1일 새벽 02:00 ~ 04:00 시스템 점검이 있습니다.', '관리자A');
INSERT INTO NOTICE(NOT_NUM, ADM_NUM, TITLE, CONTENT, NAME)
VALUES (SEQ_NOT_NUM.NEXTVAL, 2, '이벤트 공지',        '7월 한 달간 구매 금액 5% 적립 이벤트',               '관리자B');
INSERT INTO NOTICE(NOT_NUM, ADM_NUM, TITLE, CONTENT, NAME)
VALUES (SEQ_NOT_NUM.NEXTVAL, 3, '신규 기능 출시',     '상품 추천 알고리즘이 업데이트 되었습니다.',         '관리자C');

/* BANK */
INSERT INTO BANK(USER_NUM, NAME, BANK_NAME, BANK_NUM)
VALUES ((select user_num from users where user_num=1), '홍길동',    '국민은행', '123-456-7890');
INSERT INTO BANK(USER_NUM, NAME, BANK_NAME, BANK_NUM)
VALUES ((select user_num from users where user_num=2), '김영희',    '신한은행', '234-567-8901');
INSERT INTO BANK(USER_NUM, NAME, BANK_NAME, BANK_NUM)
VALUES ((select user_num from users where user_num=3), '이민호',    '우리은행', '345-678-9012');

/* FAQ */
-- FAQ 가데이터 삽입
INSERT INTO FAQ (FAQ_NUM, TITLE, CONTENT, NAME, INPUT_DATE, FAQ_TYPE, ADM_NUM) VALUES
(SEQ_FAQ_NUM.NEXTVAL, '거래 문의에 대한 FAQ', '거래 관련 문제가 발생했을 경우 해결 방법은 XXX입니다.', (select name from admin where adm_num=1), SYSDATE, '거래문의', 1);
INSERT INTO FAQ (FAQ_NUM, TITLE, CONTENT, NAME, INPUT_DATE, FAQ_TYPE, ADM_NUM) VALUES
(SEQ_FAQ_NUM.NEXTVAL, '회원가입 문제 해결', '회원가입 시 이메일 인증 오류가 발생하는 경우, 다시 시도해 보세요.', (select name from admin where adm_num=1), SYSDATE, '회원/계정', 1);
INSERT INTO FAQ (FAQ_NUM, TITLE, CONTENT, NAME, INPUT_DATE, FAQ_TYPE, ADM_NUM) VALUES
(SEQ_FAQ_NUM.NEXTVAL, '운영 정책 변경 안내', '운영 정책이 변경되어 사용자는 이를 꼭 확인해야 합니다.', (select name from admin where adm_num=1), SYSDATE, '운영정책', 1);
INSERT INTO FAQ (FAQ_NUM, TITLE, CONTENT, NAME, INPUT_DATE, FAQ_TYPE, ADM_NUM) VALUES
(SEQ_FAQ_NUM.NEXTVAL, '서비스 이용에 관한 안내', '서비스 이용 중 발생할 수 있는 문제와 해결 방법을 안내드립니다.', (select name from admin where adm_num=1), SYSDATE, '이용문의', 1);
INSERT INTO FAQ (FAQ_NUM, TITLE, CONTENT, NAME, INPUT_DATE, FAQ_TYPE, ADM_NUM) VALUES
(SEQ_FAQ_NUM.NEXTVAL, '계정 복구 절차 안내', '계정을 복구하는 방법은 이메일을 통해 인증을 받는 방식입니다.', (select name from admin where adm_num=1), SYSDATE, '회원/계정', 1);
INSERT INTO FAQ (FAQ_NUM, TITLE, CONTENT, NAME, INPUT_DATE, FAQ_TYPE, ADM_NUM) VALUES
(SEQ_FAQ_NUM.NEXTVAL, '시스템 점검 안내', '시스템 점검은 매주 금요일에 진행됩니다. 점검 시간 동안 서비스 이용에 불편이 있을 수 있습니다.', (select name from admin where adm_num=1), SYSDATE, '기타', 1);
INSERT INTO FAQ (FAQ_NUM, TITLE, CONTENT, NAME, INPUT_DATE, FAQ_TYPE, ADM_NUM) VALUES
(SEQ_FAQ_NUM.NEXTVAL, '기타 자주 묻는 질문', '기타 자주 묻는 질문은 고객센터에서 확인하실 수 있습니다.', (select name from admin where adm_num=1), SYSDATE, '기타', 1);
INSERT INTO FAQ (FAQ_NUM, TITLE, CONTENT, NAME, INPUT_DATE, FAQ_TYPE, ADM_NUM) VALUES
(SEQ_FAQ_NUM.NEXTVAL, '로그인 문제 해결법', '로그인 시 문제가 발생하면, 비밀번호를 재설정해 보세요.', (select name from admin where adm_num=1), SYSDATE, '회원/계정', 1);
INSERT INTO FAQ (FAQ_NUM, TITLE, CONTENT, NAME, INPUT_DATE, FAQ_TYPE, ADM_NUM) VALUES
(SEQ_FAQ_NUM.NEXTVAL, '결제 오류 해결 방법', '결제 오류가 발생하면 고객센터에 문의해 주세요.', (select name from admin where adm_num=1), SYSDATE, '거래문의', 1);
INSERT INTO FAQ (FAQ_NUM, TITLE, CONTENT, NAME, INPUT_DATE, FAQ_TYPE, ADM_NUM) VALUES
(SEQ_FAQ_NUM.NEXTVAL, '서비스 이용 약관 안내', '서비스 이용 약관에 동의하시려면, 아래 링크를 클릭하세요.', (select name from admin where adm_num=1), SYSDATE, '운영정책', 1);

/* REVIEW */
INSERT INTO REVIEW(REV_NUM, CONTENT, NAME, KEYWORD, PRD_NUM)
VALUES (SEQ_REV_NUM.NEXTVAL, '상품이 정말 만족스럽습니다!', '홍길동', '최고예요', '1');
INSERT INTO REVIEW(REV_NUM, CONTENT, NAME, KEYWORD, PRD_NUM)
VALUES (SEQ_REV_NUM.NEXTVAL, '책이 너무 재미있어요.',     '김영희', '좋아요', '2');
INSERT INTO REVIEW(REV_NUM, CONTENT, NAME, KEYWORD, PRD_NUM)
VALUES (SEQ_REV_NUM.NEXTVAL, '티셔츠가 핏이 예뻐요.',     '이민호', '좋아요', '3');

/* TRADES */
INSERT INTO TRADES(TRADE_ID, BUYER_ID, TRADE_STATUS, TRADE_DATE, PRD_NUM)
VALUES ((select email from users where user_num=1),
				(select email from users where user_num=2), '거래완료', SYSDATE, 1);
INSERT INTO TRADES(TRADE_ID, BUYER_ID, TRADE_STATUS, TRADE_DATE, PRD_NUM)
VALUES ((select email from users where user_num=2),
				(select email from users where user_num=3), '판매중',  SYSDATE, 2);
INSERT INTO TRADES(TRADE_ID, BUYER_ID, TRADE_STATUS, TRADE_DATE, PRD_NUM)
VALUES ((select email from users where user_num=3),
				(select email from users where user_num=1), '예약중',SYSDATE, 3);

/* PAYMENTS */
INSERT INTO PAYMENTS(PATMENT_ID, AMOUNT, METHOD, PATMENT_DATE, TRADE_ID)
VALUES ((select TRADE_ID from TRADES where BUYER_ID='younghee@example.com'),
				900000, '카드결제', SYSDATE,
				(select TRADE_ID from TRADES where TRADE_ID='hong@example.com'));
INSERT INTO PAYMENTS(PATMENT_ID, AMOUNT, METHOD, PATMENT_DATE, TRADE_ID)
VALUES ((select TRADE_ID from TRADES where BUYER_ID='minho@example.com'),
				20000,  '계좌이체', SYSDATE,
				(select TRADE_ID from TRADES where TRADE_ID='hong@example.com'));
INSERT INTO PAYMENTS(PATMENT_ID, AMOUNT, METHOD, PATMENT_DATE, TRADE_ID)
VALUES ((select TRADE_ID from TRADES where BUYER_ID='younghee@example.com'),
				15000,  '휴대폰결제', SYSDATE,
				(select TRADE_ID from TRADES where TRADE_ID='minho@example.com'));

/* FAVORITE */
INSERT INTO FAVORITE(FAV_NUM, PRD_NUM, USER_NUM)
VALUES (SEQ_FAV_NUM.NEXTVAL, 1, 1);
INSERT INTO FAVORITE(FAV_NUM, PRD_NUM, USER_NUM)
VALUES (SEQ_FAV_NUM.NEXTVAL, 2, 2);
INSERT INTO FAVORITE(FAV_NUM, PRD_NUM, USER_NUM)
VALUES (SEQ_FAV_NUM.NEXTVAL, 3, 3);

/* INQUIRY */
INSERT INTO INQUIRY(INQ_NUM, TITLE, CONTENT, NAME, USER_NUM, ADM_NUM)
VALUES (SEQ_INQ_NUM.NEXTVAL, '상품 문의',     '색상 옵션 추가 가능할까요?',   '홍길동', 1, 1);
INSERT INTO INQUIRY(INQ_NUM, TITLE, CONTENT, NAME, USER_NUM, ADM_NUM)
VALUES (SEQ_INQ_NUM.NEXTVAL, '취소 요청',     '주문 취소 어떻게 하나요?',     '김영희', 2, 2);
INSERT INTO INQUIRY(INQ_NUM, TITLE, CONTENT, NAME, USER_NUM, ADM_NUM)
VALUES (SEQ_INQ_NUM.NEXTVAL, '교환 문의',     '사이즈 교환 가능한가요?',       '이민호', 3, 3);

/* ANSWER */
INSERT INTO ANSWER(INQ_NUM, TITLE, CONTENT, NAME)
VALUES (1, 'Re: 상품 문의', '네, 색상 옵션 기능 곧 추가됩니다.', (select name from admin where adm_num=1));
INSERT INTO ANSWER(INQ_NUM, TITLE, CONTENT, NAME)
VALUES (2, 'Re: 취소 요청', '마이페이지 > 주문내역에서 가능합니다.', (select name from admin where adm_num=2));
INSERT INTO ANSWER(INQ_NUM, TITLE, CONTENT, NAME)
VALUES (3, 'Re: 교환 문의', '사이즈 교환 신청서 작성 부탁드립니다.', (select name from admin where adm_num=3));

/* IMAGE */
INSERT INTO IMAGE(IMG_NUM, SUB_IMAGE1, SUB_IMAGE2, SUB_IMAGE3, SUB_IMAGE4, IMAGE_TYPE)
VALUES (SEQ_IMG_NUM.NEXTVAL, '/img/p1_1.jpg', '/img/p1_2.jpg', '/img/p1_3.jpg', '/img/p1_4.jpg', 1);
INSERT INTO IMAGE(IMG_NUM, SUB_IMAGE1, SUB_IMAGE2, SUB_IMAGE3, SUB_IMAGE4, IMAGE_TYPE)
VALUES (SEQ_IMG_NUM.NEXTVAL, '/img/p2_1.jpg', '/img/p2_2.jpg', '/img/p2_3.jpg', '/img/p2_4.jpg', 1);
INSERT INTO IMAGE(IMG_NUM, SUB_IMAGE1, SUB_IMAGE2, SUB_IMAGE3, SUB_IMAGE4, IMAGE_TYPE)
VALUES (SEQ_IMG_NUM.NEXTVAL, '/img/p3_1.jpg', '/img/p3_2.jpg', '/img/p3_3.jpg', '/img/p3_4.jpg', 1);

/* EVENT */
INSERT INTO EVENT(EVT_NUM, TITLE, CONTENT, NAME, START_DATE, END_DATE, ADM_NUM)
VALUES (SEQ_EVT_NUM.NEXTVAL, '7월 세일', '7월 한 달간 전 상품 10% 할인',
				(SELECT NAME FROM ADMIN WHERE ADM_NUM=1), SYSDATE, SYSDATE+30, 1);
INSERT INTO EVENT(EVT_NUM, TITLE, CONTENT, NAME, START_DATE, END_DATE, EVT_TYPE, ADM_NUM)
VALUES (SEQ_EVT_NUM.NEXTVAL, '백투스쿨', '학생증 제시 시 추가 적립',
				(SELECT NAME FROM ADMIN WHERE ADM_NUM=2), SYSDATE, SYSDATE+15, 2, 2);
INSERT INTO EVENT(EVT_NUM, TITLE, CONTENT, NAME, START_DATE, END_DATE, EVT_TYPE, ADM_NUM)
VALUES (SEQ_EVT_NUM.NEXTVAL, '한글날 기념', '한글날 당일 구매 고객 경품 추첨',
				(SELECT NAME FROM ADMIN WHERE ADM_NUM=3),
				TO_DATE('2025-10-09','YYYY-MM-DD'), TO_DATE('2025-10-09','YYYY-MM-DD'), 3, 3);

/* ADDRESS */
INSERT INTO ADDRESS(ADDR_NUM, NAME, RECIPIENT_NAME, TEL, ZIPCODE, ADDRESS, USER_NUM)
VALUES (SEQ_ADDR_NUM.NEXTVAL, '집',     '홍길동',    '010-1111-1111', '12345', '서울 강남구 역삼동 123-45', 1);
INSERT INTO ADDRESS(ADDR_NUM, NAME, RECIPIENT_NAME, TEL, ZIPCODE, ADDRESS, USER_NUM)
VALUES (SEQ_ADDR_NUM.NEXTVAL, '회사',   '김영희',    '010-2222-2222', '23456', '경기 성남시 분당구 서현동 67-89', 2);
INSERT INTO ADDRESS(ADDR_NUM, NAME, RECIPIENT_NAME, TEL, ZIPCODE, ADDRESS, USER_NUM)
VALUES (SEQ_ADDR_NUM.NEXTVAL, '친구집','이민호',    '010-3333-3333', '34567', '인천 남동구 구월동 12-34',    3);

/* REPORT */
INSERT INTO REPORT(REP_NUM, TITLE, CONTENT, NAME, REPORT_TYPE, USER_NUM)
VALUES (SEQ_REP_NUM.NEXTVAL, '스팸 메시지', '광고성 메시지 수신',    '홍길동', 1, 1);
INSERT INTO REPORT(REP_NUM, TITLE, CONTENT, NAME, REPORT_TYPE, USER_NUM)
VALUES (SEQ_REP_NUM.NEXTVAL, '불량 상품',   '상품이 파손되어 도착',   '김영희', 2, 2);
INSERT INTO REPORT(REP_NUM, TITLE, CONTENT, NAME, REPORT_TYPE, USER_NUM)
VALUES (SEQ_REP_NUM.NEXTVAL, '배송 지연',   '배송이 일주일 지연됨',   '이민호', 3, 3);

commit;
