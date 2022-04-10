create table doctors
(
    id            bigint generated always as identity (maxvalue 2147483647)
        constraint polyclinics_doctors_pkey
            primary key,
    last_name     varchar(20) not null,
    first_name    varchar(20) not null,
    middle_name   varchar(20),
    qualification varchar(10),
    speciality    varchar(20) not null,
    login         varchar(20) not null
        constraint polyclinics_doctors_login_key
            unique,
    password      text        not null
);

grant delete, insert, references, select, trigger, truncate, update on doctors to new_db_admin;