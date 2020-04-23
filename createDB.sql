-- Create DB for login	
drop database if exists login;
create database login;

use login;

create table userInfo(username varchar(255), pass varchar(255), CONSTRAINT login_pk PRIMARY KEY (username)) ;
-- ID will be a 6 character length of numbers
create table favorites(username varchar(255), appid int NOT NULL, CONSTRAINT fav_pk PRIMARY KEY (username, appid), FOREIGN KEY (username) REFERENCES userInfo(username));

INSERT INTO userInfo VALUES ("testUser", "sha1:64000:18:GPDurdNt3uRWMf8JA50WME8p+D/eTv2a:+Pxh8FZ7OrzG5EsTDinUivkb");
INSERT INTO favorites VALUES ("testUser", 10);

select * from userInfo;
select * from favorites;