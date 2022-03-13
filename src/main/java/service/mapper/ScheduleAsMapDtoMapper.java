package service.mapper;

import entity.Doctor;
import entity.WorkSchedule;
import service.dto.admin.ScheduleAsMapDto;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

public class ScheduleAsMapDtoMapper implements Mapper<WorkSchedule, ScheduleAsMapDto> {

    @Override
    public ScheduleAsMapDto mapFrom(WorkSchedule schedule) {
        return mapFrom(schedule, new Doctor());
    }

    public ScheduleAsMapDto mapFrom(WorkSchedule schedule, Doctor doctor){
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
