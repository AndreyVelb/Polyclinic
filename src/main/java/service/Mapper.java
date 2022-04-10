package service;

import entity.*;
import service.dto.admin.DocAppForAdminDto;
import service.dto.admin.DoctorRegistrationDto;
import service.dto.admin.ScheduleAsMapDto;
import service.dto.doctor.AppointmentRecordDto;
import service.dto.doctor.AppointmentRecordSavedDto;
import service.dto.doctor.DoctorDto;
import service.dto.patient.DocAppForPatientDto;
import service.dto.patient.PatientDto;
import service.dto.patient.PatientRegistrationDto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class Mapper {

    public AppointmentRecord mapToAppRec(AppointmentRecordSavedDto dto) {
        return AppointmentRecord.builder()
                .patient(dto.getPatient())
                .doctor(dto.getDoctor())
                .visitDate(dto.getVisitDate())
                .healthComplaints(dto.getHealthComplaints())
                .doctorsRecommendation(dto.getDoctorsRecommendation())
                .build();
    }

    public AppointmentRecordDto mapToAppRecordDto(AppointmentRecord record) {
        return AppointmentRecordDto.builder()
                .id(record.getId())
                .patientDto(mapToPatientDto(record.getPatient()))
                .doctorDto(mapToDoctorDto(record.getDoctor()))
                .visitDate(record.getVisitDate())
                .healthComplaints(record.getHealthComplaints())
                .doctorsRecommendation(record.getDoctorsRecommendation())
                .build();
    }

    public DocAppForAdminDto mapToDocAppForAdminDto(DoctorsAppointment doctorsAppointment) {
        return DocAppForAdminDto.builder()
                .id(doctorsAppointment.getId())
                .doctorDto(mapToDoctorDto(doctorsAppointment.getDoctor()))
                .patientDto(mapToPatientDto(doctorsAppointment.getPatient()))
                .dateAndTime(LocalDateTime.parse(doctorsAppointment.getAppointmentDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .build();
    }

    public DocAppForPatientDto mapToDocAppForPatientDto(DoctorsAppointment doctorsAppointment) {
        return DocAppForPatientDto.builder()
                .id(doctorsAppointment.getId())
                .doctorDto(mapToDoctorDto(doctorsAppointment.getDoctor()))
                .dateAndTime(LocalDateTime.parse(doctorsAppointment.getAppointmentDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .build();
    }

    public DoctorDto mapToDoctorDto(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .lastName(doctor.getLastName())
                .firstName(doctor.getFirstName())
                .middleName(doctor.getMiddleName())
                .qualification(doctor.getQualification())
                .speciality(doctor.getSpeciality())
                .build();
    }

    public Doctor mapToDoctor(DoctorRegistrationDto dto) {
        return Doctor.builder()
                .lastName(dto.getLastName())
                .firstName(dto.getFirstName())
                .middleName(dto.getMiddleName())
                .speciality(dto.getSpeciality())
                .qualification(dto.getQualification())
                .login(dto.getLogin())
                .password(dto.getPassword())
                .build();
    }

    public PatientDto mapToPatientDto(Patient patient) {
        if (patient == null){
            return PatientDto.builder()
                    .id(null)
                    .lastName(null)
                    .firstName(null)
                    .middleName(null)
                    .birthDate(null)
                    .build();
        } else {
            return PatientDto.builder()
                    .id(patient.getId())
                    .lastName(patient.getLastName())
                    .firstName(patient.getFirstName())
                    .middleName(patient.getMiddleName())
                    .birthDate(patient.getBirthDate())
                    .build();
        }
    }

    public Patient mapToPatient(PatientRegistrationDto dto) {
        return Patient.builder()
                .lastName(dto.getLastName())
                .firstName(dto.getFirstName())
                .middleName(dto.getMiddleName())
                .birthDate(dto.getBirthDate())
                .login(dto.getLogin())
                .password(dto.getPassword())
                .build();
    }

    public ScheduleAsMapDto mapToScheduleAsMapDto(WorkSchedule schedule, Doctor doctor){
        Map<DayOfWeek, Boolean> scheduleAsMap = getScheduleAsMap(schedule);
        return ScheduleAsMapDto.builder()
                .doctor(doctor)
                .scheduleAsMap(scheduleAsMap)
                .build();
    }

    private Map<DayOfWeek, Boolean> getScheduleAsMap(WorkSchedule schedule){
        Map<DayOfWeek, Boolean> scheduleAsList = new HashMap<>();
        scheduleAsList.put(DayOfWeek.MONDAY, schedule.getMonday());
        scheduleAsList.put(DayOfWeek.TUESDAY, schedule.getTuesday());
        scheduleAsList.put(DayOfWeek.WEDNESDAY, schedule.getWednesday());
        scheduleAsList.put(DayOfWeek.THURSDAY, schedule.getThursday());
        scheduleAsList.put(DayOfWeek.FRIDAY, schedule.getFriday());
        scheduleAsList.put(DayOfWeek.SATURDAY, schedule.getSaturday());
        scheduleAsList.put(DayOfWeek.SUNDAY, schedule.getSunday());
        return scheduleAsList;
    }
}
