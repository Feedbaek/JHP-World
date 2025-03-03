create table file (
    id            bigint auto_increment
        primary key,
    name          varchar(255) not null,
    url           varchar(255) not null,
    ext           varchar(15)  null,
    created_date  datetime(6)  null,
    modified_date datetime(6)  null
);

/* assignment 테이블에 file id 컬럼 추가 */

alter table assignment add column file_id bigint null;

alter table assignment add constraint assignment_file_id_fk
    foreign key (file_id) references file (id);
