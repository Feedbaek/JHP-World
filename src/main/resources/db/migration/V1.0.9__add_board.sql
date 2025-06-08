/* introduce board table and migrate existing data */

-- create board table
create table board(
                      id bigint auto_increment primary key,
                      member_id bigint not null,
                      dtype varchar(31) not null,
                      created_date datetime(6) null,
                      modified_date datetime(6) null,
                      constraint board_member_id_fk foreign key (member_id) references member(id)
);

-- determine offset for new ids
set @offset := (select ifnull(max(id),0) from post);

-- board rows for post keep existing ids
insert into board(id, member_id, dtype, created_date, modified_date)
select id, member_id, 'Post', created_date, modified_date from post;

-- board rows for test_case shift by offset
insert into board(id, member_id, dtype, created_date, modified_date)
select id + @offset, member_id, 'TestCase', created_date, modified_date from test_case;

-- update test_case primary keys
alter table grade_history drop foreign key grade_test_case_id_fk;
update test_case set id = id + @offset;
update grade_history set test_case_id = test_case_id + @offset;
alter table grade_history add constraint grade_test_case_id_fk foreign key (test_case_id) references test_case(id);

-- update recommendation to board
alter table recommendation add column board_id bigint;
update recommendation set board_id = test_case_id + @offset;
alter table recommendation drop foreign key recommendation_test_case_id_fk;
alter table recommendation drop column test_case_id;
alter table recommendation modify column board_id bigint not null;
alter table recommendation add constraint recommendation_board_id_fk foreign key (board_id) references board(id);

-- update comment to board
alter table comment add column board_id bigint;
update comment set board_id = post_id;
alter table comment drop foreign key comment_post_id_fk;
alter table comment drop column post_id;
alter table comment modify column board_id bigint not null;
alter table comment add constraint comment_board_id_fk foreign key (board_id) references board(id);

-- link post and test_case to board
alter table post drop foreign key post_member_id_fk;
alter table post drop column member_id;
alter table post add constraint post_id_fk foreign key (id) references board(id);
alter table post drop column created_date;
alter table post drop column modified_date;

alter table test_case drop foreign key test_case_member_id_fk;
alter table test_case drop column member_id;
alter table test_case add constraint test_case_id_fk foreign key (id) references board(id);
alter table test_case drop column created_date;
alter table test_case drop column modified_date;
