drop database demo_db;
drop user demo_db_user;
create user demo_db_user with password 'demo_db_user';
create database demo_db owner = demo_db_user;
\connect demo_db;
alter default privileges grant all on tables to demo_db_user;
alter default privileges grant all on sequences to demo_db_user;

create table demo.users(
    id integer primary key not null,
    first_name varchar(24) not null,
    last_name varchar(24) not null,
    email varchar(36) not null,
    password text not null
);