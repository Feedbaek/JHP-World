-- lecture table
create table lecture (
    id            bigint auto_increment
        primary key,
    name          varchar(50) not null,
    created_date  datetime(6)  null,
    modified_date datetime(6)  null
);

-- subject table
create table subject (
    id            bigint auto_increment
        primary key,
    body          TEXT         not null,
    lecture_id    bigint       not null,
    created_date  datetime(6)  null,
    modified_date datetime(6)  null,
    constraint subject_lecture_id_fk
        foreign key (lecture_id) references lecture (id)
);

-- solution table
create table solution (
    id            bigint auto_increment
        primary key,
    source_code   TEXT         not null,
    member_id     bigint       not null,
    subject_id    bigint       not null,
    created_date  datetime(6)  null,
    modified_date datetime(6)  null,
    constraint solution_member_id_fk
        foreign key (member_id) references member (id),
    constraint solution_subject_id_fk
        foreign key (subject_id) references subject (id)
);

-- test_case table
create table test_case (
    id            bigint auto_increment
        primary key,
    subject_id    bigint       not null,
    member_id     bigint       not null,
    input         TEXT         not null,
    output        TEXT         not null,
    description   TEXT         null,
    created_date  datetime(6)  null,
    modified_date datetime(6)  null,
    constraint test_case_subject_id_fk
        foreign key (subject_id) references subject (id),
    constraint test_case_member_id_fk
        foreign key (member_id) references member (id)
);

-- post table
create table post(
    id            bigint auto_increment
        primary key,
    member_id     bigint       not null,
    lecture_id    bigint       not null,
    title         varchar(50)  not null,
    body          TEXT         not null,
    created_date  datetime(6)  null,
    modified_date datetime(6)  null,
    constraint post_member_id_fk
        foreign key (member_id) references member (id),
    constraint post_lecture_id_fk
        foreign key (lecture_id) references lecture (id)
);

-- recommendation table
create table recommendation(
    id            bigint auto_increment
        primary key,
    member_id     bigint       not null,
    test_case_id  bigint       not null,
    created_date  datetime(6)  null,
    modified_date datetime(6)  null,
    constraint recommendation_member_id_fk
        foreign key (member_id) references member (id),
    constraint recommendation_test_case_id_fk
        foreign key (test_case_id) references test_case (id)
);

-- comment table
create table comment(
    id            bigint auto_increment
        primary key,
    member_id     bigint       not null,
    post_id       bigint       not null,
    body          TEXT         not null,
    created_date  datetime(6)  null,
    modified_date datetime(6)  null,
    constraint comment_member_id_fk
        foreign key (member_id) references member (id),
    constraint comment_post_id_fk
        foreign key (post_id) references post (id)
);