# role 테이블 생성
create table role
(
    created_date  datetime(6)  null,
    id            bigint auto_increment
        primary key,
    modified_date datetime(6)  null,
    name          varchar(255) not null,
    constraint role_name_unique
        unique (name)
);

# member 테이블 생성
create table member
(
    created_date  datetime(6) null,
    id            bigint auto_increment
        primary key,
    modified_date datetime(6) null,
    role_id       bigint      not null,
    name          varchar(30) null,
    oauth2id      varchar(50) not null,
    constraint member_oauth2id_unique
        unique (oauth2id),
    constraint member_role_id_fk
        foreign key (role_id) references role (id)
);
