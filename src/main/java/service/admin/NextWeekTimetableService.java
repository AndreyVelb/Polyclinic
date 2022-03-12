package service.admin;

import entity.Doctor;
import entity.DoctorsAppointment;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.DoctorRepository;
import repository.DoctorsAppointmentRepository;
import service.dto.admin.ScheduleAsMapDto;
import service.mapper.ScheduleAsMapDtoMapper;
import util.SessionPool;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RequiredArgsConstructor
public class NextWeekTimetableService {

    private final DoctorRepository doctorRepository;
    private final DoctorsAppointmentRepository doctorsAppointmentRepository;

    private final ScheduleAsMapDtoMapper scheduleAsListDtoMapper;

    @SneakyThrows
    public void createDoctorsAppointmentsOnNextWeek(){
        ArrayList<DoctorsAppointment> doctorsAppointmentList = new ArrayList<>();
        Session session = SessionPool.getSession();
        try {
            session.beginTransaction();
            var mayBeLastAppDateAndTime = doctorsAppointmentRepository.getLatestAppointmentDate(session);
            ArrayList<Doctor> allDoctors = doctorRepository.findAll(session);
            ArrayList<ScheduleAsMapDto> scheduleDtoList = new ArrayList<>();
            for (Doctor doctor : allDoctors) {
                scheduleDtoList.add(scheduleAsListDtoMapper.mapFrom(doctor.getSchedule(), doctor));
            }
            List<DoctorsAppointment> newWeekTimetable = createNewWeekTimetable(scheduleDtoList, mayBeLastAppDateAndTime);
            newWeekTimetable.forEach(doctorsAppointment -> doctorsAppointmentRepository.save(doctorsAppointment, session));
            session.getTransaction().commit();
        }catch (Exception exception){
            session.getTransaction().rollback();
            throw exception;
        }
    }

    private List<DoctorsAppointment> createNewWeekTimetable(ArrayList<ScheduleAsMapDto> scheduleDtoList, Optional<LocalDateTime> mayBeLastAppDateAndTime){
        List<DoctorsAppointment> newWeekTimetable = new ArrayList<>();
        List<LocalDate> workWeek = getNextWorkWeek(mayBeLastAppDateAndTime);
        for (ScheduleAsMapDto scheduleDto : scheduleDtoList) {
            List<LocalDate> doctorsWorkDays = getDoctorsWorkDays(scheduleDto, workWeek);
            List<LocalDateTime> allAppDatesAndTime = getAllAppointmentDatesAndTime(doctorsWorkDays);
            newWeekTimetable.addAll(getDoctorsAppointments(scheduleDto.getDoctor(), allAppDatesAndTime));
        }
        return newWeekTimetable;
    }

    private List<DoctorsAppointment> getDoctorsAppointments(Doctor doctor, List<LocalDateTime> allAppDatesAndTime){
        ArrayList<DoctorsAppointment> doctorsAppointments = new ArrayList<>();
        allAppDatesAndTime.forEach(dateAndTime -> doctorsAppointments.add(DoctorsAppointment.builder()
                .doctor(doctor)
                .patient(null)
                .appointmentDateTime(dateAndTime)
                .build()));
        return doctorsAppointments;
    }

    private List<LocalDate> getDoctorsWorkDays(ScheduleAsMapDto scheduleDto, List<LocalDate> workWeek){
        ArrayList<LocalDate> doctorsWorkDays = new ArrayList<>();
        for (Map.Entry<DayOfWeek, Boolean> entry : scheduleDto.getScheduleAsMap().entrySet()) {
            workWeek.forEach(date -> {
                if (date.getDayOfWeek() == entry.getKey() && entry.getValue()){
                    doctorsWorkDays.add(date);
                }
            });
        }
        return doctorsWorkDays;
    }

    private List<LocalDateTime> getAllAppointmentDatesAndTime(List<LocalDate> allDates){
        List<LocalDateTime> dateTimeSet = new ArrayList<>();
        List<LocalTime> appointmentTimeSet = normalWorkTime();
        allDates.forEach(date -> appointmentTimeSet.forEach(time -> dateTimeSet.add(LocalDateTime.of(date, time))));
        return dateTimeSet;
    }

    private List<LocalDate> getNextWorkWeek(Optional<LocalDateTime> mayBeLastAppDateAndTime){
        ArrayList<LocalDate> dateToTimetable = new ArrayList<>();
        int year;
        int month;
        int day;
        if (mayBeLastAppDateAndTime.isEmpty()
            || LocalDate.now().isAfter(mayBeLastAppDateAndTime.get().toLocalDate())){
            year = LocalDate.now().getYear();
            month = LocalDate.now().getMonth().getValue();
            day = LocalDate.now().getDayOfMonth();
        }else if(LocalDate.now().equals(mayBeLastAppDateAndTime.get().toLocalDate())){
            return Collections.emptyList();
        }else {
            LocalDate lastAppDate = mayBeLastAppDateAndTime.get().toLocalDate();
            year = lastAppDate.getYear();
            month = lastAppDate.getMonth().getValue();
            day = lastAppDate.getDayOfMonth();
        }
        for (int i = 0; i < 7; i++) {
            dateToTimetable.add(LocalDate.of(year, month, day + i));
        }
        return dateToTimetable;
    }

    private List<LocalTime> normalWorkTime(){
        return Arrays.asList(LocalTime.of(9, 0),
                      LocalTime.of(10, 0),
                      LocalTime.of(11, 0),
                      LocalTime.of(12, 0),
                      LocalTime.of(15, 0),
                      LocalTime.of(16, 0));
    }
}
