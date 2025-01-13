/* visitor_log 테이블에 member_id 컬럼 추가 */

ALTER TABLE visitor_log
    ADD COLUMN member_id bigint;

ALTER TABLE visitor_log
    ADD CONSTRAINT fk_member_id_fk
        FOREIGN KEY (member_id)
            REFERENCES member (id);