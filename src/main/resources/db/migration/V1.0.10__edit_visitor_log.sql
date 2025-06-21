/* visitor_log 테이블에 user_agent 컬럼 길이 수정 */

ALTER TABLE visitor_log
    MODIFY COLUMN user_agent varchar(512);