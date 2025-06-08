/* board table */
create table if not exists board(
    id bigint auto_increment primary key,
    member_id bigint not null,
    dtype varchar(31) not null,
    created_date datetime(6) null,
    modified_date datetime(6) null,
    constraint board_member_id_fk foreign key (member_id) references member(id)
);

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

/* update post table */
alter table post drop foreign key post_member_id_fk;
alter table post drop column member_id;
alter table post drop column created_date;
alter table post drop column modified_date;
ALTER TABLE notification
    DROP FOREIGN KEY notification_post_id_fk;
alter table post modify column id bigint not null;
alter table post add constraint post_id_fk foreign key (id) references board(id);
ALTER TABLE notification
    ADD CONSTRAINT notification_post_id_fk
        FOREIGN KEY (post_id) REFERENCES post(id);

/* update test_case table */
alter table test_case drop foreign key test_case_member_id_fk;
alter table test_case drop column member_id;
alter table test_case drop column created_date;
alter table test_case drop column modified_date;
ALTER TABLE grade_history
    DROP FOREIGN KEY grade_test_case_id_fk;
alter table test_case modify column id bigint not null;
alter table test_case add constraint test_case_id_fk foreign key (id) references board(id);
ALTER TABLE grade_history
    ADD CONSTRAINT grade_test_case_id_fk
        FOREIGN KEY (test_case_id) REFERENCES test_case(id);


