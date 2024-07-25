# role 테이블 생성
create table role
(
    id            bigint auto_increment
        primary key,
    name          enum('ADMIN', 'USER') not null,
    created_date  datetime(6)  null,
    modified_date datetime(6)  null,
    constraint role_name_unique
        unique (name)
);

# member 테이블 생성
create table member
(
    id            bigint auto_increment
        primary key,
    oauth2id      varchar(50) not null,
    name          varchar(30) null,
    role_id       bigint      not null,
    is_enabled    boolean     not null,
    created_date  datetime(6) null,
    modified_date datetime(6) null,
    constraint member_oauth2id_unique
        unique (oauth2id),
    constraint member_role_id_fk
        foreign key (role_id) references role (id)
);
