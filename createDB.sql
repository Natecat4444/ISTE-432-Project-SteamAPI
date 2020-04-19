-- Create DB for login	
drop database if exists login;
create database login;

use login;

create table userInfo(username varchar(255), pass varchar(255), id int NOT NULL, CONSTRAINT login_pk PRIMARY KEY (id));
-- ID will be a 6 character length of numbers
create table favorites(gameName varchar(255), id int NOT NULL, CONSTRAINT fav_pk PRIMARY KEY (id));

INSERT INTO userInfo VALUES ("testUser", "sha1:64000:18:IdVIuuzbN0KOXgAdAnGuN8HLj2c1FjyL:SmuyqX58OLjTjM9N5GtrlnLU", 123456);
INSERT INTO favorites VALUES ("Counter-Strike: Global Offensive", 123456);

select * from userInfo;