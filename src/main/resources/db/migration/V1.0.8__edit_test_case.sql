/* test_case 테이블에 is_public 컬럼 추가 */

alter table test_case add column is_public boolean not null default true;