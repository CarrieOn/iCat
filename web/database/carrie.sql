drop database if exists carrie;
create database carrie;
use carrie;

create table user (
	id int(11) not null auto_increment,
	name varchar(255) default null,
	password varchar(255) default null,
	primary key (id)
)engine = InnoDB default charset = utf8;
insert into user values(null,"Jack","pw1");

create table category (
	id int(11) not null auto_increment,
	name varchar(255) default null,
	primary key (id)
)engine = InnoDB default charset = utf8;

insert into category values(1,"cat");
insert into category values(2,"dog");

create table product (
	id int(11) not null auto_increment,
	name varchar(255) default null,
	subTitle varchar(255) default null,
	originalPrice float default null,
	promotePrice float default null,
	stock int(11) default null,
	cid int(11) default null,
	createDate datetime default null,
	primary key (id),
	constraint fk_product_category foreign key (cid) references category (id)
)engine = InnoDB default charset = utf8;

create table picture (
	id int(11) not null auto_increment,
	pid int(11) default null,
	type varchar(255) default null,
	primary key (id),
	constraint fk_picture_product foreign key (pid) references product (id)
)engine = InnoDB default charset = utf8;

create table review (
	id int(11) not null auto_increment,
	uid int(11) default null,
	pid int(11) default null,
	content varchar(255) default null,
	createDate datetime default null,
	primary key (id),
	constraint fk_review_user foreign key (uid) references user (id),
	constraint fk_review_product foreign key (pid) references product (id)
)engine = InnoDB default charset = utf8;

create table order_admin (
	id int(11) not null auto_increment,
	buyer varchar(255) default null,
	mobile varchar(255) default null,
	address varchar(255) default null,
	zipcode varchar(255) default null,
	message varchar(255) default null,
	status varchar(255) default null,
	createDate datetime default null,
	payDate datetime default null,
	deliveryDate datetime default null,
	confirmDate datetime default null,	
	uid int(11) default null,
	primary key (id),
	constraint fk_order_admin_user foreign key (uid) references user (id)
)engine = InnoDB default charset = utf8;
insert into  order_admin values(null,"Jack",'1112224444',"kirkland",'12346',"please deliver ASAP","waitConfirm","2017-8-1","2017-8-1","2017-8-2","2017-8-4",1);

create table order_user (
	id int(11) not null auto_increment,
	uid int(11) default null,
	pid int(11) default null,
	oid int(11) default null,
	number int(11) default null,
	primary key (id),
	constraint fk_order_user_user foreign key (uid) references user (id),
	constraint fk_order_user_product foreign key (pid) references product (id)
)engine = InnoDB default charset = utf8;