show tables;

create table user3 (
  idx int not null auto_increment,
  mid varchar(20) not null,
  name varchar(20) not null,
  age int not null default 20,
  gender char(2) default '여자',
  address varchar(20),  
  primary key (idx)
);
drop table user3;

insert into user3 values(default, 'admin', '관리자', default, '남자', '청주');

select * from user3;
