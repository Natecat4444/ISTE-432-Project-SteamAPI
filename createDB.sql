-- Create DB for login	
drop database if exists login;
create database login;

use login;

create table userInfo(username varchar(255), pass varchar(255), id int NOT NULL, CONSTRAINT login_pk PRIMARY KEY (id));
-- ID will be a 6 character length of numbers
create table favorites(gameName varchar(255), id int NOT NULL, CONSTRAINT fav_pk PRIMARY KEY (id));

INSERT INTO userInfo VALUES ("testUser", "password", 123456);
INSERT INTO favorites VALUES ("Counter-Strike: Global Offensive", 123456);

select * from userInfo;
select * from favorites;