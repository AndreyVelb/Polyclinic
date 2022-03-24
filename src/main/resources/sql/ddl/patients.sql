create table patients
(
    id          bigint generated always as identity (maxvalue 2147483647)
        primary key,
    last_name   varchar(20) not null,
    first_name  varchar(20) not null,
    middle_name varchar(20),
    birth_date  date        not null,
    login       varchar(30) not null
        unique,
    password    text        not null
);

create index patient_last_name
    on patients (last_name, first_name, middle_name, birth_date);

grant delete, insert, references, select, trigger, truncate, update on patients to new_db_admin;