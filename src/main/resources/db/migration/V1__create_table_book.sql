create table if not exists book
(
    id     varchar(255)   not null
        primary key,
    name   varchar(255)   not null,
    author varchar(255)   null,
    price  decimal(10, 2) null
);
