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
        scheduleAsList.put(DayOfWeek.MONDAY, schedule.isMonday());
        scheduleAsList.put(DayOfWeek.TUESDAY, schedule.isTuesday());
        scheduleAsList.put(DayOfWeek.WEDNESDAY, schedule.isWednesday());
        scheduleAsList.put(DayOfWeek.THURSDAY, schedule.isThursday());
        scheduleAsList.put(DayOfWeek.FRIDAY, schedule.isFriday());
        scheduleAsList.put(DayOfWeek.SATURDAY, schedule.isSaturday());
        scheduleAsList.put(DayOfWeek.SUNDAY, schedule.isSunday());
        return scheduleAsList;
    }
}
