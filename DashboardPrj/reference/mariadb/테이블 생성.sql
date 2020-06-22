#Mariadb my.ini 설정 추가
[client] 
default-character-set=utf8 
[mysqld] 
init_connect="SET collation_connection=utf8_general_ci" 
init_connect="SET NAMES utf8" 
character-set-server=utf8 
collation-server=utf8_general_ci 
skip-character-set-client-handshake 
[mysql] 
default-character-set=utf8


# 테이블 생성
CREATE TABLE DASHBOARD_USER(
	USER_EMAIL VARCHAR(100) NOT NULL PRIMARY KEY COMMENT '이메일',
	USER_NAME VARCHAR(100) NOT NULL COMMENT '이름',
	USER_IMG_URL VARCHAR(1000) NOT NULL COMMENT '사용자 이미지',
	REG_DT DATETIME NOT NULL DEFAULT NOW() COMMENT '등록일',
	UDT_DT DATETIME NOT NULL DEFAULT NOW() COMMENT '수정일'
);

ALTER TABLE DASHBOARD_USER ADD COLUMN USER_LOCALE VARCHAR(10) COMMENT '사용자 언어';


ALTER TABLE table_name convert to charset utf8;