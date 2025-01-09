create table visitor_log (
    id            bigint auto_increment
        primary key,
    session_id       varchar(50)  not null,
    old_session_id    varchar(50),
    ip              varchar(30)  not null,
    user_agent       varchar(255),
    user_name        varchar(30),
    created_date    datetime(6)  null,
    modified_date   datetime(6)  null
);

/**/
