create table notification (
    id            bigint auto_increment
        primary key,
    sender_id     bigint       not null,
    receiver_id   bigint       not null,
    post_id       bigint       not null,
    message       varchar(30)  not null,
    is_read       boolean      not null,
    created_date  datetime(6)  null,
    modified_date datetime(6)  null,

    constraint notification_sender_id_fk
        foreign key (sender_id) references member (id),
    constraint notification_receiver_id_fk
        foreign key (receiver_id) references member (id),
    constraint notification_post_id_fk
        foreign key (post_id) references post (id)
);

/**/
