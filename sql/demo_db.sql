drop database demo_db;
drop user demo_db_user;
create user demo_db_user with password 'demo_db_user';
create database demo_db owner = demo_db_user;
\connect demo_db;
alter default privileges grant all on tables to demo_db_user;
alter default privileges grant all on sequences to demo_db_user;

create table users(
    id integer primary key not null,
    first_name varchar(24) not null,
    last_name varchar(24) not null,
    email varchar(36) not null,
    password text not null
);

alter table users add column profile_id integer unique;

create table profile(
                        id integer primary key not null,
                        code varchar(24)  not null,
                        CONSTRAINT fk_p1
                            FOREIGN KEY(id)
                                REFERENCES users(profile_id)
);