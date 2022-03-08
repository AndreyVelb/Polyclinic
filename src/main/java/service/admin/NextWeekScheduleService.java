package service.admin;

import entity.Doctor;
import entity.DoctorsAppointment;
import entity.WorkSchedule;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import repository.DoctorRepository;
import repository.DoctorsAppointmentRepository;
import repository.DoctorsWorkScheduleRepository;
import util.SessionPool;

import javax.print.Doc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class NextWeekScheduleService {

    private final DoctorRepository doctorRepository;
    private final DoctorsWorkScheduleRepository scheduleRepository;
    private final DoctorsAppointmentRepository doctorsAppointmentRepository;

    public ArrayList<DoctorsAppointment> createDoctorsAppointmentsOnNextWeek(){
        ArrayList<DoctorsAppointment> doctorsAppointmentList = new ArrayList<>();
        Session session = SessionPool.getSession();
        try {
            ArrayList<Doctor> allDoctors = doctorRepository.findAll(session);
            for (Doctor doctor : allDoctors) {
                doctor.getSchedule().
            }
            LocalDate lastAppointmentDate = doctorsAppointmentRepository.getLatestAppointmentDate(session);
            ArrayList<WorkSchedule> doctorsSchedulesList = scheduleRepository.findAll(session);
            for (WorkSchedule doctorWorkSchedule : doctorsSchedulesList) {
                doctorsAppointmentList.add()
            }
        }

    }

    private Set<LocalDateTime> allAppointmentDateTime(LocalDate date){
        Set<LocalDateTime> dateTimeSet = new HashSet<>();
        Set<LocalTime> appointmentTimeSet = normalWorkTime();
        appointmentTimeSet.forEach(time -> dateTimeSet.add(LocalDateTime.of(date, time)));
        return dateTimeSet;
    }

    private Set<LocalDate> doctorsWorkDays(Doctor doctor){
        WorkSchedule schedule = doctor.getSchedule();
        Set<LocalDate> datesToTimetable = getDatesToTimetable()
    }

    private Set<LocalDate> getDatesToTimetable(LocalDate lastAppointmentDate){
        Set<LocalDate> dateToTimetable = new HashSet<>();
        int year;
        int month;
        int day;
        if(LocalDate.now().isBefore(lastAppointmentDate)){
            year = lastAppointmentDate.getYear();
            month = lastAppointmentDate.getMonth().getValue();
            day = lastAppointmentDate.getDayOfMonth();
        }else {
            year = LocalDate.now().getYear();
            month = LocalDate.now().getMonth().getValue();
            day = LocalDate.now().getDayOfMonth();
        }
        for (int i = 0; i < 7; i++) {
            dateToTimetable.add(LocalDate.of(year, month, day + i));
        }
        return dateToTimetable;
    }

    private Set<LocalTime> normalWorkTime(){
        return Set.of(LocalTime.of(9, 0),
                      LocalTime.of(10, 0),
                      LocalTime.of(11, 0),
                      LocalTime.of(12, 0),
                      LocalTime.of(15, 0),
                      LocalTime.of(16, 0));
    }


}
