package service.admin;

import entity.Doctor;
import entity.DoctorsAppointment;
import entity.WorkSchedule;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.DoctorsAppointmentRepository;
import repository.WorkScheduleRepository;
import service.Mapper;
import service.dto.admin.ScheduleAsMapDto;
import util.SessionPool;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class NextWeekTimetableService {

    private final DoctorsAppointmentRepository doctorsAppointmentRepository;
    private final WorkScheduleRepository workScheduleRepository;

    private final Mapper mapper;

    @SneakyThrows
    public void createDoctorsAppointmentsOnNextWeek() {
        Session session = SessionPool.getSession();
        try {
            session.beginTransaction();
            LocalDateTime lastAppDateAndTime = doctorsAppointmentRepository.getLatestAppointmentDate(session)
                    .orElse(LocalDateTime.of(LocalDate.of(0, 0, 0), LocalTime.of(0, 0)));
            ArrayList<WorkSchedule> allDoctorsSchedules = workScheduleRepository.findAll(session);
            ArrayList<ScheduleAsMapDto> scheduleDtoList = new ArrayList<>();
            allDoctorsSchedules.forEach(schedule -> scheduleDtoList.add(mapper.mapToScheduleAsMapDto(schedule, schedule.getDoctor())));
            List<DoctorsAppointment> newWeekTimetable = createNewWeekTimetable(scheduleDtoList, lastAppDateAndTime);
            newWeekTimetable.forEach(doctorsAppointment -> doctorsAppointmentRepository.save(doctorsAppointment, session));
            session.getTransaction().commit();
        } catch (Exception exception) {
            session.getTransaction().rollback();
            throw exception;
        }
    }

    private List<DoctorsAppointment> createNewWeekTimetable(ArrayList<ScheduleAsMapDto> scheduleDtoList, LocalDateTime lastAppDateAndTime) {
        List<DoctorsAppointment> newWeekTimetable = new ArrayList<>();
        List<LocalDate> workWeek = getNextWorkWeek(lastAppDateAndTime);
        for (ScheduleAsMapDto scheduleDto : scheduleDtoList) {
            List<LocalDate> doctorsWorkDays = getDoctorsWorkDays(scheduleDto, workWeek);
            List<LocalDateTime> allAppDatesAndTime = getAllAppointmentDatesAndTime(doctorsWorkDays);
            newWeekTimetable.addAll(getDoctorsAppointments(scheduleDto.getDoctor(), allAppDatesAndTime));
        }
        return newWeekTimetable;
    }

    private List<DoctorsAppointment> getDoctorsAppointments(Doctor doctor, List<LocalDateTime> allAppDatesAndTime) {
        ArrayList<DoctorsAppointment> doctorsAppointments = new ArrayList<>();
        allAppDatesAndTime.forEach(dateAndTime -> doctorsAppointments.add(DoctorsAppointment.builder()
                .doctor(doctor)
                .patient(null)
                .appointmentDateTime(dateAndTime)
                .build()));
        return doctorsAppointments;
    }

    private List<LocalDate> getDoctorsWorkDays(ScheduleAsMapDto scheduleDto, List<LocalDate> workWeek) {
        ArrayList<LocalDate> doctorsWorkDays = new ArrayList<>();
        for (Map.Entry<DayOfWeek, Boolean> entry : scheduleDto.getScheduleAsMap().entrySet()) {
            workWeek.forEach(date -> {
                if (date.getDayOfWeek() == entry.getKey() && entry.getValue()) {
                    doctorsWorkDays.add(date);
                }
            });
        }
        return doctorsWorkDays;
    }

    private List<LocalDateTime> getAllAppointmentDatesAndTime(List<LocalDate> allDates) {
        List<LocalDateTime> dateTimeSet = new ArrayList<>();
        List<LocalTime> appointmentTimeSet = normalWorkTime();
        allDates.forEach(date -> appointmentTimeSet.forEach(time -> dateTimeSet.add(LocalDateTime.of(date, time))));
        return dateTimeSet;
    }

    private List<LocalDate> getNextWorkWeek(LocalDateTime lastAppDateAndTime) {
        ArrayList<LocalDate> dateToTimetable = new ArrayList<>();
        int year;
        int month;
        int day;
        if (LocalDate.now().isAfter(lastAppDateAndTime.toLocalDate())) {
            year = LocalDate.now().getYear();
            month = LocalDate.now().getMonth().getValue();
            day = LocalDate.now().getDayOfMonth();
        } else if (LocalDate.now().equals(lastAppDateAndTime.toLocalDate())) {
            return Collections.emptyList();
        } else {
            LocalDate lastAppDate = lastAppDateAndTime.toLocalDate();
            year = lastAppDate.getYear();
            month = lastAppDate.getMonth().getValue();
            day = lastAppDate.getDayOfMonth();
        }
        for (int i = 0; i < 7; i++) {
            dateToTimetable.add(LocalDate.of(year, month, day + i));
        }
        return dateToTimetable;
    }

    private List<LocalTime> normalWorkTime() {
        return Arrays.asList(LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(15, 0),
                LocalTime.of(16, 0));
    }
}
