create table appointment_records
(
    id                     bigint generated always as identity
        primary key,
    patient_id             bigint    not null
        references patients,
    doctor_id              bigint    not null
        references doctors,
    visit_date             date      not null,
    health_complaints      char(500) not null,
    doctors_recommendation char(500) not null
);

grant delete, insert, references, select, trigger, truncate, update on appointment_records to new_db_admin;