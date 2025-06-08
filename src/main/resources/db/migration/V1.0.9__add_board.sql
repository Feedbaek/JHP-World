/* board table */
create table board(
    id bigint auto_increment primary key,
    member_id bigint not null,
    dtype varchar(31) not null,
    created_date datetime(6) null,
    modified_date datetime(6) null,
    constraint board_member_id_fk foreign key (member_id) references member(id)
);

/* update post table */
alter table post drop foreign key post_member_id_fk;
alter table post drop column member_id;
alter table post modify column id bigint not null;
alter table post add constraint post_id_fk foreign key (id) references board(id);

/* update test_case table */
alter table test_case drop foreign key test_case_member_id_fk;
alter table test_case drop column member_id;
alter table test_case modify column id bigint not null;
alter table test_case add constraint test_case_id_fk foreign key (id) references board(id);

/* update comment table */
alter table comment drop foreign key comment_post_id_fk;
alter table comment drop column post_id;
alter table comment add column board_id bigint not null;
alter table comment add constraint comment_board_id_fk foreign key (board_id) references board(id);

/* update recommendation table */
alter table recommendation drop foreign key recommendation_test_case_id_fk;
alter table recommendation drop column test_case_id;
alter table recommendation add column board_id bigint not null;
alter table recommendation add constraint recommendation_board_id_fk foreign key (board_id) references board(id);
