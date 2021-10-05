create database project_2;
use project_2;

CREATE TABLE USER 
(
ID INT NOT NULL AUTO_INCREMENT,
FIRST_NAME VARCHAR(20),
LAST_NAME VARCHAR(20),
EMAIL VARCHAR(20),
PASSWORD VARCHAR(256), 
PRIMARY KEY (ID),
UNIQUE KEY (EMAIL)
);

CREATE TABLE ROLE 
(
ID INT NOT NULL AUTO_INCREMENT,
NAME VARCHAR(20),
PRIMARY KEY (ID)
);

CREATE TABLE USER_ROLE(
USER_ID int,
ROLE_ID int,
FOREIGN KEY (user_id)
REFERENCES user(id),
FOREIGN KEY (role_id)
REFERENCES role(id)
);

insert into user(first_name,last_name,email,password) values ('admin','admini','admin@email.com','$2a$12$V3PkGnttL66wt5VOUJqLzeNkqzAF1irSrLc5YQca17FojGdrTv5Au');
insert into user(first_name,last_name,email,password) values ('user','useri','user@email.com','$2a$12$V3PkGnttL66wt5VOUJqLzeNkqzAF1irSrLc5YQca17FojGdrTv5Au');

insert into role values(1,'ROLE_ADMIN');
insert into role values(2,'ROLE_USER');

insert into user_role values(1,1);
insert into user_role values(2,2);

select * from user;
select * from role;
select * from user_role;