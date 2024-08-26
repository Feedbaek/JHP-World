create table grade (
    id            bigint auto_increment
        primary key,
    solution_id   bigint       not null,
    test_case_id  bigint       not null,
    message       varchar(30)  not null,
    result        text         not null,
    created_date  datetime(6)  null,
    modified_date datetime(6)  null,
    constraint grade_solution_id_fk
        foreign key (solution_id) references solution (id),
    constraint grade_test_case_id_fk
        foreign key (test_case_id) references test_case (id)
);