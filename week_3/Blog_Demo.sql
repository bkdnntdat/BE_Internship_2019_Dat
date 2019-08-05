use blogdemo;

create table roles
(
	id int(10) primary key,
    name varchar(50)
);

create table users
(
	id int(10) primary key,
    username varchar(50),
    password varchar(50),
    firstname nvarchar(50),
    lastname nvarchar(50),
    email varchar(50),
    phone_number varchar(11)
    lastname nvarchar(50)

);

create table users_roles
(
	user_id int(10),
    role_id int(10),
    foreign key(user_id) references users(id),
    foreign key(role_id) references roles(id)
);

create table comments
(
	id int(10) primary key,
    comment nvarchar(1000),
    post_id int(10),
    user_id int(10),
    time datetime,
    foreign key(post_id) references post(id),
    foreign key(user_id) references user(id)
);

create table tags
(
	id int(10) primary key,
    name nvarchar(50)
);

create table posts
(
	id int(10) primary key,
	title nvarchar(500),
    content nvarchar(10000),
    user_id int(10),
    time datetime,
    foreign key(user_id) references users(id)
);

create table post_tags
(
	post_id int(10),
    tag_id int(10),
    foreign key(post_id) references posts(id),
    foreign key(tag_id) references tags(id),
    primary key(tag_id, post_id)
);
