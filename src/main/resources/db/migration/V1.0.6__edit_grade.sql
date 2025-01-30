/* grade_history 테이블에 message 컬럼 삭제 후 success 컬럼 추가 */

alter table grade_history drop column message;
alter table grade_history add column success boolean;

/* grade_history 테이블에 result 컬럼 null 허용으로 변경 */
alter table grade_history modify column result text null;

/* grade_history 테이블에 assignment_id 외래키 추가 */
alter table grade_history add column assignment_id bigint not null;

alter table grade_history add constraint grade_history_assignment_id_fk
    foreign key (assignment_id) references assignment (id);
