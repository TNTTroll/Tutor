create table if not exists "user" (
    id varchar(36) not null primary key,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    sur_name varchar(100),
    status varchar(20) not null
);

create table if not exists filter (
    id varchar(36) not null primary key,
    user_id varchar(36) not null,
    type_ids varchar(36)[] default '{}',
    constraint fk_user_id foreign key (user_id) references "user" (id)
);

create table if not exists tutororial (
    id varchar(36) not null primary key,
    filter_id varchar(36) not null,
    author_id varchar(36) not null,
    title text not null,
    tutor text not null,
    created_at timestamp not null,
    status varchar(20) not null default 'UNREAD',
    constraint fk_filter_id foreign key (filter_id) references filter (id),
    constraint fk_author_id foreign key (author_id) references "user" (id)
);
