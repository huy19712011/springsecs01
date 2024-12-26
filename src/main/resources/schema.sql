drop table if exists authorities;
drop table if exists users;

create table users(
    username nvarchar(50) not null primary key,
    password nvarchar(500) not null,
    enabled bit not null
);

create table authorities (
    username nvarchar(50) not null,
    authority nvarchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username, authority);


-- drop table if exists customers;
-- CREATE TABLE customers (
--   id int NOT NULL IDENTITY(1,1),
--   email nvarchar(45) NOT NULL,
--   pwd nvarchar(200) NOT NULL,
--   role nvarchar(45) NOT NULL,
--   PRIMARY KEY (id)
-- );
